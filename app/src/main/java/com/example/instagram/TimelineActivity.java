package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "FeedActivity";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rvPosts = findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(this, allPosts);
        rvPosts.setAdapter(adapter);// set the adapter on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(this));// set the layout manager on the recycler view
        queryPosts();// query posts from Parstagram
    }


    private void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btnLogOut){
            //TODO: does it know that I'm referring to the current ParseUser? basically does this work?
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.btnHome){
            Intent intent = new Intent(this, TimelineActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.btnComposeActivity){
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
