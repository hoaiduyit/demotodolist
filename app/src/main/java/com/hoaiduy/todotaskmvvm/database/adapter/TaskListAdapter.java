package com.hoaiduy.todotaskmvvm.database.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Object> listOfItemCompleted = new ArrayList<Object>();
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

        holder.checkBox.setClickable(true);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->  {
            if (isChecked){
                taskModels.get(position);
                listOfItemCompleted.add(model.getTittle());
                model.setComplete(true);
            }else {
                taskModels.remove(position);
                listOfItemCompleted.remove(model.getTittle());
                model.setComplete(false);
            }
        });

        holder.txtTittle.setOnLongClickListener(view -> {
            deleteTask(holder, position);
            return false;
        });
    }

    private void deleteTask(TaskHolder holder, int position) {
        taskDatabase = new TaskDatabase(activity);
        taskDatabase = taskDatabase.open();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setMessage("Do you want to delete this task?").setCancelable(false);
        dialogBuilder.setPositiveButton("Yes", (dialog, which) ->  {
            TaskModel model = taskModels.get(position);
            String tittle = model.getTittle();
            int delete = taskDatabase.deleteTask(tittle);
            if (delete > 0) {
                Toast.makeText(activity, "Delete task successful", Toast.LENGTH_LONG).show();
                taskModels.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(activity, "Delete task failed", Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("No", (dialog, which) ->  {
           dialog.dismiss();
        });
        dialogBuilder.show();
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
