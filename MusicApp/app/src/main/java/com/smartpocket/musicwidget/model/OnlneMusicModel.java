package com.smartpocket.musicwidget.model;

import com.google.gson.annotations.SerializedName;

public class OnlneMusicModel {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("path")
    public String path;



    public OnlneMusicModel(String id, String name, String path){
        this.id = id;
        this.name = name;
        this.path = path;

    }

}
