package com.hoaiduy.todotaskmvvm.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.sharepre.MySharedPreference;
import com.hoaiduy.todotaskmvvm.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class AddTaskActivity extends AppCompatActivity {

    String tittle_key;
    String task_key;
    @BindView(R.id.txtTit)
    @Nullable
    TextView txtTittle;
    @BindView(R.id.txtTask)
    @Nullable
    TextView txtTask;

    MySharedPreference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_item_detail);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        tittle_key = bundle.getString("tittle");
        task_key = bundle.getString("task");
        preference = new MySharedPreference(this);
        preference.saveCategoryCode(tittle_key, task_key);

        Utils.setTittleDetail(tittle_key, txtTittle, this);
        Utils.setTaskDetail(task_key, txtTask, this);
    }
}
