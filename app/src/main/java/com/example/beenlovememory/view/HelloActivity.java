package com.example.beenlovememory.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

import com.example.beenlovememory.R;

import pl.droidsonroids.gif.GifImageView;

public class HelloActivity extends AppCompatActivity {
    GifImageView gif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        gif = findViewById(R.id.imgGif);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Intent intent = new Intent(HelloActivity.this, MainActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(gif, "imgGif");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HelloActivity.this, pairs);

                startActivity(intent, options.toBundle());

                finish();
            }
        }, 2500);
    }
}
