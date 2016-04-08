package com.testapp.weather.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.RelativeSizeSpan;

import com.testapp.weather.R;
import com.testapp.weather.model.Temp;
import com.testapp.weather.model.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.testapp.weather.util.ForecastUtils.WeatherCondition.*;

/**
 * Helper class to simplify work with displaying weather
 * Created by d_rom on 25.12.2015.
 */
public class ForecastUtils {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("cccc, d MMMM", Locale.getDefault());
    public static final DateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("cc, d MMMM", Locale.getDefault());
    public static final int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    public enum WeatherCondition {
        THUNDERSTORM(R.drawable.ic_weather_lightning, R.color.colorWeatherStorm),
        DRIZZLE(R.drawable.ic_weather_drizzle, R.color.colorWeatherDrizzle),
        RAIN(R.drawable.ic_weather_rain, R.color.colorWeatherPouring),
        SNOW(R.drawable.ic_weather_snow, R.color.colorWeatherSnowy),
        FOG(R.drawable.ic_weather_fog, R.color.colorWeatherFog),
        HAIL(R.drawable.ic_weather_hail, R.color.colorWeatherHail),
        CLEAR(R.drawable.ic_weather_clear, R.color.colorWeatherClear),
        CLOUDS(R.drawable.ic_weather_cloudy, R.color.colorWeatherCloudy),
        PARTLY_CLOUDS(R.drawable.ic_weather_partlycloudy, R.color.colorWeatherCloudy),
        STORM(R.drawable.ic_weather_windy, R.color.colorWeatherWindy);

        private final int mIconResId;
        private final int mColorResId;

        WeatherCondition(@DrawableRes int _iconResId, @ColorRes int _colorResId) {
            mIconResId = _iconResId;
            mColorResId = _colorResId;
        }

        public int getIconResId() {
            return mIconResId;
        }

        public int getColorResId() {
            return mColorResId;
        }
    }

    // See http://openweathermap.org/weather-conditions
    public static WeatherCondition getWeatherCondition(List<Weather> weather) {
        if (weather == null || weather.isEmpty()) return CLEAR;
        final int weatherId = weather.get(0).id;

        if (weatherId >= 200 && weatherId < 300) { //Thunderstorm
            return THUNDERSTORM;
        } else if (weatherId >= 300 && weatherId < 400) { //Drizzle
            return DRIZZLE;
        } else if (weatherId >= 500 && weatherId < 600) { //Rain
            return RAIN;
        } else if (weatherId >= 600 && weatherId < 700 || weatherId == 903) { //Snow
            return SNOW;
        } else if (weatherId >= 701 && weatherId <= 761) { //Fog
            return FOG;
        } else if (weatherId == 761 || weatherId == 781
                || (weatherId >= 900 && weatherId <= 902) || weatherId == 905
                || (weatherId >= 956 && weatherId <= 962)) { //storm
            return STORM;
        } else if (weatherId == 800 || weatherId == 904 || (weatherId >= 951 && weatherId <= 955)) { //Clear
            return CLEAR;
        } else if (weatherId == 801) { //few clouds
            return PARTLY_CLOUDS;
        } else if (weatherId >= 802 && weatherId <= 804) { //Clouds
            return CLOUDS;
        } else if (weatherId == 906) { //hail
            return HAIL;
        }
        return CLEAR;
    }

    public static String getWindDirection(Integer windDirection) {
        if (windDirection == null) return "";
        // From wind direction in degrees, determine compass direction as a string (e.g NW)
        String direction = "";
        if (windDirection >= 337.5 || windDirection < 22.5) {
            direction = "N";
        } else if (windDirection >= 22.5 && windDirection < 67.5) {
            direction = "NE";
        } else if (windDirection >= 67.5 && windDirection < 112.5) {
            direction = "E";
        } else if (windDirection >= 112.5 && windDirection < 157.5) {
            direction = "SE";
        } else if (windDirection >= 157.5 && windDirection < 202.5) {
            direction = "S";
        } else if (windDirection >= 202.5 && windDirection < 247.5) {
            direction = "SW";
        } else if (windDirection >= 247.5 && windDirection < 292.5) {
            direction = "W";
        } else if (windDirection >= 292.5 || windDirection < 337.5) {
            direction = "NW";
        }
        return direction;
    }

    public static CharSequence getRelativeDate(Context _context, Long timeMillis) {
        if (timeMillis == null) return "";
        final CharSequence relativeDate;
        if (DateUtils.isToday(timeMillis)) {
            relativeDate = _context.getString(R.string.date_format_today,
                    DATE_FORMAT_SHORT.format(new Date(timeMillis)));
        } else if (DateUtils.isToday(timeMillis - MILLIS_IN_DAY)) {
            relativeDate = _context.getString(R.string.relative_date_tomorrow,
                    DATE_FORMAT_SHORT.format(new Date(timeMillis)));
        } else {
            relativeDate = DATE_FORMAT.format(new Date(timeMillis));
        }
        return relativeDate;
    }

    public static SpannableStringBuilder getMaxMinTemp(Context _context, Temp temp) {
        final SpannableStringBuilder sb = new SpannableStringBuilder();
        if (temp != null) {
            String maxTemp = _context.getString(R.string.format_temp_short, temp.max);
            String minTemp = _context.getString(R.string.format_temp_short, temp.min);

            sb.append(maxTemp).setSpan(new RelativeSizeSpan(2f), 0, maxTemp.length(), 0);
            sb.append(minTemp);
        }
        return sb;
    }
}
