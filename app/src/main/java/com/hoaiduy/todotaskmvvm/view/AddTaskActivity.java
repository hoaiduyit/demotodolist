package com.hoaiduy.todotaskmvvm.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hoaiduy.todotaskmvvm.R;
import com.hoaiduy.todotaskmvvm.database.sharepre.MySharedPreference;
import com.hoaiduy.todotaskmvvm.databinding.TaskItemDetailBinding;
import com.hoaiduy.todotaskmvvm.util.Utils;

/**
 * Created by hoaiduy2503 on 5/15/2017.
 */

public class AddTaskActivity extends AppCompatActivity {

    String title_key;
    String task_key;
    MySharedPreference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskItemDetailBinding mBinding = DataBindingUtil.setContentView(this, R.layout.task_item_detail);

        Bundle bundle = getIntent().getExtras();
        title_key = bundle.getString("title");
        task_key = bundle.getString("task");
        preference = new MySharedPreference(this);
        preference.saveTaskCode(title_key, task_key);

        Utils.setTittleDetail(title_key, mBinding.txtTit, this);
        Utils.setTaskDetail(task_key, mBinding.txtTask, this);
    }
}
