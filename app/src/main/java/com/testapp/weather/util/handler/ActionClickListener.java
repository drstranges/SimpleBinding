package com.testapp.weather.util.handler;

import android.view.View;

/**
 * Used in bindings to fire action
 * Created by d_rom on 25.12.2015.
 */
public interface ActionClickListener {
    void onActionClick(View view, String actionType, Object model);
}
