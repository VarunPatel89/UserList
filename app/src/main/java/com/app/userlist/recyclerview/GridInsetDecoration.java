package com.app.userlist.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridInsetDecoration extends RecyclerView.ItemDecoration {

    private int insetHorizontal;
    private int insetVertical;

    public GridInsetDecoration(int insetHorizontal, int insetVertical) {
        this.insetHorizontal = insetHorizontal;
        this.insetVertical = insetVertical;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        GridLayoutManager.LayoutParams layoutParams
                = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        int position = layoutParams.getViewAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        // add edge margin only if item edge is not the grid edge
        int itemSpanIndex = layoutParams.getSpanIndex();
        // is left grid edge?
        outRect.left = itemSpanIndex == 0 ? 0 : insetHorizontal;
        // is top grid edge?
        outRect.top = itemSpanIndex == position ? 0 : insetVertical;
        outRect.right = 0;
        outRect.bottom = 0;
    }
}