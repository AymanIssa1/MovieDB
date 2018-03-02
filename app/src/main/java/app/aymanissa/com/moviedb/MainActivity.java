package app.aymanissa.com.moviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.ConnectivityChangeListener;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.ArrayList;

import app.aymanissa.com.moviedb.models.Movie;
import app.aymanissa.com.moviedb.models.Result;
import butterknife.BindView;
import butterknife.ButterKnife;
import iammert.com.library.Status;
import iammert.com.library.StatusView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapterListener, SearchView.OnQueryTextListener, ConnectivityChangeListener {

    MoviesAdapter moviesAdapter;

    ArrayList<Movie> mainMoviesArrayList;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    SearchView searchView;
    @BindView(R.id.noMoviesFoundTextView) TextView noMoviesFoundTextView;

    @BindView(R.id.status) StatusView statusView;


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void initRecyclerView(){
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    void getMovies(){
        Endpoint endpoint = ServiceGenerator.createService(Endpoint.class);
        Call<Result> resultCall = endpoint.getMovives(getResources().getString(R.string.api_key));
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                mainMoviesArrayList = response.body().results;
                setMoviesAdapter(mainMoviesArrayList);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    void searchMovies(String query){
        Endpoint endpoint = ServiceGenerator.createService(Endpoint.class);
        Call<Result> resultCall = endpoint.getSearchedMovives(getResources().getString(R.string.api_key),query);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                ArrayList<Movie> movieArrayList = response.body().results;
                setMoviesAdapter(movieArrayList);

                if(movieArrayList.size()==0)
                    noMoviesFoundTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    void setMoviesAdapter(ArrayList<Movie> moviesArrayList){
        moviesAdapter = new MoviesAdapter(moviesArrayList,getApplicationContext(),this);
        if(moviesArrayList != null)
            recyclerView.setAdapter(moviesAdapter);

    }

    @Override
    public void onMovieClickListener(Movie movie) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_PARCLE_EXTRA,movie);
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
        if(event.getState().getValue() == ConnectivityState.CONNECTED){
            // device has active internet connection

            if(mainMoviesArrayList.isEmpty())
                getMovies();

            statusView.setStatus(Status.COMPLETE);
            Constants.INTERNET_CONNECTION_FLAG = true;


        } else {
            // there is no active internet connection on this device
            statusView.setStatus(Status.ERROR);
            Constants.INTERNET_CONNECTION_FLAG = false;
        }
    }
}
