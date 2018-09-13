package com.example.simengzhu.korvus;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TodoTaskViewModel extends AndroidViewModel {
    private TodoTaskRepository mRepository;
    private LiveData<List<TodoDBTask>> mAllTasks;

    public TodoTaskViewModel (Application application) {
        super(application);
        mRepository = new TodoTaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    LiveData<List<TodoDBTask>> getAllTasks() { return mAllTasks; }

    public void insert(TodoDBTask task) { mRepository.insert(task); }

    public void delete(TodoDBTask task) { mRepository.delete(task); }
}
