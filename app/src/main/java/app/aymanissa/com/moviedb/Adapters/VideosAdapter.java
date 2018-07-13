package app.aymanissa.com.moviedb.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.aymanissa.com.moviedb.Models.MovieTrailer;
import app.aymanissa.com.moviedb.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {

    List<MovieTrailer> movieTrailers;
    Context context;

    public VideosAdapter(List<MovieTrailer> movieTrailers, Context context) {
        this.movieTrailers = movieTrailers;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_video_item, null);
        return new VideosAdapter.VideoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        MovieTrailer movieTrailer = movieTrailers.get(position);

        holder.videoTitleTextView.setText(movieTrailer.getName());
        holder.videoCardView.setOnClickListener(v -> {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailer.getId()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + movieTrailer.getId()));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.videoCardView) CardView videoCardView;
        @BindView(R.id.videoTitleTextView) TextView videoTitleTextView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
