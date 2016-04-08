package com.testapp.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.text.TextUtils;

import com.testapp.weather.R;

/**
 * Helper class for working with preferences
 * Created on 24.12.2015.
 */
public final class PrefUtils {

    private static SharedPreferences sPreferences;
    public static ObservableField<String> sLocation = new ObservableField<>();

    public static void setPreferredLocation(Context context, String location) {
        sLocation.set(TextUtils.isEmpty(location) ? null : location);
        final SharedPreferences pref = getDefaultSharedPreferences(context);
        pref.edit().putString(context.getString(R.string.pref_location_key), location).apply();
    }

    public static String getPreferredLocation(Context context) {
        final SharedPreferences pref = getDefaultSharedPreferences(context);
        String location = pref.getString(
                context.getString(R.string.pref_location_key),
                context.getString(R.string.pref_location_default));
        sLocation.set(TextUtils.isEmpty(location) ? null : location);
        return location;
    }

    protected static SharedPreferences getDefaultSharedPreferences(Context _context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());
        }
        return sPreferences;
    }

    public static void onPreferenceChange(Preference preference, Object value) {
        String locationKey = preference.getContext().getString(R.string.pref_location_key);
        if (locationKey.equals(preference.getKey())) {
            sLocation.set(value.toString());
        }
    }
}
