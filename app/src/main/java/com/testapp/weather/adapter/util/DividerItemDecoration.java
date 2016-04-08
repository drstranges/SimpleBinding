package com.testapp.weather.adapter.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.testapp.weather.R;

/**
 * Provide divider in the RecyclerView with LinearLayoutManager
 * Created on 18.01.2016
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int SPACE_LEFT = 1;
    public static final int SPACE_RIGHT = 2;
    public static final int SPACE_TOP = 4;
    public static final int SPACE_BOTTOM = 8;

    private final int mSpacingConfig;

    private int mSpacing;

    public DividerItemDecoration(final int spacing) {
        mSpacing = spacing;
        mSpacingConfig = SPACE_TOP;
    }

    public DividerItemDecoration(final int spacing, int spacingConfig) {
        mSpacing = spacing;
        mSpacingConfig = spacingConfig;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (check(SPACE_LEFT)) outRect.left = mSpacing;
        if (check(SPACE_TOP)) outRect.top = mSpacing;
        if (check(SPACE_RIGHT)) outRect.right = mSpacing;
        if (check(SPACE_BOTTOM)) outRect.bottom = mSpacing;
    }

    private boolean check(int value) {
        return (mSpacingConfig & value) == value;
    }
}
