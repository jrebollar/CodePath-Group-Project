package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Submission> posts;

    @NonNull
    @Override
    public DetailsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_window, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsRecyclerAdapter.ViewHolder holder, int position) {
        Submission post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvStatus;
        TextView tvCategory;
        RatingBar ratingBar;
        ImageView ivImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvStatus = itemView.findViewById(R.id.snippet);
            tvCategory = itemView.findViewById(R.id.category);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ivImage = itemView.findViewById(R.id.ivImage);
        }

        public void bind(Submission post) {
            tvStatus.setText(post.getStatus());
            tvName.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
        }
    }
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Submission> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}