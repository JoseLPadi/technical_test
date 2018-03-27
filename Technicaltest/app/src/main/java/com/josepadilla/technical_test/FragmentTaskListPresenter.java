package com.josepadilla.technical_test;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.josepadilla.technical_test.entities.Project;
import com.josepadilla.technical_test.entities.TaskGroup;
import com.josepadilla.technical_test.services.RetrofitHelper;

import java.lang.reflect.Type;
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

public class FragmentTaskListPresenter extends RxPresenter<FragmentTaskListView> {

    public static final int LIST_PROJECT_ID = 1;
    public static final int LIST_TASK_FOR_PROJECT= 2;

    @State private List<Project> listProject;
    @State private List<TaskGroup> listTask;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);

        restartableLatestCache(LIST_PROJECT_ID, new Factory<Observable<List<Project>>>() {
                    @Override
                    public Observable<List<Project>> create() {
                        return new RetrofitHelper().getService().getProjects().map(new Function<JsonObject, List<Project>>() {

                            @Override
                            public List<Project> apply(JsonObject jsonObject) throws Exception {

                                JsonArray jsa = jsonObject.getAsJsonArray("projects");
                                List<Project> projects;
                                Type listType = new TypeToken<List<Project>>() {
                                }.getType();
                                projects = new Gson().fromJson(jsa, listType);
                                return projects;
                            }
                        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                                .mainThread());


                    }
                },
                new BiConsumer<FragmentTaskListView, List<Project>>() {
                    @Override
                    public void accept(FragmentTaskListView fragmentTaskListView, List<Project> projects) throws Exception {
                        listProject = projects;
                        fragmentTaskListView.setSpinnerList(projects);
                        fragmentTaskListView.dismissAlert();

                    }
                }, new BiConsumer<FragmentTaskListView, Throwable>() {
                    @Override
                    public void accept(FragmentTaskListView fragmentTaskListView, Throwable throwable) throws Exception {
                        fragmentTaskListView.dismissAlert();
                    }
                });


        restartableLatestCache(LIST_TASK_FOR_PROJECT, new Factory<Observable<List<TaskGroup>>>() {
            @Override
            public Observable<List<TaskGroup>> create() {
                return new RetrofitHelper().getServiceForGetAlltasks(Integer.parseInt(listProject.get(positionProjectSelected).getId())).getTasksList().map(new Function<JsonObject, List<TaskGroup>>() {

                    @Override
                    public List<TaskGroup> apply(JsonObject jsonObject) throws Exception {

                        JsonArray jsa = jsonObject.getAsJsonArray("tasklists");
                        List<TaskGroup> projects;
                        Type listType = new TypeToken<List<TaskGroup>>() {
                        }.getType();
                        projects = new Gson().fromJson(jsa, listType);
                        return projects;
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
                        .mainThread());


            }
        }, new BiConsumer<FragmentTaskListView, List<TaskGroup>>() {
            @Override
            public void accept(FragmentTaskListView fragmentTaskListView, List<TaskGroup> taskGroups) throws Exception {
                listTask = taskGroups;
                fragmentTaskListView.setListViewTasksList(listTask);
                fragmentTaskListView.dismissAlert();

            }
        }, new BiConsumer<FragmentTaskListView, Throwable>() {
            @Override
            public void accept(FragmentTaskListView fragmentTaskListView, Throwable throwable) throws Exception {
                fragmentTaskListView.dismissAlert();
            }
        });


    }

    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
        Icepick.saveInstanceState(this, state);
    }

    public void startEvent(int id){
        start(id);

    }
    @State private int positionProjectSelected;
    public void selectProject(int positionProj){

        this.positionProjectSelected = positionProj;
        start(LIST_TASK_FOR_PROJECT);

    }

    public Bundle selectTaskGroup(int position){
        Bundle b = new Bundle();
        b.putString("name", listTask.get(position).getName());
        b.putString("id", listTask.get(position).getId());
        return  b;
    }


}
