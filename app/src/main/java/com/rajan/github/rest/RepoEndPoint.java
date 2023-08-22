package com.rajan.github.rest;

import com.rajan.github.model.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoEndPoint {
//    @GET("/user/{user}/repos")
//    Call<List<RepoModel>> getRepo(@Path("user") String name);

    @GET("repos/{owner}/{repo}")
    Call<List<RepoModel>> getRepo(@Path("owner") String name, @Path("repo") String repoName);
}
