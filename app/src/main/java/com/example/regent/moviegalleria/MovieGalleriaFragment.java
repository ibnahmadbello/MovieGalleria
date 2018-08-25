package com.example.regent.moviegalleria;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MovieGalleriaFragment extends Fragment {

    private static final String TAG = MovieGalleriaFragment.class.getSimpleName();

    private RecyclerView mMovieRecyclerView;
    private List<Movie> mItems = new ArrayList<>();

    public static MovieGalleriaFragment newInstance(){
        return new MovieGalleriaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        new FetchMovieTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movie_galleria, container, false);
        mMovieRecyclerView = view.findViewById(R.id.fragment_movie_galleria_recycler_view);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter();

        return view;
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>>{


        @Override
        protected List<Movie> doInBackground(Void... params) {
            return new TheMovieSearch().fetchMovie();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            mItems = movies;
            setupAdapter();
        }
    }

    private class MovieHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private ImageView mMovieImageView;
        private Movie movie;

        public MovieHolder(View itemView){
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.movie_text_view);
            mMovieImageView = itemView.findViewById(R.id.movie_image_view);

        }

        public void bindMovie(Movie item){
            movie = item;
            mNameTextView.setText(movie.getMovieName());
        }

    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private List<Movie> movies;

        public MovieAdapter(List<Movie> movieList){
            movies = movieList;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getActivity());
            return new MovieHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder movieHolder, int position) {
            Movie movie = movies.get(position);
            movieHolder.bindMovie(movie);
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private void setupAdapter(){
        if (isAdded()){
            mMovieRecyclerView.setAdapter(new MovieAdapter(mItems));
        }
    }

}
