package app.aymanissa.com.moviedb.Interfaces;

import app.aymanissa.com.moviedb.Models.MovieTrailerResult;

public interface MoviesTrailersListener {

    void onReceivedMovieTrailers(MovieTrailerResult movieTrailerResult);

    void onError(String errorMessage);
}
