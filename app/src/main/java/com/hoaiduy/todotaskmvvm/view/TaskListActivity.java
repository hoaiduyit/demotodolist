package com.hoaiduy.todotaskmvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.databinding.ActivityTaskListBinding;
import com.hoaiduy.todotaskmvvm.viewmodel.TaskViewModel;


public class TaskListActivity extends AppCompatActivity {

    TaskViewModel taskViewModel;
    ActivityTaskListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_list);
        taskViewModel = new TaskViewModel(this);
        initView();
    }

    private void initView() {
        binding.recycleView.setHasFixedSize(true);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setItemAnimator(new DefaultItemAnimator());
        taskViewModel.setupRecycleView(binding.recycleView);
        binding.btnAdd.setOnClickListener(view -> {
            taskViewModel.setupAddTask();
        });

    }
}
