package com.hoaiduy.todotaskmvvm.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.TaskDatabase;
import com.hoaiduy.todotaskmvvm.databinding.ActivityTaskBinding;
import com.hoaiduy.todotaskmvvm.model.TaskModel;
import com.hoaiduy.todotaskmvvm.view.adapter.TaskListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class TaskViewModel {
    private Context mContext;
    private List<TaskModel> taskModels = new ArrayList<>();
    private TaskListAdapter adapter;
    private TaskDatabase taskDatabase;
    private String stringTitle, stringTask;
    private TaskModel model = new TaskModel();

    public TaskViewModel(Context activity){
        this.mContext = activity;
    }

    public void setupRecycleView(RecyclerView recyclerView){
        taskDatabase = new TaskDatabase(mContext);
        taskDatabase = taskDatabase.open();
        adapter = new TaskListAdapter(taskModels, mContext);
        recyclerView.setAdapter(adapter);
        makeObservable(getAllTask())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskModels1 -> {
                        taskModels.addAll(taskModels1);
                });
    }

    //-----RxJava----
    private Callable<List<TaskModel>> getAllTask(){
        return () -> taskDatabase.showTaskList();
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                e -> {
                    try {
                        e.onNext(func.call());
                        e.onComplete();
                    } catch(Exception ex) {
                        e.onError(ex);
                    }
                });
    }
    //-------------

    public void setupAddTask(){
        taskDatabase = new TaskDatabase(mContext);
        taskDatabase = taskDatabase.open();
        ActivityTaskBinding mBinding = DataBindingUtil.inflate(LayoutInflater
                .from(mContext), R.layout.activity_task, null, false);

        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(mBinding.getRoot());
        dialog.setTitle("Task Detail");

        mBinding.btnAddTask.setOnClickListener(view -> {
            stringTitle = mBinding.txtTitle.getText().toString();
            stringTask = mBinding.txtTask.getText().toString();
            if (TextUtils.isEmpty(stringTitle) || TextUtils.isEmpty(stringTask)){
                Toast.makeText(mContext, "Empty...", Toast.LENGTH_SHORT).show();
            }else {
                model = new TaskModel();
                model.setTitle(stringTitle);
                model.setTask(stringTask);
                long result = taskDatabase.insertEntry(model);
                if (result == -1){
                    Toast.makeText(mContext, "Failed to add task", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "Add task successful", Toast.LENGTH_SHORT).show();
                    taskModels.add(model);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
