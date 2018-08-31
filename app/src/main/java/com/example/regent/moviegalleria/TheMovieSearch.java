package com.example.regent.moviegalleria;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TheMovieSearch {

    private static final String TAG = TheMovieSearch.class.getSimpleName();
    private static final String API_KEY = "e8ee808272e37253ce9bcafad5189dd0";


    public byte[] getUrlBytes(String movieUrl) throws IOException{

        URL url = new URL(movieUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = httpsURLConnection.getInputStream();

            if (httpsURLConnection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(httpsURLConnection.getResponseMessage() + ": with " + movieUrl);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            httpsURLConnection.disconnect();
        }

    }
    public String getUrlString(String movieUrl) throws IOException{
        return new String(getUrlBytes(movieUrl));
    }

    public List<Movie> fetchMovie(){
        List<Movie> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.themoviedb.org/3/movie/")
                    .buildUpon()
                    .appendPath("popular")
                    .appendQueryParameter("api_key", API_KEY)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "RECEIVED JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (JSONException e){
            Log.e(TAG, "Failed to parse JSON", e);
        } catch (IOException e){
            Log.e(TAG, "Failed to fetch items" + e.getMessage());
        }

        Log.i(TAG, "items" + items);
        return items;
    }

    private void parseItems(List<Movie> movies, JSONObject jsonBody) throws IOException, JSONException{
        JSONArray resultJSONArray = jsonBody.getJSONArray("results");
        for (int i = 0; i < resultJSONArray.length(); i++){
            JSONObject resultObject = resultJSONArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setMovieName(resultObject.getString("title"));
            movie.setOverView(resultObject.getString("overview"));
            movie.setImage(resultObject.getString("poster_path"));
            movie.setRating(resultObject.getString("vote_average"));
            movie.setDate(resultObject.getString("release_date"));

            movies.add(movie);
            Log.i(TAG, "Item successfully added: " + movie.getImage());
        }
    }
}
