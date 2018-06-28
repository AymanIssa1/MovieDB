package app.aymanissa.com.moviedb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.aymanissa.com.moviedb.Models.Result;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ayman on 3/2/2018.
 */

public class ApiClient implements ApiService{

    private static final String BASE_URL = "http://api.themoviedb.org/";

    private static ApiClient instance;
    private ApiService apiService;

    private ApiClient() {
        Gson gson = new GsonBuilder().create();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    @Override
    public Observable<Result> getMovies(String path,String apiKey) {
        return apiService.getMovies(path,apiKey);
    }

    @Override
    public Observable<Result> getSearchedMovies(String query, String apiKey) {
        return apiService.getSearchedMovies(query, apiKey);
    }
}
