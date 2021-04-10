package com.app.tmdb.Utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

    @NonNull
    private GridLayoutManager gridLayoutManager;
    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
    public static final int PAGE_SIZE = 20;
    private Context mContext;

    public PaginationListener(@NonNull GridLayoutManager layoutManager, Context context) {
        gridLayoutManager = layoutManager;
        mContext = context;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage() && Utils.checkNetwork(mContext)) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}