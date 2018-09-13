package com.example.simengzhu.korvus;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;

public interface TodoItemTouchHelperAdapter {
    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
     */
    void onItemMove(int fromPosition, int toPosition);


    /**
     * Called when an item has been dismissed by a swipe.<br/>
     */
    void onItemDismiss(int position);

}
