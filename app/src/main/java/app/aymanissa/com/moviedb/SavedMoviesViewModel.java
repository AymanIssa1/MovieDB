package app.aymanissa.com.moviedb;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import app.aymanissa.com.moviedb.Database.AppDatabase;
import app.aymanissa.com.moviedb.Models.Movie;

public class SavedMoviesViewModel extends AndroidViewModel {

    private static final String TAG = SavedMoviesViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public SavedMoviesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the movies from the DataBase");
        movies = database.moviesDao().loadSavedMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
