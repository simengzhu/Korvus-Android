package com.example.simengzhu.korvus;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class TodoDBTask {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    public TodoDBTask(@NonNull String task) {this.mTask = task;}

    public String getTask(){return this.mTask;}
}
