package app.aymanissa.com.moviedb.Interfaces;

import app.aymanissa.com.moviedb.Models.Result;

public interface MoviesInterface {

    void onReceivedMovies(Result result);

    void onError(String errorMessage);

}
