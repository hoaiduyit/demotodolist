package com.hoaiduy.todotaskmvvm.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.hoaiduy.todotaskmvvm.model.TaskModel;

import java.util.ArrayList;

import rx.functions.Func1;

/**
 * Created by hoaiduy2503 on 5/12/2017.
 */

public class TaskDatabase {
    private ArrayList<TaskModel> taskList;

    private static final String DATABASE_NAME = "TODO.db";
    private static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    static final String DATABASE_CREATE = "create table "
            + "TaskList" + "(" + "id" + " integer primary key autoincrement," + "tittle text,task text,complete integer); ";
    private SQLiteDatabase sqLiteDatabase;
    private final Context mContext;
    private DatabaseHelper mHelper;
    @NonNull
    private Func1<Cursor, TaskModel> mTaskMapperFunction;

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
        values.put("tittle", taskModel.getTittle());
        values.put("task", taskModel.getTask());

        return sqLiteDatabase.insert("TaskList", null, values);
    }

    public void completeTask(String taskId){
        ContentValues values = new ContentValues();
        values.put("complete", true);
        String selection = "complete" + "LIKE ?";
        String[] selectionArg = {taskId};
        sqLiteDatabase.update("TaskList", values, selection, selectionArg);
    }

    public void deleteCompleteTask(){
        String selection = "complete" + " LIKE ?";
        String[] selectionArgs = {"1"};
        sqLiteDatabase.delete("TaskList", selection, selectionArgs);
    }

    /*public int deleteTask(String taskId){
        String selection = "id" + " LIKE ?";
        String[] selectionArgs = {taskId};
        return sqLiteDatabase.delete("TaskList", selection, selectionArgs);
    }*/

    public int deleteTask(String tittle){
        return sqLiteDatabase.delete("TaskList", "tittle= ?", new String[]{tittle});
    }

    public ArrayList<TaskModel> showTaskList(){
        taskList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("TaskList", null, "", null, null, null, null);
        while (cursor.moveToNext()){
            String tittle = cursor.getString(cursor.getColumnIndex("tittle"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            boolean complete = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("complete")));
            TaskModel taskModel = new TaskModel(tittle, task, complete);
            taskList.add(taskModel);
        }
        cursor.close();
        return taskList;
    }

    public ArrayList<TaskModel> showTaskDetail(){
        taskList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("TaskList", null, "", null, null, null, null);
        while (cursor.moveToNext()){
            String tittle = cursor.getString(cursor.getColumnIndex("tittle"));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            TaskModel taskModel = new TaskModel(tittle, task);
            taskList.add(taskModel);
        }
        cursor.close();
        return taskList;
    }

    public ArrayList<TaskModel> showTaskAtList(){
        taskList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query("TaskList", null, "", null, null, null, null);
        while (cursor.moveToNext()){
            String tittle = cursor.getString(cursor.getColumnIndex("tittle"));
            boolean complete = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("complete")));
            TaskModel taskModel = new TaskModel(tittle, complete);
            taskList.add(taskModel);
        }
        cursor.close();
        return taskList;
    }
}
