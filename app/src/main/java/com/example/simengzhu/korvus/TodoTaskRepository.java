package com.example.simengzhu.korvus;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class TodoTaskRepository {
    private TodoTaskDAO mTodoTaskDao;
    private LiveData<List<TodoDBTask>> mAllTasks;

    TodoTaskRepository(Application application) {
        TodoTaskRoomDatabase db = TodoTaskRoomDatabase.getDatabase(application);
        mTodoTaskDao = db.todoTaskDAO();
        mAllTasks = mTodoTaskDao.getAllTasks();
    }

    LiveData<List<TodoDBTask>> getAllTasks() {
        return mAllTasks;
    }


    public void insert (TodoDBTask task) {
        new insertAsyncTask(mTodoTaskDao).execute(task);
    }

    public void delete(TodoDBTask task) {
        new deleteAsyncTask(mTodoTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<TodoDBTask, Void, Void> {

        private TodoTaskDAO mAsyncTaskDao;

        insertAsyncTask(TodoTaskDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoDBTask... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TodoDBTask, Void, Void> {

        private TodoTaskDAO mAsyncTaskDao;

        deleteAsyncTask(TodoTaskDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoDBTask... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
