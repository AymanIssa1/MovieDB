package app.aymanissa.com.moviedb.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.aymanissa.com.moviedb.Models.MovieReview;
import app.aymanissa.com.moviedb.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    List<MovieReview> movieReviewList;


    public ReviewsAdapter(List<MovieReview> movieReviewList) {
        this.movieReviewList = movieReviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review_item, null);
        return new ReviewsAdapter.ReviewViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewTitleTextView.setText(movieReviewList.get(position).getAuthor());
        holder.reviewDescriptionTextView.setText(movieReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reviewTitleTextView) TextView reviewTitleTextView;
        @BindView(R.id.reviewDescriptionTextView) TextView reviewDescriptionTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
