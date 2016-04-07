package com.testapp.weather.util.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.testapp.weather.adapter.util.ListConfig;
import com.testapp.weather.util.ForecastUtils;
import com.testapp.weather.util.LogHelper;

/**
 * Helper class which accumulate all custom BindingConversion and BindingAdapter,
 * which are used in the project
 */
public class Converters {
    private static final String LOG_TAG = LogHelper.makeLogTag(Converters.class);

    @BindingConversion
    public static boolean convertBindableToBoolean(BindableBoolean bindableBoolean) {
        return bindableBoolean.get();
    }

    @BindingConversion
    public static int convertBooleanToInt(boolean value) {
        return value ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter({"listConfig"})
    public static void configRecyclerView(final RecyclerView _recyclerView,
                                          final ListConfig _config) {
        _config.applyConfig(_recyclerView);
    }

    @BindingAdapter({"actionHandler", "actionType", "model"})
    public static void onActionClick(final View _view, final OnActionClickListener _listener,
                                     final String _clickAction, final Object _model) {
        if (_listener == null) return;
        _view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _listener.onActionFired(_view, _clickAction, _model);
            }
        });
    }

    @BindingAdapter(value = {"backgroundColorResId", "applyColorToParent"}, requireAll = false)
    public static void setBackgroundColorResId(final View _view, final @ColorRes int _colorResId, final boolean _applyToParent) {
        final int color = ContextCompat.getColor(_view.getContext(), _colorResId);

        final ViewParent parent = _view.getParent();
        if (_applyToParent && parent != null && parent instanceof View) {
            ((View) parent).setBackgroundColor(color);
        } else {
            _view.setBackgroundColor(color);
        }
    }

}