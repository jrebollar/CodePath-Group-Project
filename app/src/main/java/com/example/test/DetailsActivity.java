package com.example.test;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class DetailsActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvStatus;
    TextView tvCategory;
    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window);
        tvName = findViewById(R.id.name);
        tvStatus = findViewById(R.id.snippet);
        tvCategory = findViewById(R.id.category);
        ratingBar = findViewById(R.id.ratingBar);

        String title = getIntent().getStringExtra("name");
        if(title == null){
            title = "N/A";
        }
        String snippet = getIntent().getStringExtra("status");
        if(snippet == null){
            snippet = "N/A";
        }
        String category = getIntent().getStringExtra("category");
        if(category == null){
            category = "N/A";
        }
        //int rating = getIntent().getIntExtra("rating", 0);
        tvName.setText(title);
        tvStatus.setText(snippet);
        tvCategory.setText(category);
        //ratingBar.setRating(rating);

    }
}
