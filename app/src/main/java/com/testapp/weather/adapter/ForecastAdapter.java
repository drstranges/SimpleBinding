package com.testapp.weather.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.testapp.weather.R;
import com.testapp.weather.adapter.util.BindingHolder;
import com.testapp.weather.databinding.ItemForecastBinding;
import com.testapp.weather.model.ForecastItem;
import com.testapp.weather.util.handler.ActionClickListener;

import java.util.List;

/**
 * Created on 25.12.2015.
 */
public final class ForecastAdapter extends RecyclerView.Adapter<BindingHolder> {

    protected List<ForecastItem> mDataSource;
    private final ActionClickListener mActionClickListener;

    public ForecastAdapter(final List<ForecastItem> _dataSource, ActionClickListener _clickListener) {
        mDataSource = _dataSource;
        mActionClickListener = _clickListener;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup _parent, int _viewType) {
        final ItemForecastBinding binding = DataBindingUtil.inflate(LayoutInflater.from(_parent.getContext()),
                R.layout.item_forecast, _parent, false);
        binding.setActionHandler(mActionClickListener);
        return new BindingHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(final BindingHolder _holder, final int _position) {
        final ForecastItem item = mDataSource.get(_position);

        final ItemForecastBinding binding = (ItemForecastBinding) _holder.getBinding();
        binding.setForecast(item);
        binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return mDataSource.get(position).dt;
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

}
