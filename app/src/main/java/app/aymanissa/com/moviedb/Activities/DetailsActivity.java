package app.aymanissa.com.moviedb.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.aymanissa.com.moviedb.Adapters.ReviewsAdapter;
import app.aymanissa.com.moviedb.Adapters.VideosAdapter;
import app.aymanissa.com.moviedb.AppExecutors;
import app.aymanissa.com.moviedb.Constants;
import app.aymanissa.com.moviedb.Database.AppDatabase;
import app.aymanissa.com.moviedb.Interfaces.MoviesReviewsListener;
import app.aymanissa.com.moviedb.Interfaces.MoviesTrailersListener;
import app.aymanissa.com.moviedb.Models.Movie;
import app.aymanissa.com.moviedb.Models.MovieReview;
import app.aymanissa.com.moviedb.Models.MovieReviewsResult;
import app.aymanissa.com.moviedb.Models.MovieTrailer;
import app.aymanissa.com.moviedb.Models.MovieTrailerResult;
import app.aymanissa.com.moviedb.MoviesController;
import app.aymanissa.com.moviedb.MoviesViewModel;
import app.aymanissa.com.moviedb.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.moviePosterImageView) ImageView moviePosterImageView;
    @BindView(R.id.releaseDateTextView) TextView releaseDateTextView;
    @BindView(R.id.rateTextView) TextView rateTextView;
    @BindView(R.id.movieDescriptionTextView) TextView movieDescriptionTextView;

    @BindView(R.id.videosRecyclerView) RecyclerView videosRecyclerView;
    @BindView(R.id.reviewsRecyclerView) RecyclerView reviewsRecyclerView;

    @BindView(R.id.likeButton) LikeButton likeButton;

    VideosAdapter videosAdapter;
    ReviewsAdapter reviewsAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE_PARCLE_EXTRA);
        setTitle(movie.getTitle());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (getIntent().getBooleanExtra(Constants.SAVED_MOVIE_EXTRA,false) || mDb.moviesDao().loadMovieById(movie.getId()) != null)
                likeButton.setLiked(true);
        });

        movieDescriptionTextView.setText(movie.getOverview());
        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                .placeholder(R.drawable.movie_placeholder)
                .into(moviePosterImageView);

        rateTextView.setText(movie.getVoteAverage() + "/10");
        releaseDateTextView.setText(movie.getReleaseDate());

        MoviesController.getMovieTrailers(this, movie.getId(), new MoviesTrailersListener() {
            @Override
            public void onReceivedMovieTrailers(MovieTrailerResult movieTrailerResult) {
                setVideosAdapter(movieTrailerResult.getMovieTrailers());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        MoviesController.getMovieReviews(this, movie.getId(), new MoviesReviewsListener() {
            @Override
            public void onReceivedMovieReviews(MovieReviewsResult movieReviewsResult) {
                setReviewsAdapter(movieReviewsResult.getResults());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    try {
                        mDb.moviesDao().insertMovie(movie);
                    } catch (Exception e){
                        mDb.moviesDao().updateMovie(movie);
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                AppExecutors.getInstance().diskIO().execute(() -> mDb.moviesDao().deleteMovie(movie.getId()));
            }
        });

    }

    void setVideosAdapter(List<MovieTrailer> trailerList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        videosAdapter = new VideosAdapter(trailerList,this);
        videosRecyclerView.setLayoutManager(layoutManager);
        videosRecyclerView.setHasFixedSize(true);
        videosRecyclerView.setNestedScrollingEnabled(false);
        videosRecyclerView.setAdapter(videosAdapter);
    }

    void setReviewsAdapter(List<MovieReview> movieReviews) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reviewsAdapter = new ReviewsAdapter(movieReviews);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setNestedScrollingEnabled(false);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
    }



}
