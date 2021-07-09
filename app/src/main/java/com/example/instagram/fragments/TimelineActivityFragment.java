package com.example.instagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.ComposeActivity;
import com.example.instagram.Post;
import com.example.instagram.PostsAdapter;
import com.example.instagram.R;
import com.example.instagram.TimelineActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivityFragment extends Fragment {

    public static final String TAG = "TimelineActivityFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;

    public TimelineActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);// set the adapter on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));// set the layout manager on the recycler view
        queryPosts();// query posts from Parstagram

        //Swipe Refresh Layout
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);// Lookup the swipe container view
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

    protected void queryPosts() {
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
}