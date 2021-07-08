package com.example.instagram;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    public static final String TAG = "PostDetailsActivity";
    private TextView tvFeedUsername;
    private ImageView ivFeedImage;
    private TextView tvFeedDescription;
    private TextView tvTimeAgo;
    Post post;

    public PostDetailsActivity(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_post);

        //Replacing app name with Instagram script
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.instagram_script);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Handling bottom navigation toolbar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        startHomeActivity();
                        return true;
                    case R.id.btnComposeActivity:
                        startComposeActivity();
                        return true;
                    default: return true;
                }
            }
        });

        tvFeedUsername = findViewById(R.id.tvFeedUsername);
        ivFeedImage = findViewById(R.id.ivFeedImage);
        tvFeedDescription = findViewById(R.id.tvFeedDescription);
        tvTimeAgo = findViewById(R.id.tvTimeAgo);

        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvFeedDescription.setText(post.getDescription());
        tvFeedUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivFeedImage);
        }
        tvTimeAgo.setText(post.getTimeAgo());
    }

    private void startComposeActivity() {
        Intent intent = new Intent(this, ComposeActivity.class);
        startActivity(intent);
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }
}
