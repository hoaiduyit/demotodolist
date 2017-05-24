package com.hoaiduy.todotaskmvvm.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.view.View;

import com.hoaiduy.todotaskmvvm.model.TaskModel;
import com.hoaiduy.todotaskmvvm.view.AddTaskActivity;

/**
 * Created by hoaiduy2503 on 5/21/2017.
 */

public class SingleTaskViewModel extends BaseObservable {

    private TaskModel model;
    private Context mContext;

    public SingleTaskViewModel(TaskModel model, Context context) {
        this.model = model;
        this.mContext = context;
    }

    public String getTitle(){
        return model.getTitle();
    }

    public String getTask(){
        return model.getTask();
    }

    public void onItemClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString("title", model.getTitle());
        bundle.putString("task", model.getTask());
        mContext.startActivity(new Intent(mContext, AddTaskActivity.class).putExtras(bundle));
    }
}
