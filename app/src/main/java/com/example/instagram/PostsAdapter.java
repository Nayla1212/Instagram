package com.example.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFeedUsername;
        private ImageView ivFeedImage;
        private TextView tvFeedDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFeedUsername = itemView.findViewById(R.id.tvFeedUsername);
            ivFeedImage = itemView.findViewById(R.id.ivFeedImage);
            tvFeedDescription = itemView.findViewById(R.id.tvFeedDescription);
        }


        public void bind(Post post) {
            // Bind the post data to the view elements
            tvFeedDescription.setText(post.getDescription());
            tvFeedUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivFeedImage);
            }
        }
    }
}
