package com.josepadilla.technical_test.services;



import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by jose on 03/24/2018.
 */

public interface EndpointInterface {


    @GET("projects.json")
    Observable<JsonObject> getProjects();

    //GET/projects/{project_id}/tasklists.json
    @GET("tasklists.json")
    Observable<JsonObject> getTasksList();

    //POST to /tasklists/{task_list_id}/quickadd.json
    @POST("quickadd.json")
    Observable<JsonObject> quickAddTaskList(@Query(value = "content", encoded = false) String content);

}
