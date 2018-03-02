package app.aymanissa.com.moviedb;


import app.aymanissa.com.moviedb.models.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ayman on 3/2/2018.
 */

public interface Endpoint {

    @GET("/3/movie/popular")
    Call<Result> getMovives(@Query("api_key") String apiKey);

    @GET("/3/search/movie")
    Call<Result> getSearchedMovives(@Query("api_key") String apiKey, @Query("query") String query);


}
