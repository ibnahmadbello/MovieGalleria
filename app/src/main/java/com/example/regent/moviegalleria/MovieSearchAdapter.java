package com.example.regent.moviegalleria;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder> {

    private static final String TAG = MovieSearchAdapter.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    final private RecyclerViewClickListener mClickListener;

    private Context context;
    private List<Movie> movieList;

    public MovieSearchAdapter(MovieGalleriaActivity context, List<Movie> movies, RecyclerViewClickListener clickListener){
        this.context = context;
        movieList = movies;
        mClickListener = clickListener;
    }

    public interface RecyclerViewClickListener{
        void onItemClick(int clickedItemPosition);
    }

    @Override
    public MovieSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.movie_item, parent, false);
        Log.i(TAG, "View has been created.");
        return new MovieSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieSearchViewHolder holder, int position) {
        Movie movieItem = movieList.get(position);
        holder.bindMovieItem(movieItem);
//        holder.movieName.setText(movieItem.getMovieName());
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "Movie size is: " + movieList.size());
        return movieList.size();
    }

    class MovieSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private ImageView movieImage;
        private TextView movieName;
        private Movie movie;

        public MovieSearchViewHolder(View view){
            super(view);

            movieImage = view.findViewById(R.id.movie_image_view);
            movieName = view.findViewById(R.id.movie_text_view);
            cardView = view.findViewById(R.id.card_view);
            view.setOnClickListener(this);
        }

        public void bindMovieItem(Movie movieItem){
            movie = movieItem;
            movieName.setText(movie.getMovieName());
            Picasso.with(context)
                    .load(IMAGE_BASE_URL + movie.getImage())
                    .into(movieImage);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickListener.onItemClick(clickedPosition);
            Movie singleMovie = movieList.get(clickedPosition);
            Log.i(TAG, singleMovie.toString());
        }
    }


}
