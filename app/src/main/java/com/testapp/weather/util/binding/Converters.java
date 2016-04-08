package com.testapp.weather.util.binding;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

import com.testapp.weather.adapter.util.ListConfig;
import com.testapp.weather.util.LogHelper;
import com.testapp.weather.util.handler.ActionClickListener;

/**
 * Helper class which accumulate all custom BindingConversion and BindingAdapter,
 * which are used in the project
 */
public class Converters {
    private static final String LOG_TAG = LogHelper.makeLogTag(Converters.class);

    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }

    @BindingConversion
    public static String convertBindableToString(BindableString bindableString) {
        return bindableString != null ? bindableString.get() : "";
    }

    @BindingConversion
    public static int convertBooleanToInt(boolean value) {
        return value ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter({"listConfig"})
    public static void configRecyclerView(final RecyclerView recyclerView,
                                          final ListConfig config) {
        config.applyConfig(recyclerView);
    }

    @BindingAdapter(value = {"actionHandler", "actionType", "actionTypeLongClick", "model"}, requireAll = false)
    public static void onActionFired(final View view, final ActionClickListener listener, final String actionType, final String actionTypeLongClick, final Object model) {
        if (listener == null) return;

        if (actionType != null) {
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onActionClick(view, actionType, model);
                }
            });
        }

        if (actionTypeLongClick != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    listener.onActionClick(view, actionTypeLongClick, model);
                    return true;
                }
            });
        }
    }

    @BindingAdapter(value = {"backgroundColorResId", "applyColorToParent"}, requireAll = false)
    public static void setBackgroundColorResId(final View view, final @ColorRes int colorResId, final boolean applyToParent) {
        final int color = ContextCompat.getColor(view.getContext(), colorResId);

        final ViewParent parent = view.getParent();
        if (applyToParent && parent != null && parent instanceof View) {
            ((View) parent).setBackgroundColor(color);
        } else {
            view.setBackgroundColor(color);
        }
    }

}