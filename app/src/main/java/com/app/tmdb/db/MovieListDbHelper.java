package com.app.tmdb.db;

import android.content.Context;

import com.app.tmdb.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class MovieListDbHelper {
    /*Method use to save user me data in database using Room...*/
    public void saveMovieList(ArrayList<Result> result, Context context) {
        FutureTask<Void> futureTask = new FutureTask<Void>(() -> {
            MovieListDao userMeDao = AppDatabase.getInstance(context).movieListDao();
            userMeDao.insertMovieList(result);
            return null;
        });

        try {
            Executors.newSingleThreadExecutor().execute(futureTask);
            futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*Method use to get user me data from database using Room...*/
    public List<Result> getMovieList(Context context) {
        FutureTask<List<Result>> futureTask = new FutureTask<List<Result>>(() -> {
            MovieListDao movieListDao = AppDatabase.getInstance(context).movieListDao();
            return movieListDao.getMovieList();
        });

        try {
            Executors.newSingleThreadExecutor().execute(futureTask);
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
