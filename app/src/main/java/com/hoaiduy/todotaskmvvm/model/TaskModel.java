package com.hoaiduy.todotaskmvvm.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.io.Serializable;

/**
 * Created by hoaiduy2503 on 5/12/2017.
 */

public class TaskModel implements Serializable {

    private int id;

    @Nullable
    private String tittle;

    @Nullable
    private String task;

    private boolean complete = false;

    public TaskModel(){

    }

    public TaskModel(@Nullable String tittle, @Nullable String task, boolean complete) {
        this.tittle = tittle;
        this.task = task;
        this.complete = complete;
    }

    public TaskModel(@Nullable String tittle, boolean complete) {
        this.tittle = tittle;
        this.complete = complete;
    }

    public TaskModel(@Nullable String tittle, @Nullable String task){
        this.tittle = tittle;
        this.task = task;
    }

    public TaskModel(int id, @Nullable String tittle, @Nullable String task, boolean complete) {
        this.id = id;
        this.tittle = tittle;
        this.task = task;
        this.complete = complete;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @Nullable
    public String getTittle() {
        return tittle;
    }

    @Nullable
    public String getTask() {
        return task;
    }

    public boolean isActive() {
        return !complete;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(tittle) &&
                Strings.isNullOrEmpty(task);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTittle(@Nullable String tittle) {
        this.tittle = tittle;
    }

    public void setTask(@Nullable String task) {
        this.task = task;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return "Task with title " + tittle;
    }
}
