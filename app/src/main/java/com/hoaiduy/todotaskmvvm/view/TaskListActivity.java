package com.hoaiduy.todotaskmvvm.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.viewmodel.TaskViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    TaskViewModel taskViewModel;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        taskViewModel = new TaskViewModel(this);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        taskViewModel.setupRecycleView(recyclerView);
        btnAdd.setOnClickListener(view -> {
            taskViewModel.setupAddTask(recyclerView);
        });

    }
}
