package com.rajan.github.model;

import com.google.gson.annotations.SerializedName;

public class RepoModel {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("html_url")
    private String repoURL;


    public RepoModel(
            String description,
            String name,
            String repoURL) {

        this.setName(name);
        this.setDescription(description);
        this.setRepoURL(repoURL);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepoURL(String repoURL){this.repoURL = repoURL;}

    public String getRepoURL(){return repoURL;}

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

}
