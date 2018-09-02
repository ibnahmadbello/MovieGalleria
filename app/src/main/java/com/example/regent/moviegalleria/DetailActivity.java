package com.example.regent.moviegalleria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.regent.moviegalleria.MovieSearchAdapter.IMAGE_BASE_URL;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";

//    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private TextView movieNameTextView, overViewTextView, dateTextView;
    private ImageView movieImageView;
    private RatingBar movieRatingBar;

    private String receivedRating;
    private float numberRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieNameTextView = findViewById(R.id.movie_name_text_view);
        overViewTextView = findViewById(R.id.movie_overview_text_view);
        dateTextView = findViewById(R.id.release_date_text_view);
        movieImageView = findViewById(R.id.movie_picture_image_view);
        movieRatingBar = findViewById(R.id.movie_rating);

        Movie movie = (Movie) getIntent().getParcelableExtra(MovieGalleriaActivity.EXTRA);

        movieNameTextView.setText(movie.getMovieName());
        overViewTextView.setText(movie.getOverView());
        dateTextView.setText(movie.getDate());

        receivedRating = movie.getRating();
        numberRating = Float.parseFloat(receivedRating);

//        movieRatingBar.setNumStars(numberRating);
        movieRatingBar.setRating(numberRating);
        movieRatingBar.setMax(10);
        movieRatingBar.setIsIndicator(true);

        Picasso.with(this)
                .load(IMAGE_BASE_URL + movie.getImage())
                .resize(250, 250)
                .into(movieImageView);

    }
}
