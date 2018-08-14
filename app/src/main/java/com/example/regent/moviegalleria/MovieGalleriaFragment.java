package com.example.regent.moviegalleria;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class MovieGalleriaFragment extends Fragment {

    private static final String TAG = MovieGalleriaFragment.class.getSimpleName();

    private RecyclerView mMovieRecyclerView;

    public static MovieGalleriaFragment newInstance(){
        return new MovieGalleriaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_galleria, container, false);
        mMovieRecyclerView = view.findViewById(R.id.fragment_movie_galleria_recycler_view);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

    private class FetchMovieTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... params) {
            try {
                String result = new TheMovieSearch().getUrlString("https://api.themoviedb.org/3/movie/popular?api_key=e8ee808272e37253ce9bcafad5189dd0");
                Log.i(TAG, "Fetched contents of the URL: " + result);
            } catch (IOException e){
                Log.e(TAG, "Failed to fetch URL Content: ", e);
            }

            return null;
        }
    }
}
