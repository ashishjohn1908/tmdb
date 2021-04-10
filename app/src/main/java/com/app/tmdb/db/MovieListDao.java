package com.app.tmdb.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.tmdb.model.Result;

import java.util.List;

@Dao
public interface MovieListDao {

    @Query("select * from MovieList")
    List<Result> getMovieList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieList(List<Result> result);

}
