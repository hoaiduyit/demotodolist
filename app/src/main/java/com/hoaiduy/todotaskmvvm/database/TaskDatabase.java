package com.hoaiduy.todotaskmvvm.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.hoaiduy.todotaskmvvm.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoaiduy2503 on 5/12/2017.
 */

public class TaskDatabase {
    private List<TaskModel> taskList;

    private static final String DATABASE_NAME = "TODO.db";
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table "
            + "TaskList" + "(" + "id" + " integer primary key autoincrement,"
            + "title text,task text,complete integer not null default 0); ";
    private SQLiteDatabase sqLiteDatabase;
    private final Context mContext;
    private DatabaseHelper mHelper;

    public TaskDatabase(Context context){
        this.mContext = context;
        mHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TaskDatabase open() throws SQLiteException{
        sqLiteDatabase = mHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public SQLiteDatabase getSqLiteDatabase(){
        return sqLiteDatabase;
    }

    public long insertEntry(TaskModel taskModel){
        ContentValues values = new ContentValues();
        values.put("title", taskModel.getTitle());
        values.put("task", taskModel.getTask());

        return sqLiteDatabase.insert("TaskList", null, values);
    }

    public int deleteTask(String title){
        return sqLiteDatabase.delete("TaskList", "title= ?", new String[]{title});
    }

    public int updateIsComplete(String id){
        ContentValues values = new ContentValues();
        values.put("complete", 1);
        return sqLiteDatabase.update("TaskList", values, "id= ?", new String[]{id});
    }

    public List<TaskModel> showTaskList(){
        taskList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("TaskList", null, "", null, null, null, null);
        while (cursor.moveToNext()){
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            boolean complete = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("complete")));
            TaskModel taskModel = new TaskModel(id, title, task, complete);
            taskList.add(taskModel);
        }
        cursor.close();
        return taskList;
    }
}
