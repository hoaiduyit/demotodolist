package com.hoaiduy.todotaskmvvm.database.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.TaskDatabase;
import com.hoaiduy.todotaskmvvm.model.TaskModel;
import com.hoaiduy.todotaskmvvm.view.AddTaskActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private ArrayList<TaskModel> taskModels;
    private Activity activity;
    TaskDatabase taskDatabase;

    public TaskListAdapter(ArrayList<TaskModel> taskModel, Activity activity){
        this.taskModels = taskModel;
        this.activity = activity;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        TaskModel model = taskModels.get(position);
        holder.txtTittle.setText(model.getTittle());
        holder.checkBox.setChecked(false);
        holder.txtTittle.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("tittle", model.getTittle());
            bundle.putString("task", model.getTask());
            activity.startActivity(new Intent(activity, AddTaskActivity.class).putExtras(bundle));
        });
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setData(ArrayList<TaskModel> modelArrayList){
        this.taskModels = modelArrayList;
    }

    class TaskHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTit)
        TextView txtTittle;
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        public TaskHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
