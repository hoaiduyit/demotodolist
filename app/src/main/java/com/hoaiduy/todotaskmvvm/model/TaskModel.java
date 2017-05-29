package com.hoaiduy.todotaskmvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.hoaiduy.todotaskmvvm.BR;

/**
 * Created by hoaiduy2503 on 5/12/2017.
 */

public class TaskModel extends BaseObservable {

    private int id;
    private String title;
    private String task;
    private boolean complete;

    public TaskModel(){

    }

    public TaskModel(String title) {
        this.title = title;
    }

    public TaskModel(int id, String title, String task, boolean complete) {
        this.id = id;
        this.title = title;
        this.task = task;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getTask() {
        return task;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(id);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setTask(String task) {
        this.task = task;
        notifyPropertyChanged(BR.task);
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return "Task with title " + title + " id= " + id;
    }
}
