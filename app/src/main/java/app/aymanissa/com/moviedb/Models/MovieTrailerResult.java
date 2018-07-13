package app.aymanissa.com.moviedb.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieTrailerResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> movieTrailers = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public List<MovieTrailer> getMovieTrailers() {
        return movieTrailers;
    }
}
