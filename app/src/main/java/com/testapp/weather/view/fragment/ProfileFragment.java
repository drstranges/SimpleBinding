package com.testapp.weather.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testapp.weather.R;
import com.testapp.weather.databinding.FragmentProfileBinding;
import com.testapp.weather.util.KeyboardUtils;
import com.testapp.weather.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment<ProfileViewModel, FragmentProfileBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected ProfileViewModel createViewModel() {
        return new ProfileViewModel(getContext());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarColor(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        KeyboardUtils.hideSoftKeyboard(getActivity());
    }
}
