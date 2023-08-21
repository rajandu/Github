package com.rajan.github.rest;

import com.rajan.github.model.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepoEndPoint {
    @GET("/users/{user}/repos")
    Call<List<RepoModel>> getRepo(@Path("user") String name);
}
