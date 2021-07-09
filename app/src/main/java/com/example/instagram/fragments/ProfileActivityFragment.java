package com.example.instagram.fragments;

import android.util.Log;

import com.example.instagram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivityFragment extends TimelineActivityFragment{

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);// specify what type of data we want to query - Post.class
        query.include(Post.KEY_USER); // include data referred by user key
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
