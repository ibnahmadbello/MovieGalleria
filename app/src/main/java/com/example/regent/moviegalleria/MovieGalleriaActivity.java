package com.example.regent.moviegalleria;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MovieGalleriaActivity extends AppCompatActivity {

    private static final String TAG = MovieGalleriaActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MovieSearchAdapter searchAdapter;
    private List<Movie> movieItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_galleria);

        mRecyclerView = findViewById(R.id.activity_movie_galleria_recycler_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MovieGalleriaActivity.this, 2);
        searchAdapter = new MovieSearchAdapter(this, new ArrayList<Movie>());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(searchAdapter);

        new FetchMovieTask().execute();
        Log.i(TAG, "Movie size in activity: " + movieItems.size());
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(Void... params) {
            return new TheMovieSearch().fetchMovie();
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieItems = movies;
//            setupAdapter();
//            searchAdapter = new MovieSearchAdapter(movieItems);
            searchAdapter = new MovieSearchAdapter(MovieGalleriaActivity.this, movies);
            mRecyclerView.setAdapter(searchAdapter);
        }
    }

}
