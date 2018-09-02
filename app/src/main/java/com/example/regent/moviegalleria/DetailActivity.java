package com.example.regent.moviegalleria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_POSITION = "extra_position";

    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        testTextView = (TextView) findViewById(R.id.test_text_view);

        Movie movie = (Movie) getIntent().getParcelableExtra(MovieGalleriaActivity.EXTRA);

        testTextView.setText(movie.getMovieName());

    }
}
