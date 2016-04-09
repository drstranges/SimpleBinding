package com.testapp.weather.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.content.ContextCompat;

import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.network.RestClient;
import com.testapp.weather.storage.DataStorage;
import com.testapp.weather.util.ForecastUtils;
import com.testapp.weather.util.PrefUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 25.12.2015.
 */
public class DayViewModel extends BaseViewModel {

    private Callback mCallback;
    public ObservableBoolean isEmptyMessageVisible = new ObservableBoolean(false);
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableField<ForecastItem> bForecast = new ObservableField<>();
    private Subscription mSubscription;

    public DayViewModel(Context context, ForecastItem item, Callback callback) {
        super(context);
        mCallback = callback != null ? callback : Callback.EMPTY_CALLBACK;
        if (item != null) {
            setForecast(item);
        } else {
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe(mSubscription);
        mCallback = null;
    }

    public void onRefresh() {
        loadData();
    }

    public void loadData() {
        unsubscribe(mSubscription);
        isLoading.set(true);
        final String location = PrefUtils.getPreferredLocation(mContext);
        mSubscription = RestClient.getApi()
                .getDailyForecast(location, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap((f) -> Observable.from(f.list))
                .take(1)
                .subscribe(new Subscriber<ForecastItem>() {
                    @Override
                    public void onCompleted() {
                        isLoading.set(false);
                        refreshStatus();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        mCallback.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ForecastItem forecast) {
                        setForecast(forecast);
                    }
                });
    }

    private void setForecast(ForecastItem forecast) {
        bForecast.set(forecast);
        DataStorage.storeCurrentWeather(ForecastUtils.getWeatherCondition(forecast.weather));
    }

    private void refreshStatus() {
        final ForecastItem forecastItem = bForecast.get();
        final boolean hasForecast = forecastItem != null;
        isEmptyMessageVisible.set(!hasForecast);
        if (hasForecast) {
            mCallback.setBgColor(
                    ContextCompat.getColor(mContext,
                            ForecastUtils.getWeatherCondition(forecastItem.weather).getColorResId()));
        }
    }

    public interface Callback {
        void setBgColor(int _color);
        void showError(String message);

        Callback EMPTY_CALLBACK = new Callback() {
            @Override
            public void setBgColor(int _color) {}

            @Override
            public void showError(String message) {}
        };
    }
}
