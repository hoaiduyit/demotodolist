package com.hoaiduy.todotaskmvvm.database.sharepre;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class MySharedPreference {

    private static final String TITLE_CODE = "title";
    private static final String TASK_CODE = "task";
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public MySharedPreference(Context context){
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(null, Context.MODE_PRIVATE);
    }

    public void saveTaskCode(String title, String task){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TITLE_CODE, title);
        editor.putString(TASK_CODE, task);
        editor.apply();
    }
}
