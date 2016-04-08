package com.testapp.weather.util.handler;

import android.view.View;

import com.testapp.weather.util.handler.action.Action;
import com.testapp.weather.util.handler.action.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Use ActionHandler to manage action and bind them to view
 * Created by roman.donchenko on 18.01.2016
 */
public class ActionHandler implements ActionClickListener {

    protected List<ActionPair> mActions;
    protected OnActionFiredListener mOnActionFiredListener;
    private ActionClickListener mOnActionClickListener;

    protected ActionHandler(List<ActionPair> _actions) {
        mActions = _actions;
    }

    public void setOnActionFiredListener(OnActionFiredListener _onActionFiredListener) {
        OnActionFiredListener oldListener = null;
        if (mOnActionFiredListener != null) oldListener = mOnActionFiredListener;
        mOnActionFiredListener = _onActionFiredListener;
        for (ActionPair actionPair : mActions) {
            if (actionPair.action instanceof BaseAction) {
                BaseAction baseAction = ((BaseAction) actionPair.action);
                baseAction.removeActionFireListener(oldListener);
                baseAction.addActionFiredListener(mOnActionFiredListener);
            }
        }
    }

    public boolean canHandle(final String _action) {

        for (ActionPair actionPair : mActions) {
            if (actionPair.actionType.equals(_action)) return true;
        }
        return false;
    }

    @Override
    public void onActionClick(View view, String actionType, Object model) {

        if (mOnActionClickListener != null)
            mOnActionClickListener.onActionClick(view, actionType, model);

        for (ActionPair actionPair : mActions) {
            if (actionPair.actionType.equals(actionType)) {
                if (actionPair.action != null && actionPair.action.isModelAccepted(model)) {
                    //noinspection unchecked
                    actionPair.action.onActionFire(view, actionType, model);
                }
            }
        }
    }

    public void setOnActionClickListener(ActionClickListener actionClickListener) {
        mOnActionClickListener = actionClickListener;
    }

    public static final class Builder {
        protected List<ActionPair> mActions;

        public Builder() {
            mActions = new ArrayList<>();
        }

        public Builder addAction(String actionType, Action action) {
            mActions.add(new ActionPair(actionType, action));
            return this;
        }

        public ActionHandler build() {
            return new ActionHandler(mActions);
        }
    }

    public static final class SimpleBuilder {
        protected Builder mBuilder;
        private OnActionFiredListener mActionFiredListener;

        public SimpleBuilder() {
            mBuilder = new ActionHandler.Builder();
        }

        public ActionHandler build() {
            final ActionHandler actionHandler = mBuilder.build();
            if (mActionFiredListener != null) {
                actionHandler.setOnActionFiredListener(mActionFiredListener);
            }
            return actionHandler;
        }

//        public SimpleBuilder addActionFollow() {
//            mBuilder.addAction(BaseActionType.FOLLOW, new FollowAction());
//            return this;
//        }

        public SimpleBuilder setActionListener(final OnActionFiredListener _actionFiredListener) {
            mActionFiredListener = _actionFiredListener;
            return this;
        }
    }

    private static class ActionPair {
        public String actionType;
        public Action action;

        public ActionPair(String actionType, Action action) {
            this.actionType = actionType;
            this.action = action;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ActionPair that = (ActionPair) o;

            if (!actionType.equals(that.actionType)) return false;
            return action.equals(that.action);

        }

        @Override
        public int hashCode() {
            int result = actionType.hashCode();
            result = 31 * result + action.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "ActionPair{" +
                    "actionType='" + actionType + '\'' +
                    ", action=" + action +
                    '}';
        }
    }
}
