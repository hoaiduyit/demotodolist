package com.hoaiduy.todotaskmvvm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hoaiduy2503 on 5/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskDatabase.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter","Upgrading from version:" + oldVersion + " to " + newVersion + ", which destroy all old data");
        db.execSQL("DROP TABLE IF EXIST" + "TEMPLATE");
        onCreate(db);
    }
}
