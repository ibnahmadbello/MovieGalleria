package com.example.regent.moviegalleria;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieGalleriaActivity extends AppCompatActivity implements
        MovieSearchAdapter.RecyclerViewClickListener, LoaderManager.LoaderCallbacks<List<Movie>>{

    private static final String TAG = MovieGalleriaActivity.class.getSimpleName();

    public static String EXTRA = "Movie_Extra";
    private static final int LOADER_NUMBER = 19;

    private RecyclerView mRecyclerView;
    private ProgressBar loadingProgressBar;
    private MovieSearchAdapter searchAdapter;
    private List<Movie> movieItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_galleria);

        mRecyclerView = findViewById(R.id.activity_movie_galleria_recycler_view);
        loadingProgressBar = findViewById(R.id.activity_movie_galleria_progress_bar);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MovieGalleriaActivity.this, 2);
        searchAdapter = new MovieSearchAdapter(this, new ArrayList<Movie>(), this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(searchAdapter);

//        String query = QueryPreferences.getStoredQuery(this);
        new FetchMovieTask("popular").execute();
        Log.i(TAG, "Movie size in activity: " + movieItems.size());
    }

    @Override
    public void onItemClick(int clickedItemPosition) {
        Intent intent = new Intent(this, DetailActivity.class);
        Movie testMovie = movieItems.get(clickedItemPosition);
//        intent.putExtra(DetailActivity.EXTRA_POSITION, clickedItemPosition);
        intent.putExtra(EXTRA, testMovie);
        startActivity(intent);
    }

    /*@Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Movie>>(this) {
            private String movieQuery;
            @Nullable
            @Override
            public List<Movie> loadInBackground() {
                if (movieQuery.equals("top_rated")) {
                    QueryPreferences.setStoredQuery(MovieGalleriaActivity.this, "top_rated");
                    return new TheMovieSearch().fetchTopRated();
                } else if (movieQuery.equals("popular")){
                    QueryPreferences.setStoredQuery(MovieGalleriaActivity.this, "popular");
                    return new TheMovieSearch().fetchPopularMovie();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }*/


    private class FetchMovieTask extends AsyncTask<Void, Void, List<Movie>> {

        private String movieQuery;

        public FetchMovieTask(String query){
            movieQuery = query;
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            if (movieQuery.equals("top_rated")) {
                QueryPreferences.setStoredQuery(MovieGalleriaActivity.this, "top_rated");
                return new TheMovieSearch().fetchTopRated();
            } else if (movieQuery.equals("popular")){
                QueryPreferences.setStoredQuery(MovieGalleriaActivity.this, "popular");
                return new TheMovieSearch().fetchPopularMovie();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            loadingProgressBar.setVisibility(View.GONE);
            movieItems = movies;
            searchAdapter = new MovieSearchAdapter(MovieGalleriaActivity.this, movies, MovieGalleriaActivity.this);
            mRecyclerView.setAdapter(searchAdapter);
        }

        @Override
        protected void onPreExecute() {
            loadingProgressBar.setMax(100);
            loadingProgressBar.incrementProgressBy(20);
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_popular_movie:
                String popular_query = "popular";
                if (QueryPreferences.getStoredQuery(this).equals("popular")){
                    Toast.makeText(this, "Already showing Popular Movies", Toast.LENGTH_SHORT).show();
                } else{
                    new FetchMovieTask(popular_query).execute();
                }
                break;
            case R.id.action_top_rated_movie:
                String top_rated_query = "top_rated";
                if (QueryPreferences.getStoredQuery(this).equals("top_rated")){
                    Toast.makeText(this, "Already showing Top Rated Movies", Toast.LENGTH_SHORT).show();
                } else{
                    new FetchMovieTask(top_rated_query).execute();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
