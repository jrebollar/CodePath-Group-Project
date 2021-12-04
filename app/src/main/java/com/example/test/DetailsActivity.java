package com.example.test;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseObject;


public class DetailsActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvStatus;
    TextView tvCategory;
    RatingBar ratingBar;
    ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window);
        tvName = findViewById(R.id.name);
        tvStatus = findViewById(R.id.snippet);
        tvCategory = findViewById(R.id.category);
        ratingBar = findViewById(R.id.ratingBar);
        ivImage = findViewById(R.id.ivImage);

        String title = getIntent().getStringExtra("name");
        if(title == null){
            title = "N/A";
        }
        String status = getIntent().getStringExtra("status");
        if(status == null || status.equals("")){
            status = "N/A";
        }
        String category = getIntent().getStringExtra("category");
        if(category == null){
            category = "N/A";
        }
        int rating = getIntent().getIntExtra("rating", 0);
        tvName.setText(title);
        tvStatus.setText(status);
        tvCategory.setText(category);
        ratingBar.setRating(rating);

    }
}
