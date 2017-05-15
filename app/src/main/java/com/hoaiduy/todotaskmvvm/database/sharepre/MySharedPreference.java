package com.hoaiduy.todotaskmvvm.database.sharepre;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class MySharedPreference {

    private static final String SHARED_NAME = "account";
    private static final String TITTLE_CODE = "tittle";
    private static final String TASK_CODE = "task";
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public MySharedPreference(Context context){
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public void saveCategoryCode(String tittle, String task){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TITTLE_CODE, tittle);
        editor.putString(TASK_CODE, task);
        editor.apply();
    }
}
