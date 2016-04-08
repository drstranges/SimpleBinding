package com.testapp.weather.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.widget.Toast;

import com.testapp.weather.R;
import com.testapp.weather.databinding.FragmentDayBinding;
import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.network.RestClient;
import com.testapp.weather.util.ForecastUtils;
import com.testapp.weather.viewmodel.DayViewModel;

/**
 * Created on 23.12.2015.
 */
public class DayFragment extends BaseFragment<DayViewModel, FragmentDayBinding> implements DayViewModel.Callback {
    public static final String ARG_FORECAST = "ARG_FORECAST";

    private ForecastItem mItem;

    @NonNull
    public static Bundle buildArgs(ForecastItem item) {
        final Bundle args = new Bundle();
        args.putString(ARG_FORECAST, RestClient.getGson().toJson(item));
        return args;
    }

    @Override
    protected void onInitArgs() {
        getActivity().postponeEnterTransition();
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_FORECAST)) {
            String item = args.getString(ARG_FORECAST);
            mItem = RestClient.getGson().fromJson(item, ForecastItem.class);
        }
    }

    @Override
    protected void onBindViewModel(FragmentDayBinding _binding, DayViewModel _viewModel) {
        super.onBindViewModel(_binding, _viewModel);
        _binding.executePendingBindings();
        getActivity().startPostponedEnterTransition();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Long timeMillis;
        if (mItem != null){
            timeMillis = mItem.dt;
            int color = ContextCompat.getColor(getContext(),
                    ForecastUtils.getWeatherCondition(mItem.weather).getColorResId());
            setToolbarColor(color);
        } else {
            timeMillis = System.currentTimeMillis();
        }
        setTitle(ForecastUtils.getRelativeDate(getContext(), timeMillis));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    protected DayViewModel createViewModel() {
        return new DayViewModel(getContext(), mItem, this);
    }

    @Override
    public void setBgColor(int _color) {
        setToolbarColor(_color);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
