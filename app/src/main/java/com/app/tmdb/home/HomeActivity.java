package com.app.tmdb.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.Utils.PaginationListener;
import com.app.tmdb.Utils.Utils;
import com.app.tmdb.base.AppApplication;
import com.app.tmdb.base.AppConstant;
import com.app.tmdb.base.BaseViewModelFactory;
import com.app.tmdb.detail.MovieDetailActivity;
import com.app.tmdb.model.MovieListModel;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private HomeViewModel mHomeViewModel;
    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private int sortBy = AppConstant.SORTED_BY_DATE;
    private boolean isLoading;
    private boolean isLastPage = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppApplication.getApp().getDaggerAppComponent().provideIn(this);

        mHomeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(HomeViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        setRecyclerView();

        if (mHomeViewModel.getMovieListLiveData() != null)
            mHomeViewModel.getMovieListLiveData().observe(this, this::observeData);

        if (mHomeViewModel.getMostPopularMovieListLiveData() != null)
            mHomeViewModel.getMostPopularMovieListLiveData().observe(this, this::observeData);

        if (mHomeViewModel.getTopRatedMovieListLiveData() != null)
            mHomeViewModel.getTopRatedMovieListLiveData().observe(this, this::observeData);

        mHomeViewModel.getUpcomingList(currentPage);
    }

    private void observeData(MovieListModel movieModel) {
        isLoading = false;
        if (mHomeAdapter != null) {
            isLastPage = movieModel.getResults() == null || movieModel.getTotalPages() == currentPage;
            mHomeAdapter.setData(movieModel.getResults(), sortBy);
        }
    }

    private void setRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mHomeAdapter = new HomeAdapter(this, this::OnItemClick);
        mRecyclerView.setAdapter(mHomeAdapter);

        mRecyclerView.addOnScrollListener(new PaginationListener(gridLayoutManager, this) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getDataFromServer();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_sort) {
            if (Utils.checkNetwork(this))
                showSortPopUpMenu();
            else
                Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortPopUpMenu() {
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.action_sort));
        sortMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_sort_top_rated:
                    sortBy = AppConstant.SORTED_BY_RATING;
                    break;

                case R.id.action_sort_most_popular:
                    sortBy = AppConstant.SORTED_BY_POPULARITY;
                    break;

                case R.id.action_sort_upcoming:
                    sortBy = AppConstant.SORTED_BY_DATE;
                    break;
            }

            getDataFromServer();
            return false;
        });
        sortMenu.inflate(R.menu.category_menu);
        sortMenu.show();
    }

    private void getDataFromServer() {
        switch (sortBy) {
            case AppConstant.SORTED_BY_DATE:
                mHomeViewModel.getUpcomingList(currentPage);
                break;

            case AppConstant.SORTED_BY_POPULARITY:
                mHomeViewModel.getMostPopularList(currentPage);
                break;

            case AppConstant.SORTED_BY_RATING:
                mHomeViewModel.getTopRatedList(currentPage);
                break;
        }
    }

    @Override
    public void OnItemClick(int movieId) {

        if (Utils.checkNetwork(this)) {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(AppConstant.KEY_MOVIE_ID, movieId);
            startActivity(intent);

        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }
}