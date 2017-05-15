package com.hoaiduy.todotaskmvvm.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.TaskDatabase;
import com.hoaiduy.todotaskmvvm.database.adapter.TaskListAdapter;
import com.hoaiduy.todotaskmvvm.model.TaskModel;

import java.util.ArrayList;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class TaskViewModel {
    private Activity activity;
    private ArrayList<TaskModel> taskModels = new ArrayList<>();
    private TaskListAdapter adapter;
    private TaskDatabase taskDatabase;
    private EditText txtTittle, txtTask;
    private Button btnAddTask;
    private String stringTittle, stringTask;

    public TaskViewModel(Activity activity){
        this.activity = activity;
    }

    public void setupRecycleView(RecyclerView recyclerView){
        taskDatabase = new TaskDatabase(activity);
        taskDatabase = taskDatabase.open();
        taskModels = taskDatabase.showTaskList();

        adapter = new TaskListAdapter(taskModels, activity);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setupAddTask(RecyclerView recyclerView){
        taskDatabase = new TaskDatabase(activity);
        taskDatabase = taskDatabase.open();

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.activity_task);
        dialog.setTitle("Task Detail");
        txtTittle = (EditText) dialog.findViewById(R.id.txtTittle);
        txtTask = (EditText) dialog.findViewById(R.id.txtTask);
        btnAddTask = (Button) dialog.findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(view -> {
            stringTittle = txtTittle.getText().toString().substring(0, 1).toUpperCase();
            stringTask = txtTask.getText().toString().substring(0, 1).toUpperCase();
            if (TextUtils.isEmpty(stringTittle) || TextUtils.isEmpty(stringTask)){
                Toast.makeText(activity, "Empty...", Toast.LENGTH_LONG).show();
            }else {
                TaskModel model = new TaskModel();
                model.setTittle(stringTittle);
                model.setTask(stringTask);
                long result = taskDatabase.insertEntry(model);
                if (result == -1){
                    Toast.makeText(activity, "Failed to add task", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(activity, "Add task successful", Toast.LENGTH_LONG).show();
                    taskModels.add(model);
                    adapter = new TaskListAdapter(taskModels, activity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }
}
