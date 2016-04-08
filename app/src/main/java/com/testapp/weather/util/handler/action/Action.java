package com.testapp.weather.util.handler.action;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by roman.donchenko on 18.01.2016
 */
public interface Action<M> {

    void onActionFire(@Nullable View view, @Nullable String actionType, @Nullable M model);

    boolean isModelAccepted(Object model);
}
