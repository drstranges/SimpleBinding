package com.testapp.weather.network;

import android.support.annotation.IntRange;

import com.google.gson.Gson;
import com.testapp.weather.BuildConfig;
import com.testapp.weather.model.Forecast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.testapp.weather.network.OpenWeatherContract.METHOD_GET_DAILY_FORECAST;
import static com.testapp.weather.network.OpenWeatherContract.PARAM_DAYS;
import static com.testapp.weather.network.OpenWeatherContract.PARAM_QUERY;
import static com.testapp.weather.network.OpenWeatherContract.ROOT_URL;

public class RestClient {
    private static Api sApi;
    private static Gson sGson;

    public static Api getApi() {
        if (sApi == null) {
            sApi = createApi();
        }
        return sApi;
    }

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }

    private static Api createApi() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(loggingInterceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okBuilder.build())
                .build();
        return retrofit.create(Api.class);
    }

    public interface Api {

        @GET(METHOD_GET_DAILY_FORECAST)
        Observable<Forecast> getDailyForecast(@Query(PARAM_QUERY) final String _location,
                                              @Query(PARAM_DAYS) @IntRange(from = 1, to = 16) final int _days);
//        @GET(METHOD_GET_DAY_WEATHER)
//        Observable<Forecast> getDayForecast(@Query(PARAM_QUERY) final String _location);
    }
}