package com.testapp.weather.util.handler.action;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * Use for all action based on Intent
 * Created by roman.donchenko on 18.01.2016
 */
public abstract class IntentAction<M> extends BaseAction<M> {

    public static final String SIMPLE_TRANSITION = "SIMPLE_TRANSITION";

    @Override
    public void onActionFire(View view, String actionType, M model) {
        Context context = view.getContext();
        final Intent intent = getIntent(view, context, actionType, model);
        if (intent == null) return;
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityOptionsCompat activityOptions = prepareTransition(intent, view);
            if (activityOptions != null) {
                context.startActivity(intent, activityOptions.toBundle());
                return;
            }
        }
        notifyOnActionFired(actionType, model);
        context.startActivity(intent);
    }

    protected abstract @Nullable
    Intent getIntent(View view, Context context, String actionType, M model);

    protected ActivityOptionsCompat prepareTransition(Intent intent, View view) {
        return null;
    }
}
