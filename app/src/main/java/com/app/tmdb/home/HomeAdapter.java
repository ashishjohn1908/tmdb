package com.app.tmdb.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.BuildConfig;
import com.app.tmdb.R;
import com.app.tmdb.Utils.PaginationListener;
import com.app.tmdb.Utils.Utils;
import com.app.tmdb.base.AppConstant;
import com.app.tmdb.model.Result;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Result> mMovieList = new ArrayList<>();
    private int mSortedBy;
    private Context mContext;
    private ItemClickListener mListener;

    public HomeAdapter(Context context, ItemClickListener itemClickListener) {
        mContext = context;
        mListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        if (mMovieList != null && mMovieList.size() > 0) {
            String appendedString = "";
            if (mSortedBy == AppConstant.SORTED_BY_RATING)
                appendedString = String.format(mContext.getResources().getString(R.string.sort_by_rating), String.valueOf(mMovieList.get(position).getVoteAverage()));
            else if (mSortedBy == AppConstant.SORTED_BY_POPULARITY)
                appendedString = String.format(mContext.getResources().getString(R.string.sort_by_popularity), String.valueOf(mMovieList.get(position).getPopularity()));
            else
                appendedString = String.format(mContext.getResources().getString(R.string.sort_by_date), mMovieList.get(position).getReleaseDate());

            itemViewHolder.tvSortOrder.setText(appendedString);
            itemViewHolder.tvTitle.setText(mMovieList.get(position).getTitle());

            String posterUrl = BuildConfig.PosterBaseUrl + mMovieList.get(position).getPosterPath();
            Utils.setImage(posterUrl, itemViewHolder.imageView);
            itemViewHolder.imageView.setTag(mMovieList.get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvSortOrder, tvTitle;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSortOrder = itemView.findViewById(R.id.tv_sort_order);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_movie_poster);

            imageView.setOnClickListener(v -> mListener.OnItemClick((int) imageView.getTag()));
        }
    }

    public void setData(ArrayList<Result> results, int sortedBy) {
        mMovieList.addAll(results);
        mSortedBy = sortedBy;
        notifyItemRangeChanged(mMovieList.size() - PaginationListener.PAGE_SIZE, mMovieList.size());
    }

    public interface ItemClickListener {
        void OnItemClick(int movieId);
    }
}
