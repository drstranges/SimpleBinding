package com.testapp.weather.action;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.util.ForecastUtils;
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
}
