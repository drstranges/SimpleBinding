package com.testapp.weather.network;

import com.testapp.weather.BuildConfig;

/**
 * Contract for use OpenWeatherMap Api
 * Created on 23.12.2015.
 */
public final class OpenWeatherContract {

    public static final String ROOT_URL = "http://api.openweathermap.org/data/2.5/";

    public static final String PARAM_QUERY = "q";
    public static final String PARAM_FORMAT = "mode";
    public static final String PARAM_UNITS = "units";
    public static final String PARAM_DAYS = "cnt";
    public static final String PARAM_APPID = "APPID";

    public static final String BASE_PARAM = "?" + PARAM_FORMAT + "=json&"
            + PARAM_UNITS + "=metric&"
            + PARAM_APPID + "=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;

    public static final String METHOD_GET_DAILY_FORECAST = "forecast/daily" + BASE_PARAM;

//    public static final String METHOD_GET_DAY_WEATHER = "weather" + BASE_PARAM;
}
