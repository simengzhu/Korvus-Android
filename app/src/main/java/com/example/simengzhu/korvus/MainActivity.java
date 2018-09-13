package com.example.simengzhu.korvus;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView todoRecyclerView;
    private TodoAdapter todoAdapter;
    private RecyclerView.LayoutManager todoLayoutManager;
    private EditText todoEditText;
    private Button todoButton;
    private TodoTaskViewModel mTodoTaskViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_todo:
                    setTitle(R.string.title_todo);
                    return true;
                case R.id.navigation_dashboard:
                    setTitle(R.string.title_calendar);
                    return true;
                case R.id.navigation_notifications:
                    setTitle(R.string.title_me);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.title_todo);

        // Instantiating my TodoTaskViewModel, persisted with ViewModelProviders
        mTodoTaskViewModel = ViewModelProviders.of(this).get(TodoTaskViewModel.class);

        // Adding new to-do item to the list
        todoEditText = (EditText) findViewById(R.id.todo_edittext);
        todoButton = (Button)findViewById(R.id.add_todo_btn);
        todoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newTaskText = todoEditText.getText().toString();
                if (!newTaskText.equals("")) {
                    mTodoTaskViewModel.insert(new TodoDBTask(newTaskText));
                    todoEditText.setText("");
                }
            }
        });

        // Use a linear layout manager
        todoRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler_view);
        todoLayoutManager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(todoLayoutManager);
        todoRecyclerView.setHasFixedSize(true);

        // Specify an adapter
        todoAdapter = new TodoAdapter(this);
        todoRecyclerView.setAdapter(todoAdapter);

        // Changes the UI when database data changes
        mTodoTaskViewModel.getAllTasks().observe(this, new Observer<List<TodoDBTask>>() {
            @Override
            public void onChanged(@Nullable final List<TodoDBTask> todoDBTasks) {
                todoAdapter.setTodoDataset((ArrayList<TodoDBTask>) todoDBTasks);
            }
        });

        // Attaching ItemTouchHelper to the RecyclerView
        ItemTouchHelper.Callback todoItemTouchHelperCallback =
                new TodoItemTouchHelperCallback(todoAdapter, mTodoTaskViewModel);
        ItemTouchHelper todoItemTouchHelper = new ItemTouchHelper(todoItemTouchHelperCallback);
        todoItemTouchHelper.attachToRecyclerView(todoRecyclerView);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
