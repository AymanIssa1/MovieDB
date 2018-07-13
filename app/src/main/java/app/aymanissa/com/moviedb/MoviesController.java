package app.aymanissa.com.moviedb;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import app.aymanissa.com.moviedb.Interfaces.MoviesInterface;
import app.aymanissa.com.moviedb.Interfaces.MoviesReviewsListener;
import app.aymanissa.com.moviedb.Interfaces.MoviesTrailersListener;
import app.aymanissa.com.moviedb.Models.MovieReviewsResult;
import app.aymanissa.com.moviedb.Models.MovieTrailerResult;
import app.aymanissa.com.moviedb.Models.Result;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MoviesController {


    public static void getMovies(Activity activity, SortType sortType, final MoviesInterface moviesInterface){
        String type = "";

        if (sortType == SortType.POPULAR)
            type = "popular";
        else if (sortType == SortType.TOP_RATED)
            type = "top_rated";

        ApiClient.getInstance()
                .getMovies(type, activity.getResources().getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Result result) {
                        moviesInterface.onReceivedMovies(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesInterface.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    public static void getSearchedMovies(Activity activity, String searchQuery, final MoviesInterface moviesInterface){
        ApiClient.getInstance()
                .getSearchedMovies(searchQuery,activity.getResources().getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Result result) {
                        moviesInterface.onReceivedMovies(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesInterface.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }


    public static void getMovieTrailers(Activity activity, int movieId, final MoviesTrailersListener moviesTrailersListener) {
        ApiClient.getInstance()
                .getMovieTrailers(movieId, activity.getResources().getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieTrailerResult>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MovieTrailerResult movieTrailerResult) {
                        moviesTrailersListener.onReceivedMovieTrailers(movieTrailerResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesTrailersListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    public static void getMovieReviews(Activity activity, int movieId, final MoviesReviewsListener moviesReviewsListener) {
        ApiClient.getInstance()
                .getMovieReviews(movieId, activity.getResources().getString(R.string.api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieReviewsResult>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(MovieReviewsResult movieReviewsResult) {
                        moviesReviewsListener.onReceivedMovieReviews(movieReviewsResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        moviesReviewsListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }


}
