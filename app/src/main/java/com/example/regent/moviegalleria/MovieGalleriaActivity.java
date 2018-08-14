package com.example.regent.moviegalleria;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MovieGalleriaActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MovieGalleriaFragment.newInstance();
    }
}
