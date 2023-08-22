package com.rajan.github.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "repo")
public class RepoModel {

    @PrimaryKey
    public int uid;


    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String description;

    @ColumnInfo(name = "url")
    @SerializedName("html_url")
    private String html_url;

    public RepoModel(
            String name,
            String description,
            String html_url) {

        this.setName(name);
        this.setDescription(description);
        this.setHtml_url(html_url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if(description==null){
            return "This repo has no discription";
        }else{
            return description;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
