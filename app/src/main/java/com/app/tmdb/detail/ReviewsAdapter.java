package com.app.tmdb.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tmdb.R;
import com.app.tmdb.model.Reviews;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MoviesReviewViewHolder> {

    private ArrayList<Reviews> mReviewsList;

    public ReviewsAdapter(ArrayList<Reviews> moviesReview) {
        mReviewsList = moviesReview;
    }

    @NonNull
    @Override
    public MoviesReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MoviesReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesReviewViewHolder holder, int position) {

        if (mReviewsList != null && mReviewsList.size() > 0) {
            if (mReviewsList.get(position) != null) {
                holder.tvAuthor.setText(mReviewsList.get(position).getAuthor());
                holder.tvContent.setText(mReviewsList.get(position).getContent());
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mReviewsList == null) ? 0 : mReviewsList.size();
    }

    public static class MoviesReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor;
        TextView tvContent;

        MoviesReviewViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tv_reviews_author);
            tvContent = itemView.findViewById(R.id.tv_reviews_content);
        }
    }
}
