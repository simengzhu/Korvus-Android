package com.example.simengzhu.korvus;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table",indices = {@Index(value = {"position"}, unique = true)})
public class TodoDBTask {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id = 0;

    @NonNull
    @ColumnInfo(name = "position")
    private int position = 0;

    @NonNull
    @ColumnInfo(name = "task")
    private String mTask;

    public TodoDBTask(@NonNull String task) {this.mTask = task;}

    public int getId() {return this.id;}

    public int getPosition() {return this.position;}

    public String getTask() {return this.mTask;}
}
