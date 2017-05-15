package com.hoaiduy.todotaskmvvm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.hoaiduy.todotaskmvvm.R;

import butterknife.BindView;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class AddTaskActivity extends AppCompatActivity {

    String tittle_key;
    String task_key;
    int id;
    @BindView(R.id.btnAddTask)
    Button btnAddTask;
    @BindView(R.id.txtTittle)
    EditText txtTittle;
    @BindView(R.id.txtTask)
    EditText txtTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Bundle bundle = getIntent().getExtras();
        tittle_key = bundle.getString("tittle");
        task_key = bundle.getString("task");
        id = bundle.getInt("id");
    }

    private void initView() {
        btnAddTask.setText("Update Task");
    }
}
