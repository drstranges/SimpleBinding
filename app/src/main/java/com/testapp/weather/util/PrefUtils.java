package com.testapp.weather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.testapp.weather.R;

/**
 * Helper class for working with preferences
 * Created on 24.12.2015.
 */
public final class PrefUtils {

    private static SharedPreferences sPreferences;

    public static void setPreferredLocation(Context _context, String _location) {
        final SharedPreferences pref = getDefaultSharedPreferences(_context);
        pref.edit().putString(_context.getString(R.string.pref_location_key), _location).apply();
    }

    public static String getPreferredLocation(Context _context) {
        final SharedPreferences pref = getDefaultSharedPreferences(_context);
        return pref.getString(
                _context.getString(R.string.pref_location_key),
                _context.getString(R.string.pref_location_default));
    }

    protected static SharedPreferences getDefaultSharedPreferences(Context _context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(_context.getApplicationContext());
        }
        return sPreferences;
    }
}
