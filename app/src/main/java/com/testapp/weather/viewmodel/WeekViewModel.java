package com.testapp.weather.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;

import com.testapp.weather.action.OpenDayWeatherAction;
import com.testapp.weather.adapter.ForecastAdapter;
import com.testapp.weather.adapter.util.ListConfig;
import com.testapp.weather.model.Forecast;
import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.network.RestClient;
import com.testapp.weather.util.PrefUtils;
import com.testapp.weather.util.handler.ActionClickListener;
import com.testapp.weather.util.handler.ActionHandler;
import com.testapp.weather.util.handler.ActionType;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created on 25.12.2015.
 */
public class WeekViewModel extends BaseViewModel {

    public static final int FORECAST_DAY_COUNT = 16;

    private Callback mCallback;
    public ListConfig listConfig;
    public ForecastAdapter mAdapter;
    public ObservableBoolean isEmptyMessageVisible = new ObservableBoolean(false);
    public ObservableBoolean isLoading = new ObservableBoolean();

    private final List<ForecastItem> mItems;
    private Subscription mSubscription;

    public WeekViewModel(Context context, Callback callback) {
        super(context);
        mCallback = callback != null ? callback : Callback.EMPTY_CALLBACK;
        mItems = new ArrayList<>(7);
        mAdapter = new ForecastAdapter(mItems, createActionHandler());
        listConfig = getListConfig();
        loadData();
    }

    private ActionClickListener createActionHandler() {
        return new ActionHandler.Builder()
                .addAction(ActionType.OPEN, new OpenDayWeatherAction())
                .build();
    }

    protected ListConfig getListConfig() {
        ListConfig.Builder builder = new ListConfig.Builder(mAdapter)
                .setHasFixedSize(true)
                .setDefaultDividerEnabled(true);
        return builder.build(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe(mSubscription);
        isLoading.set(false);
        mCallback = null;
    }

    public void onRefresh() {
        loadData();
    }

    private void loadData(){
        unsubscribe(mSubscription);
        isLoading.set(true);
        final String location = PrefUtils.getPreferredLocation(mContext);
        mSubscription = RestClient.getApi()
                .getDailyForecast(location, FORECAST_DAY_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Forecast>() {
                    @Override
                    public void onCompleted() {
                        isLoading.set(false);
                        mAdapter.notifyDataSetChanged();
                        refreshStatus();
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoading.set(false);
                        mCallback.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Forecast forecast) {
                        mItems.clear();
                        mItems.addAll(forecast.list);
                    }
                });
    }


    private void refreshStatus() {
        isEmptyMessageVisible.set(mItems.isEmpty());
    }


    public interface Callback {

        void showError(String message);

        Callback EMPTY_CALLBACK = new Callback() {

            @Override
            public void showError(String message) {

            }
        };
    }
}
