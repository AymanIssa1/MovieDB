package app.aymanissa.com.moviedb;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import app.aymanissa.com.moviedb.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.moviePosterImageView) ImageView moviePosterImageView;
    @BindView(R.id.movieDescriptionTextView) TextView movieDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE_PARCLE_EXTRA);
        setTitle(movie.getTitle());

        movieDescriptionTextView.setText(movie.getOverview());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path()).into(moviePosterImageView);

    }
}
