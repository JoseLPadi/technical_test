package com.josepadilla.technical_test.entities;

/**
 * Created by jose on 03/24/2018.
 */

public class TaskGroup {

    private String name;
    private String id;

    public TaskGroup(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
