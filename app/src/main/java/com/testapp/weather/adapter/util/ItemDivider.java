package com.testapp.weather.adapter.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.testapp.weather.R;

/**
 * Provide dvider in the RecyclerView with LinearLayoutManager
 * Created on 25.12.15.
 */
public class ItemDivider extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 2;

    private final int mLayoutOrientation;
    private int mSizeGridSpacingPx;

    public ItemDivider(Context _context, final int _dividerSize) {
        this(_context, _dividerSize, VERTICAL);
    }

    public ItemDivider(Context _context, final int _dividerSize, int _orientation) {
        mSizeGridSpacingPx = _dividerSize >= 0 ? _dividerSize : _context.getResources().getDimensionPixelSize(R.dimen.divider_size_list);
        mLayoutOrientation = _orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (itemPosition < 1) return;
        if ((mLayoutOrientation & VERTICAL) == VERTICAL){
            outRect.top = mSizeGridSpacingPx;
            outRect.bottom = 0;
        }
        if ((mLayoutOrientation & HORIZONTAL) == HORIZONTAL) {
            outRect.left = mSizeGridSpacingPx;
            outRect.right = 0;
        }

    }
}
