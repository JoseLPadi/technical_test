package com.josepadilla.technical_test;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.josepadilla.technical_test.services.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import icepick.Icepick;
import icepick.State;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nucleus5.presenter.Factory;
import nucleus5.presenter.RxPresenter;

/**
 * Created by jose on 03/24/2018.
 */

public class FragmentAddMultipleTaskPresenter extends RxPresenter<FragmentAddMultipleTaskView> {

    @State
    List<String> listNewtasks;
    final static int ADD_NEW_ITEM = 1;
    final static int QUICK_ADD_TASKS = 2;

    @State private String taskName;
    @State private String taskID;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);

        restartableLatestCache(QUICK_ADD_TASKS, new Factory<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> create() {
                return new RetrofitHelper().getServiceForSendAllNewTasks(taskID).quickAddTaskList(stringListTask).map(new Function<JsonObject, Boolean>() {

                    @Override
                    public Boolean apply(JsonObject jsonObject) throws Exception {

                        return true;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                        .mainThread());


            }
        }, new BiConsumer<FragmentAddMultipleTaskView, Boolean>() {
            @Override
            public void accept(FragmentAddMultipleTaskView fragmentAddMultipleTaskView, Boolean taskGroup) throws Exception {
                fragmentAddMultipleTaskView.allTaskSendedDone();
                listNewtasks.clear();
                fragmentAddMultipleTaskView.updateListTask(listNewtasks);
                fragmentAddMultipleTaskView.dismissAlert();
                fragmentAddMultipleTaskView.allTaskSendedDone();

            }
        }, new BiConsumer<FragmentAddMultipleTaskView, Throwable>() {
            @Override
            public void accept(FragmentAddMultipleTaskView fragmentAddMultipleTaskView, Throwable throwable) throws Exception {
                fragmentAddMultipleTaskView.allTaskSendedFail();
                fragmentAddMultipleTaskView.dismissAlert();

            }
        });

    }

    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
        Icepick.saveInstanceState(this, state);
    }



    @State String newTaskAux;
    public List<String> addNewTask(String title){
        if (title.isEmpty()) {
            return listNewtasks;
        }
        if (listNewtasks == null ){
            listNewtasks = new ArrayList<String>();
        }
        listNewtasks.add(title);
        return listNewtasks;
    }

    public List<String> removeItem(int itemPosition){
        listNewtasks.remove(itemPosition);
        return listNewtasks;
    }


    public void setTaskNameAndTaskId(String taskID, String taskName){
        this.taskID = taskID;
        this.taskName = taskName;
    }


    @State String stringListTask;
    public void addAllTasks(){

        stringListTask ="";
        for (int i = 0; i < listNewtasks.size(); i++){
            if (stringListTask.isEmpty())
                stringListTask = listNewtasks.get(i);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                stringListTask  = stringListTask + System.lineSeparator() + listNewtasks.get(i);
            } else {
                stringListTask  = stringListTask + System.getProperty("line.separator") + listNewtasks.get(i);
            }
        }
        start(QUICK_ADD_TASKS);

    }



}
