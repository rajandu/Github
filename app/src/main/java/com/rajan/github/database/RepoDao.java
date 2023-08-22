package com.rajan.github.database;

import androidx.room.*;

import com.rajan.github.model.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert
    void insert(RepoModel repoEntity);

    @Insert
    void insertAll(RepoModel... repoEntity);

    @Insert
    void insertList(List<RepoModel> repoEntity);

    @Query("select * from repo")
    List<RepoModel> getAllRepo();

    @Delete
    void delete(RepoModel repoEntity);

}
