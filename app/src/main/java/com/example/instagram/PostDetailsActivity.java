package com.example.instagram;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.instagram.fragments.ComposeActivityFragment;
import com.example.instagram.fragments.TimelineActivityFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    public static final String TAG = "PostDetailsActivity";
    private TextView tvFeedUsername;
    private ImageView ivFeedImage;
    private TextView tvFeedDescription;
    private TextView tvPostUsername;
    private TextView tvTimeAgo;
    private TextView tvNumberLikes;
    private ImageButton ibLike;
    private ImageButton ibComment;
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

        tvFeedUsername = findViewById(R.id.tvFeedUsername);
        ivFeedImage = findViewById(R.id.ivFeedImage);
        tvFeedDescription = findViewById(R.id.tvFeedDescription);
        tvTimeAgo = findViewById(R.id.tvTimeAgo);
        tvPostUsername = findViewById(R.id.tvPostUsername);
        tvNumberLikes = findViewById(R.id.tvNumberLikes);
        ibLike = findViewById(R.id.ibLike);
        ibComment = findViewById(R.id.ibComment);

        // unwrap the movie passed in via intent, using its simple name as a key
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvFeedDescription.setText(post.getDescription());
        tvFeedUsername.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivFeedImage);
        }
        tvTimeAgo.setText(post.getTimeAgo());
        tvPostUsername.setText(post.getUser().getUsername());
        tvNumberLikes.setText("" + post.getLikes() + " Likes");


    }
}
