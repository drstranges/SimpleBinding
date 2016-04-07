package com.testapp.weather.adapter.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class to simplify adapter configuration
 * Created by d_rom on 25.12.2015.
 */
public final class ListConfig {

    private final RecyclerView.Adapter mAdapter;
    private final RecyclerView.LayoutManager mLayoutManager;
    private final List<RecyclerView.ItemDecoration> mItemDecorations;
    private final List<RecyclerView.OnScrollListener> mScrollListeners;
    private final boolean mHasFixedSize;

    public ListConfig(RecyclerView.Adapter _adapter, RecyclerView.LayoutManager _layoutManager, List<RecyclerView.ItemDecoration> _itemDecorations, List<RecyclerView.OnScrollListener> _scrollListeners, boolean _hasFixedSize) {
        mAdapter = _adapter;
        mLayoutManager = _layoutManager;
        mItemDecorations = _itemDecorations != null
                ? _itemDecorations : Collections.<RecyclerView.ItemDecoration>emptyList();
        mScrollListeners = _scrollListeners != null
                ? _scrollListeners : Collections.<RecyclerView.OnScrollListener>emptyList();
        mHasFixedSize = _hasFixedSize;
    }

    public void applyConfig(RecyclerView _recyclerView) {
        if (mAdapter == null || mLayoutManager == null) return;
        _recyclerView.setAdapter(mAdapter);
        _recyclerView.setLayoutManager(mLayoutManager);
        _recyclerView.setHasFixedSize(mHasFixedSize);
        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorations) {
            _recyclerView.addItemDecoration(itemDecoration);
        }
        for (RecyclerView.OnScrollListener scrollListener : mScrollListeners) {
            if (scrollListener instanceof EndlessOnScrollListener)
                ((EndlessOnScrollListener) scrollListener).setRecyclerView(_recyclerView);
            _recyclerView.addOnScrollListener(scrollListener);
        }
    }

    public static class Builder {
        private final RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private List<RecyclerView.ItemDecoration> mItemDecorations;
        private List<RecyclerView.OnScrollListener> mOnScrollListeners;
        private boolean mHasFixedSize;
        private int mDefaultDividerSize = -2;

        public Builder(RecyclerView.Adapter _adapter) {
            mAdapter = _adapter;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager _layoutManager) {
            mLayoutManager = _layoutManager;
            return this;
        }

        public Builder addItemDecoration(RecyclerView.ItemDecoration _itemDecoration) {
            if (mItemDecorations == null) {
                mItemDecorations = new ArrayList<>();
            }
            mItemDecorations.add(_itemDecoration);
            return this;
        }

        public Builder addOnScrollListener(RecyclerView.OnScrollListener _onScrollListener) {
            if (mOnScrollListeners == null) {
                mOnScrollListeners = new ArrayList<>();
            }
            mOnScrollListeners.add(_onScrollListener);
            return this;
        }

        public Builder setHasFixedSize(boolean _isFixedSize) {
            mHasFixedSize = _isFixedSize;
            return this;
        }

        public Builder setDefaultDividerEnabled(boolean _enabled) {
            mDefaultDividerSize = _enabled ? -1 : -2;
            return this;
        }

        public Builder setDefaultDividerSize(int _offset) {
            mDefaultDividerSize = _offset;
            return this;
        }

        public ListConfig build(Context _context) {
            if (mLayoutManager == null) mLayoutManager = new LinearLayoutManager(_context);
            if (mDefaultDividerSize >= -1)
                addItemDecoration(new ItemDivider(_context, mDefaultDividerSize));
            return new ListConfig(
                    mAdapter,
                    mLayoutManager,
                    mItemDecorations,
                    mOnScrollListeners,
                    mHasFixedSize);
        }
    }
}

