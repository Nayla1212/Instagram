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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "FeedActivity";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;


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

        //Swipe Refresh Layout

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);// Lookup the swipe container view
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {// Setup refresh listener which triggers new data loading
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, // Configure the refreshing colors
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync(int page) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);// specify what type of data we want to query - Post.class
        query.include(Post.KEY_USER); // include data referred by user key
        query.setLimit(20); // limit query to latest 20 items
        query.addDescendingOrder("createdAt"); // order posts by creation date (newest first)
        query.findInBackground(new FindCallback<Post>() { // start an asynchronous call for posts
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
                allPosts.addAll(posts);
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);// specify what type of data we want to query - Post.class
        query.include(Post.KEY_USER); // include data referred by user key
        query.setLimit(20); // limit query to latest 20 items
        query.addDescendingOrder("createdAt"); // order posts by creation date (newest first)
        query.findInBackground(new FindCallback<Post>() { // start an asynchronous call for posts
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
