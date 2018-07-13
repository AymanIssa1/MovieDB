package app.aymanissa.com.moviedb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import app.aymanissa.com.moviedb.Models.Movie;

public class MoviesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> moviesList;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMoviesList() {
        if (moviesList == null){
            moviesList = new MutableLiveData<>();
        }
        return moviesList;
    }

    public void setMovies(List<Movie> movies){
        moviesList.setValue(movies);
    }
}
