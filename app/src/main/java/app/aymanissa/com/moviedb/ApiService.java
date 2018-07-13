package app.aymanissa.com.moviedb;


import app.aymanissa.com.moviedb.Models.MovieReviewsResult;
import app.aymanissa.com.moviedb.Models.MovieTrailerResult;
import app.aymanissa.com.moviedb.Models.Result;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ayman on 3/2/2018.
 */

public interface ApiService {

    @GET("/3/movie/{path}")
    Observable<Result> getMovies(@Path("path") String path, @Query("api_key") String apiKey);

    @GET("/3/search/movie")
    Observable<Result> getSearchedMovies(@Query("query") String query, @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/videos")
    Observable<MovieTrailerResult> getMovieTrailers(@Path("id") int movieId, @Query("api_key") String apiKey);


    @GET("/3/movie/{id}/reviews")
    Observable<MovieReviewsResult> getMovieReviews(@Path("id") int movieId, @Query("api_key") String apiKey);

}
