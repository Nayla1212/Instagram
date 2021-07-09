package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseClassName;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(){};

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

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvFeedUsername;
        private ImageView ivFeedImage;
        private TextView tvFeedDescription;
        private TextView tvTimeAgo;
        private TextView tvPostUsername;
        private TextView tvNumberLikes;
        private ImageButton ibLike;
        private ImageButton ibComment;
        private boolean liked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvFeedUsername = itemView.findViewById(R.id.tvFeedUsername);
            ivFeedImage = itemView.findViewById(R.id.ivFeedImage);
            tvFeedDescription = itemView.findViewById(R.id.tvFeedDescription);
            tvTimeAgo = itemView.findViewById(R.id.tvTimeAgo);
            tvPostUsername = itemView.findViewById(R.id.tvPostUsername);
            tvNumberLikes = itemView.findViewById(R.id.tvNumberLikes);
            ibLike = (ImageButton) itemView.findViewById(R.id.ibLike);
            ibComment = (ImageButton) itemView.findViewById(R.id.ibComment);
            liked = false;
        }


        public void bind(Post post) {
            // Bind the post data to the view elements
            tvFeedDescription.setText(post.getDescription());
            tvFeedUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivFeedImage);
            }
            tvTimeAgo.setText(post.getTimeAgo());
            tvPostUsername.setText(post.getUser().getUsername());
            tvNumberLikes.setText("" + post.getLikes() + " Likes");

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(liked == true){
                        Toast.makeText(context, "You already liked this", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        int position = getAdapterPosition();
                        Post post = posts.get(position);
                        int likes = post.getLikes().intValue() + 1;
                        post.setLike(likes);
                        tvNumberLikes.setText("" + post.getLikes() + " Likes");
                        ibLike.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.like_active));
                        liked = true;
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Post post = posts.get(position);
            if(position != RecyclerView.NO_POSITION){
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(intent);
            }
        }
    }
}
