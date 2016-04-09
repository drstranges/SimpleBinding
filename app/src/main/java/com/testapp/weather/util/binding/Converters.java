package com.testapp.weather.util.binding;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.testapp.weather.R;
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

    @BindingAdapter({"bidirectional"})
    public static void bindEditText(EditText view, final BindableString bindableString) {
        if(bindableString != null) {
            if(view.getTag(R.id.binded) == null) {
                view.setTag(R.id.binded, true);
                view.addTextChangedListener(new TextWatcher() {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        bindableString.set(s.toString());
                    }

                    public void afterTextChanged(Editable s) {}
                });
            }

            String newValue = bindableString.get();
            if(!view.getText().toString().equals(newValue)) {
                view.setText(newValue);
            }

        }
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

    @BindingAdapter(value = {"transitionName"})
    public static void setTransitionName(final View view, final String transitionName) {
        ViewCompat.setTransitionName(view, transitionName);
    }

    @BindingAdapter(value = {"glideSrc", "glidePlaceholder"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String glideSrc, Drawable placeholder) {
        Context context = imageView.getContext();
        if (context instanceof Activity && ((Activity) context).isFinishing()) return;

        boolean isEmptyPath = TextUtils.isEmpty(glideSrc);
        if (isEmptyPath) {
            if (placeholder != null) {
                imageView.setImageDrawable(placeholder);
            }
            return;
        }
        try {
            RequestManager glide = Glide.with(context);
            DrawableRequestBuilder request = glide.load(glideSrc);

            if (placeholder != null) {
                request.crossFade().placeholder(placeholder);
            }
            request.into(imageView);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}