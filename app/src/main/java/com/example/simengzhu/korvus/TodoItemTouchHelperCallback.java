package com.example.simengzhu.korvus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class TodoItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final TodoAdapter mAdapter;
    private Paint clearPaint;
//    private ColorDrawable background;
    private TodoTaskViewModel mTodoTaskViewModel;

    public TodoItemTouchHelperCallback(TodoItemTouchHelperAdapter mAdapter, TodoTaskViewModel mTodoTaskViewModel) {
        this.mAdapter = (TodoAdapter) mAdapter;
        this.clearPaint = new Paint();
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        this.background = new ColorDrawable();
        this.mTodoTaskViewModel = mTodoTaskViewModel;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int todoItemAmount = mAdapter.getItemCount();
        TodoDBTask first = mAdapter.getTodoDBTask(viewHolder.getAdapterPosition());
        TodoDBTask second = mAdapter.getTodoDBTask(target.getAdapterPosition());
        mTodoTaskViewModel.updateOrder(first.getPosition(), mTodoTaskViewModel.getNumOfRows());
        mTodoTaskViewModel.updateOrder(first.getPosition(), second.getPosition());
        mTodoTaskViewModel.updateOrder(second.getPosition(), first.getPosition());
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        mTodoTaskViewModel.delete(mAdapter.getTodoDBTask(viewHolder.getAdapterPosition()));
    }

//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        View itemView = viewHolder.itemView;
//        int itemHeight = itemView.getBottom() - itemView.getTop();
//        boolean isCanceled = dX == 0f && !isCurrentlyActive;
//
//        if (isCanceled) {
//            clearCanvas(c, (float)itemView.getLeft(), (float)itemView.getTop(), (float)itemView.getRight(), (float)itemView.getBottom());
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            return;
//        }
//
//        // Draw the red delete background
//        background.setColor(Color.parseColor("#"+Integer.toHexString(R.color.colorRemoveItem)));
//        background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        background.draw(c);
//
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }

    private void clearCanvas(Canvas c, float left, float top, float right, float bottom) {
        c.drawRect(left, top, right, bottom, clearPaint);
    }

}
