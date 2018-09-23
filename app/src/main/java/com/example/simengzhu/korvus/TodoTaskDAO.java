package com.example.simengzhu.korvus;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoTaskDAO {
    @Insert
    void insert(TodoDBTask task);

    @Delete
    void delete(TodoDBTask task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Query("SELECT * from task_table ORDER BY task ASC")
    LiveData<List<TodoDBTask>> getAllTasks();

    @Query("UPDATE task_table SET position = :newOrder WHERE position = :oldOrder")
    void updateOrder(int oldOrder, int newOrder);

    @Query("SELECT count(*) FROM task_table")
    int getNumOfRows();

}
