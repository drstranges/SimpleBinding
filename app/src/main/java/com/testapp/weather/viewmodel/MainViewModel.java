package com.testapp.weather.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.testapp.weather.R;
import com.testapp.weather.util.LocationLoader;
import com.testapp.weather.util.PermissionHelper;
import com.testapp.weather.util.PrefUtils;

/**
 * Created on 25.12.2015.
 */
public class MainViewModel extends BaseViewModel {

    private static final int REQUEST_CODE_PERMISSIONS = 1;

    private Callback mCallback;


    public MainViewModel(Context context, Callback callback) {
        super(context);
        mCallback = callback != null ? callback : Callback.EMPTY_CALLBACK;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
    }

    public void performSync() {
        PrefUtils.init(mContext); // Can be called in Application instead
        String location = PrefUtils.getPreferredLocation(mContext);
        if (TextUtils.isEmpty(location)) {
            findLocation();
        }
    }


    private void findLocation() {
        try {
            LocationLoader.launch(mContext);
        } catch (PermissionHelper.PermissionSecurityException e) {
            e.printStackTrace();
            mCallback.requestPermissions(REQUEST_CODE_PERMISSIONS, e.getRequiredPermissions());
        }
    }


    public boolean onRequestPermissionResult(int _requestCode, String[] _permissions, int[] _grantResults) {
        if (REQUEST_CODE_PERMISSIONS == _requestCode) {
            if (PermissionHelper.isSomePermissionGranted(_grantResults)) {
                findLocation();
            }
            return true;
        }
        return false;
    }


    public interface Callback {
        void requestPermissions(int _requestCode, String[] _requiredPermissions);
        void showError(String message);

        Callback EMPTY_CALLBACK = new Callback() {
            @Override
            public void showError(String message) {}

            @Override
            public void requestPermissions(int _requestCode, String[] _requiredPermissions) {}

        };
    }
}
