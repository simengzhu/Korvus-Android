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

    public void updateOrder(int oldPosition, int newPosition) {
        new updateOrderAsyncTask(mTodoTaskDao).execute(oldPosition, newPosition);
    }

    public int getNumOfRows() {
        new getCountAsyncTask(mTodoTaskDao).execute();
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

    private static class deleteAsyncTask extends android.os.AsyncTask<TodoDBTask, Void, Void> {

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

    private static class updateOrderAsyncTask extends android.os.AsyncTask<Integer, Void, Void> {

        private TodoTaskDAO mAsyncTaskDao;

        updateOrderAsyncTask(TodoTaskDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.updateOrder(params[0], params[1]);
            return null;
        }
    }

    private static class getCountAsyncTask extends AsyncTask<Void, Void, Integer> {

        private TodoTaskDAO mAsyncTaskDao;

        getCountAsyncTask(TodoTaskDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Void... params) {
            return mAsyncTaskDao.getNumOfRows();
        }

        @Override
        protected void onPostExecute() {

        }
    }
}
