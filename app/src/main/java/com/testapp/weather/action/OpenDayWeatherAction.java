package com.testapp.weather.action;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import com.testapp.weather.R;
import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.util.handler.action.IntentAction;
import com.testapp.weather.view.ChildActivity;
import com.testapp.weather.view.fragment.DayFragment;

/**
 * Created on 08.04.2016.
 */
public class OpenDayWeatherAction extends IntentAction<ForecastItem> {

    @Nullable
    @Override
    protected Intent getIntent(View view, Context context, String actionType, ForecastItem model) {
        return ChildActivity.getIntent(context, DayFragment.class, DayFragment.buildArgs(model));
    }

    @Override
    public boolean isModelAccepted(Object model) {
        return model instanceof ForecastItem;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected ActivityOptionsCompat prepareTransition(Intent _intent, View _view, ForecastItem model) {
        Context context = _view.getContext();
        ActivityOptionsCompat options = null;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            ViewGroup group = (ViewGroup) _view;
            View view = group.findViewById(R.id.ivIcon);
            String transName = "transition_" + model.dt;
            ViewCompat.setTransitionName(view, transName);
            options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, transName);
        }
        return options;
    }
}
