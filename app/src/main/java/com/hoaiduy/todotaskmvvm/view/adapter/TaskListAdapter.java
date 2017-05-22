package com.hoaiduy.todotaskmvvm.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.TaskDatabase;
import com.hoaiduy.todotaskmvvm.databinding.TaskItemBinding;
import com.hoaiduy.todotaskmvvm.model.TaskModel;
import com.hoaiduy.todotaskmvvm.viewmodel.SingleTaskViewModel;

import java.util.List;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder>{

    private List<TaskModel> taskModels;
    private Context mContext;
    private TaskDatabase taskDatabase;
    private TaskItemBinding binding;

    public TaskListAdapter(List<TaskModel> modelList, Context context){
        this.taskModels = modelList;
        this.mContext = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.task_item, parent, false);
        return new TaskHolder(binding);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.bindTask(new SingleTaskViewModel(taskModels.get(position), mContext));
        holder.bindDelete(binding.txtTit, holder, position);
        holder.bindChecked(binding.checkbox, position);
    }

    public void deleteTask(TaskHolder holder, int position) {
        taskDatabase = new TaskDatabase(mContext);
        taskDatabase = taskDatabase.open();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setMessage("Do you want to delete this task?").setCancelable(false);
        dialogBuilder.setPositiveButton("Yes", (dialog, which) ->  {
            TaskModel model = taskModels.get(position);
            String tittle = model.getTittle();
            int delete = taskDatabase.deleteTask(tittle);
            if (delete > 0) {
                Toast.makeText(mContext, "Delete task successful", Toast.LENGTH_LONG).show();
                taskModels.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(mContext, "Delete task failed", Toast.LENGTH_LONG).show();
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

    class TaskHolder extends RecyclerView.ViewHolder{

        TaskItemBinding mBinding;

        public TaskHolder(TaskItemBinding binding){
            super(binding.getRoot());
            this.mBinding = binding;
        }

        void bindTask(SingleTaskViewModel singleTaskViewModel){
            mBinding.setTaskViewModel(singleTaskViewModel);
            mBinding.executePendingBindings();
        }

        void bindDelete(TextView txtTit, TaskHolder holder, int position){
            txtTit.setOnLongClickListener(view -> {
                deleteTask(holder, position);
                return false;
            });
        }

        void bindChecked(CheckBox cb, int position){
            TaskDatabase taskDatabase = new TaskDatabase(mContext);
            taskDatabase = taskDatabase.open();
            TaskDatabase finalTaskDatabase = taskDatabase;
            cb.setOnCheckedChangeListener((com, isChecked) -> {
                if (isChecked){
                    int id = position + 1;
                    finalTaskDatabase.updateIsComplete(String.valueOf(id));
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Task is completed", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(mContext, "Task is activated", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
