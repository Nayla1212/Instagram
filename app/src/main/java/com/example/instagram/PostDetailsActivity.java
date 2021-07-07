package com.example.instagram;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
}
