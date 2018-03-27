package com.josepadilla.technical_test.entities;


import java.util.List;

/**
 * Created by jose on 03/24/2018.
 */

public class Project {
    private String id;
    private String name;


    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


