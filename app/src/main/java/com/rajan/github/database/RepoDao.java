package com.rajan.github.database;

import androidx.room.*;

import com.rajan.github.model.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepoModel repoModel);

    @Insert
    void insertAll(RepoModel... repoModels);

    @Insert
    void insertList(List<RepoModel> repoModels);

    @Query("select * from repo")
    List<RepoModel> getAllRepo();

    @Delete
    void delete(RepoModel repoModel);

}
