package com.example.simengzhu.korvus;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> implements TodoItemTouchHelperAdapter {

    private ArrayList<TodoDBTask> todoDataset;
    private final LayoutInflater mInflater;

    public TodoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = mInflater.inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        if (todoDataset != null) {
            holder.todoTitle.setText(todoDataset.get(position).getTask());
        } else {
            holder.todoTitle.setText("No To-do items found in database today");
        }
    }

    @Override
    public int getItemCount() {
        if (todoDataset != null) {
            return todoDataset.size();
        }
        return 0;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todoDataset, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todoDataset, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        todoDataset.remove(position);
        notifyItemRemoved(position);
    }

    TodoDBTask getTodoDBTask(int position) {
        return todoDataset.get(position);
    }


    void setTodoDataset(ArrayList<TodoDBTask> newDataset) {
        this.todoDataset = newDataset;
        notifyDataSetChanged();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            todoTitle = (TextView) itemView.findViewById(R.id.todo_description);

        }
    }
}
