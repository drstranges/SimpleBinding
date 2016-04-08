package com.testapp.weather.storage;

import android.databinding.ObservableField;

import static com.testapp.weather.util.ForecastUtils.WeatherCondition;
import static com.testapp.weather.util.ForecastUtils.WeatherCondition.RAIN;

/**
 * This is dummy class replacement of or something like database helper
 * Created on 09.04.2016.
 */
public final class DataStorage {

    public static ObservableField<WeatherCondition> currentCondition = new ObservableField<>(RAIN);

    public static void storeCurrentWeather(WeatherCondition weatherCondition) {
        currentCondition.set(weatherCondition);
        // save
    }

}
