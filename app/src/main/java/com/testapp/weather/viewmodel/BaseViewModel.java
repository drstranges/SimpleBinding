package com.testapp.weather.viewmodel;

import android.content.Context;
import android.support.annotation.CallSuper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created on 08.04.2016.
 */
public class BaseViewModel implements ViewModel {

    protected Context mContext;
    private CompositeSubscription mSubscriptions;

    public BaseViewModel(Context context) {
        this.mContext = context;
    }

    @CallSuper
    @Override
    public void onDestroy() {
        unsubscribe(mSubscriptions);
        mContext = null;
    }

    protected Context getContext() {
        return mContext;
    }

    protected String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Use this method for add your subscription to managed CompositeSubscription,
     * which was unsubscribed automatically inside onDestroy
     * @param subscription    your subscription
     * @return the same subscription as {@param subscription}
     */
    protected Subscription manageSubscription(Subscription subscription) {
        if (mSubscriptions == null) mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(subscription);
        return subscription;
    }

    protected void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
