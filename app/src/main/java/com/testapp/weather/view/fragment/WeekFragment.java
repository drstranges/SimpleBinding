package com.testapp.weather.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.testapp.weather.R;
import com.testapp.weather.databinding.FragmentWeekBinding;
import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.util.ForecastUtils;
import com.testapp.weather.view.ColorToolbarHolder;
import com.testapp.weather.view.Navigator;
import com.testapp.weather.viewmodel.WeekViewModel;

/**
 * Created on 23.12.2015.
 */
public class WeekFragment extends BaseFragment<WeekViewModel, FragmentWeekBinding> implements WeekViewModel.Callback {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_week;
    }

    @Override
    protected WeekViewModel createViewModel() {
        return new WeekViewModel(getContext(), this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarColor(null);
        getActivity().setTitle(R.string.menu_navigation_weather_week);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
