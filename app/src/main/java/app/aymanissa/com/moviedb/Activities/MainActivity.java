package app.aymanissa.com.moviedb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;
import java.util.List;

import app.aymanissa.com.moviedb.Adapters.MoviesAdapter;
import app.aymanissa.com.moviedb.Constants;
import app.aymanissa.com.moviedb.Interfaces.MoviesAdapterListener;
import app.aymanissa.com.moviedb.Interfaces.MoviesInterface;
import app.aymanissa.com.moviedb.Models.Movie;
import app.aymanissa.com.moviedb.Models.Result;
import app.aymanissa.com.moviedb.MoviesController;
import app.aymanissa.com.moviedb.R;
import app.aymanissa.com.moviedb.SortType;
import butterknife.BindView;
import butterknife.ButterKnife;
import iammert.com.library.Status;
import iammert.com.library.StatusView;

public class MainActivity extends AppCompatActivity implements MoviesAdapterListener, SearchView.OnQueryTextListener, ConnectivityChangeListener {

    MoviesAdapter moviesAdapter;

    List<Movie> mainMoviesArrayList;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.noMoviesFoundTextView) TextView noMoviesFoundTextView;

    @BindView(R.id.status) StatusView statusView;

    SearchView searchView;
    MenuItem popularMenuItem;
    MenuItem topRatedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mainMoviesArrayList = new ArrayList<>();

        initRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        popularMenuItem = menu.findItem(R.id.action_popular);
        topRatedMenuItem = menu.findItem(R.id.action_top_rated);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                noMoviesFoundTextView.setVisibility(View.INVISIBLE);
                setMoviesAdapter(mainMoviesArrayList);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                return true;
            case R.id.action_popular:
                getMovies(SortType.POPULAR);
                popularMenuItem.setVisible(false);
                topRatedMenuItem.setVisible(true);
                return true;
            case R.id.action_top_rated:
                getMovies(SortType.TOP_RATED);
                popularMenuItem.setVisible(true);
                topRatedMenuItem.setVisible(false);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    void getMovies(SortType sortType) {
        MoviesController.getMovies(this, sortType, new MoviesInterface() {
            @Override
            public void onReceivedMovies(Result result) {
                mainMoviesArrayList = result.movieList;
                setMoviesAdapter(mainMoviesArrayList);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    void searchMovies(String query) {
        MoviesController.getSearchedMovies(this, query, new MoviesInterface() {
            @Override
            public void onReceivedMovies(Result result) {
                List<Movie> movieArrayList = result.movieList;
                setMoviesAdapter(movieArrayList);

                if (movieArrayList.size() == 0)
                    noMoviesFoundTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    void setMoviesAdapter(List<Movie> moviesArrayList) {
        moviesAdapter = new MoviesAdapter(moviesArrayList, getApplicationContext(), this);
        if (moviesArrayList != null)
            recyclerView.setAdapter(moviesAdapter);

    }

    @Override
    public void onMovieClickListener(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_PARCLE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        noMoviesFoundTextView.setVisibility(View.INVISIBLE);
        searchMovies(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        noMoviesFoundTextView.setVisibility(View.INVISIBLE);
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionBuddy.getInstance().registerForConnectivityEvents(this, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConnectionBuddy.getInstance().unregisterFromConnectivityEvents(this);
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if (event.getState().getValue() == ConnectivityState.CONNECTED) {
            // device has active internet connection

            if (mainMoviesArrayList.isEmpty())
                getMovies(SortType.POPULAR);

            statusView.setStatus(Status.COMPLETE);
            Constants.INTERNET_CONNECTION_FLAG = true;


        } else {
            // there is no active internet connection on this device
            statusView.setStatus(Status.ERROR);
            Constants.INTERNET_CONNECTION_FLAG = false;
        }
    }
}
