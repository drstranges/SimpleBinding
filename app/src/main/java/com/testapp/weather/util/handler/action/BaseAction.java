package com.testapp.weather.util.handler.action;

import com.testapp.weather.util.handler.OnActionFiredListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Use as parent for other Action
 * Created by roman.donchenko on 18.01.2016
 */
public abstract class BaseAction<M> implements Action<M> {
    protected List<OnActionFiredListener> mActionFiredListeners = new ArrayList<>(1);

    public void addActionFiredListener(OnActionFiredListener listener) {
        if (listener != null) mActionFiredListeners.add(listener);
    }

    public void removeActionFireListener(OnActionFiredListener listener) {
        if (listener != null) mActionFiredListeners.remove(listener);
    }

    public void clearActionFireListeners() {
        mActionFiredListeners.clear();
    }

    public void notifyOnActionFired(String actionType, M oldModel) {
        for (OnActionFiredListener listener : mActionFiredListeners) {
            listener.onClickActionFired(actionType, oldModel);
        }
    }
}