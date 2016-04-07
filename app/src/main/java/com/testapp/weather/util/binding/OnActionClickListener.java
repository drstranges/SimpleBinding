package com.testapp.weather.util.binding;

import android.view.View;

/**
 * Used in bindings to fire action
 * Created by d_rom on 25.12.2015.
 */
public interface OnActionClickListener {
    void onActionFired(View _view, String _actionType, Object _model);
}
