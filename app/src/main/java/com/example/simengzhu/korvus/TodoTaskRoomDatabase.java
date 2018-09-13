package com.example.simengzhu.korvus;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {TodoDBTask.class}, version = 1)
public abstract class TodoTaskRoomDatabase extends RoomDatabase {
    public abstract TodoTaskDAO todoTaskDAO();

    private static TodoTaskRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
//                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TodoTaskDAO mDao;

        PopulateDbAsync(TodoTaskRoomDatabase db) {
            mDao = db.todoTaskDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            TodoDBTask task = new TodoDBTask("Hello");
            mDao.insert(task);
            task = new TodoDBTask("World");
            mDao.insert(task);
            return null;
        }
    }

    static TodoTaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoTaskRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoTaskRoomDatabase.class, "task_database")
                            .addCallback(sRoomDatabaseCallback).build();

                }
            }
        }
        return INSTANCE;
    }
}
