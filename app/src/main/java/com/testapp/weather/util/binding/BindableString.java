package com.testapp.weather.util.binding;

import android.databinding.BaseObservable;
/**
 * Two way bindable string
 * Created by roman.donchenko on 18.01.2016
 */
public class BindableString extends BaseObservable {
    String value;

    public BindableString() {}

    public BindableString(String value) {
        this.value = value;
    }

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (this.value != value && (this.value == null || !this.value.equals(value))) {
            this.value = value;
            notifyChange();
        }
    }

}
