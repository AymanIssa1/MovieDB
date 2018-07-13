package app.aymanissa.com.moviedb.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import app.aymanissa.com.moviedb.Models.Movie;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadSavedMovies();

    @Insert
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Query("DELETE FROM movie WHERE id = :id")
    void deleteMovie(int id);

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie loadMovieById(int id);

}
