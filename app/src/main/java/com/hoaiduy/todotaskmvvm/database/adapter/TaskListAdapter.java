package com.hoaiduy.todotaskmvvm.database.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
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
import rx.functions.Action1;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> implements Action1<ArrayList<TaskModel>>{

    private ArrayList<TaskModel> taskModels;
    private Activity activity;
    private TaskDatabase taskDatabase;

    public TaskListAdapter(ArrayList<TaskModel> taskModel, Activity activity){
        this.taskModels = taskModel;
        this.activity = activity;
    }

    @Override
    public void call(ArrayList<TaskModel> taskModels) {
        this.taskModels = taskModels;
        notifyDataSetChanged();
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
        holder.checkBox.setClickable(true);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->  {
            if (isChecked){
                CharSequence tittle = model.getTittle();
                SpannableString spannable = new SpannableString(model.getTittle());
                spannable.setSpan(new StrikethroughSpan(), 0, tittle.length(), 0);
                tittle = spannable;
                model.isComplete();
                holder.txtTittle.setText(tittle);
            }else {
                holder.txtTittle.setText(model.getTittle());
            }
        });
        holder.txtTittle.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("tittle", model.getTittle());
            bundle.putString("task", model.getTask());
            activity.startActivity(new Intent(activity, AddTaskActivity.class).putExtras(bundle));
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
