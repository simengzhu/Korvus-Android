package com.example.simengzhu.korvus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<String> todoDataset;

    public TodoAdapter(ArrayList<String> todoDataset) {
        this.todoDataset = todoDataset;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.todoTitle.setText(todoDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return todoDataset.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        public TextView todoTitle;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            todoTitle = (TextView) itemView.findViewById(R.id.todo_description);

        }
    }
}
