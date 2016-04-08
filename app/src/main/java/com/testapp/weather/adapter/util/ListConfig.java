package com.testapp.weather.adapter.util;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.testapp.weather.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class to simplify adapter configuration
 * Created by d_rom on 25.12.2015.
 */
public final class ListConfig {
    private final RecyclerView.Adapter mAdapter;
    private final LayoutManagerProvider mLayoutManagerProvider;
    private final List<RecyclerView.ItemDecoration> mItemDecorations;
    private final List<RecyclerView.OnScrollListener> mScrollListeners;
    private final boolean mHasFixedSize;

    public ListConfig(RecyclerView.Adapter adapter, LayoutManagerProvider layoutManagerProvider,
                      List<RecyclerView.ItemDecoration> itemDecorations, List<RecyclerView.OnScrollListener> scrollListeners,
                      boolean hasFixedSize) {
        mAdapter = adapter;
        mLayoutManagerProvider = layoutManagerProvider;
        mItemDecorations = itemDecorations != null
                ? itemDecorations : Collections.<RecyclerView.ItemDecoration>emptyList();
        mScrollListeners = scrollListeners != null
                ? scrollListeners : Collections.<RecyclerView.OnScrollListener>emptyList();
        mHasFixedSize = hasFixedSize;
    }

    public void applyConfig(RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager;
        final Context context = recyclerView.getContext();
        if (mAdapter == null || mLayoutManagerProvider == null || (layoutManager = mLayoutManagerProvider.get(context)) == null) return;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(mHasFixedSize);
        recyclerView.setAdapter(mAdapter);
        for (RecyclerView.ItemDecoration itemDecoration : mItemDecorations) {
            recyclerView.addItemDecoration(itemDecoration);
        }
        for (RecyclerView.OnScrollListener scrollListener : mScrollListeners) {
            if (scrollListener instanceof EndlessOnScrollListener)
                ((EndlessOnScrollListener) scrollListener).setRecyclerView(recyclerView);
            recyclerView.addOnScrollListener(scrollListener);
        }
    }

    public static class Builder {
        private final RecyclerView.Adapter mAdapter;
        private LayoutManagerProvider mLayoutManagerProvider;
        private List<RecyclerView.ItemDecoration> mItemDecorations;
        private List<RecyclerView.OnScrollListener> mOnScrollListeners;
        private boolean mHasFixedSize;
        private int mDefaultDividerOffset = -2;

        public Builder(RecyclerView.Adapter adapter) {
            mAdapter = adapter;
        }

        public Builder setLayoutManagerProvider(LayoutManagerProvider layoutManagerProvider) {
            mLayoutManagerProvider = layoutManagerProvider;
            return this;
        }

        public Builder addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
            if (mItemDecorations == null) {
                mItemDecorations = new ArrayList<>();
            }
            mItemDecorations.add(itemDecoration);
            return this;
        }

        public Builder addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            if (mOnScrollListeners == null) {
                mOnScrollListeners = new ArrayList<>();
            }
            mOnScrollListeners.add(onScrollListener);
            return this;
        }

        public Builder setHasFixedSize(boolean hasFixedSize) {
            mHasFixedSize = hasFixedSize;
            return this;
        }

        public Builder setDefaultDividerEnabled(boolean enabled) {
            mDefaultDividerOffset = enabled ? -1 : -2;
            return this;
        }

        public Builder setDefaultDividerOffset(int offset) {
            mDefaultDividerOffset = offset;
            return this;
        }

        public ListConfig build(Context context) {
            if (mLayoutManagerProvider == null) mLayoutManagerProvider = new SimpleLinearLayoutManagerProvider();
            if (mDefaultDividerOffset >= -1) {
                if (mDefaultDividerOffset == -1) mDefaultDividerOffset = context.getResources()
                        .getDimensionPixelSize(R.dimen.default_list_divider_size);
                addItemDecoration(new DividerItemDecoration(mDefaultDividerOffset));
            }
            return new ListConfig(
                    mAdapter,
                    mLayoutManagerProvider,
                    mItemDecorations,
                    mOnScrollListeners,
                    mHasFixedSize);
        }
    }

    public interface LayoutManagerProvider {
        RecyclerView.LayoutManager get(Context context);
    }


    public static class SimpleLinearLayoutManagerProvider implements LayoutManagerProvider {
        @Override
        public RecyclerView.LayoutManager get(Context context) {
            return new LinearLayoutManager(context);
        }
    }

    public static class SimpleGridLayoutManagerProvider implements LayoutManagerProvider {
        private final int mSpanCount;
        private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

        public SimpleGridLayoutManagerProvider(@IntRange(from = 1) int mSpanCount) {
            this.mSpanCount = mSpanCount;
        }

        public SimpleGridLayoutManagerProvider(int spanCount, GridLayoutManager.SpanSizeLookup spanSizeLookup) {
            mSpanCount = spanCount;
            mSpanSizeLookup = spanSizeLookup;
        }

        @Override
        public RecyclerView.LayoutManager get(Context context) {
            GridLayoutManager layoutManager = new GridLayoutManager(context, mSpanCount);
            if (mSpanSizeLookup != null) layoutManager.setSpanSizeLookup(mSpanSizeLookup);
            return layoutManager;
        }
    }

    public static class SimpleStaggeredGridLayoutManagerProvider implements LayoutManagerProvider {
        private final int mSpanCount;

        public SimpleStaggeredGridLayoutManagerProvider(@IntRange(from = 1) int mSpanCount) {
            this.mSpanCount = mSpanCount;
        }

        @Override
        public RecyclerView.LayoutManager get(Context context) {
            return new StaggeredGridLayoutManager(mSpanCount, StaggeredGridLayoutManager.VERTICAL);
        }
    }
}
