package app.aymanissa.com.moviedb.Interfaces;

import app.aymanissa.com.moviedb.Models.MovieReviewsResult;

public interface MoviesReviewsListener {

    void onReceivedMovieReviews(MovieReviewsResult movieReviewsResult);

    void onError(String errorMessage);
}
