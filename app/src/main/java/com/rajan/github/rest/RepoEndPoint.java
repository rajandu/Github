package com.rajan.github.rest;

import com.rajan.github.model.RepoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RepoEndPoint {

    @GET("orgs/{owner}/{repos}")
    Call<RepoModel> getOrgRepo(@Path("owner") String name, @Path("repos") String repoName);

    @POST("orgs/{owner}/repos")
    @Headers("Authorization: Bearer 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")
    Call<RepoModel> createOrgRepo(@Path("owner") String name, @Body RepoModel body);



    @GET("repos/{owner}/{repo}")
    Call<RepoModel> getUserRepo(@Path("owner") String name, @Path("repo") String repoName);

}
