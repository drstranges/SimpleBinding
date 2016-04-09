package com.testapp.weather.viewmodel;

import android.content.Context;
import android.databinding.Observable;
import android.text.TextUtils;

import com.testapp.weather.R;
import com.testapp.weather.util.PrefUtils;
import com.testapp.weather.util.binding.BindableString;

/**
 * Created on 09.04.2016.
 */
public class ProfileViewModel extends BaseViewModel {

    public BindableString nickname;

    public ProfileViewModel(Context context) {
        super(context);
        nickname = new BindableString(PrefUtils.getNickName(context));
        if (TextUtils.isEmpty(nickname.get())) nickname.set(getString(R.string.unknown_nickname));
        nickname.addOnPropertyChangedCallback(mChangedCallback);
    }

    private final Observable.OnPropertyChangedCallback mChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            PrefUtils.setNickName(mContext, nickname.get());
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        nickname.removeOnPropertyChangedCallback(mChangedCallback);
    }
}
