package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class DetailsActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvStatus;
    TextView tvCategory;
    RatingBar ratingBar;
    ImageView ivImage;
    public String instanceID;

    private Button addnewbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_window);

        tvName = findViewById(R.id.name);
        tvStatus = findViewById(R.id.snippet);
        tvCategory = findViewById(R.id.category);
        ratingBar = findViewById(R.id.ratingBar);
        ivImage = findViewById(R.id.ivImage);

        addnewbtn = (Button) findViewById(R.id.addnewbtn);
        addnewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, AddRatingActivity.class);
                ParseObject object = getIntent().getParcelableExtra("Plc");
                if(object == null) Toast.makeText(DetailsActivity.this, "no object", Toast.LENGTH_SHORT).show();
                //i.putExtra("plc", (Bundle) getIntent().getParcelableExtra("Plc"));
                i.putExtra("plc", object);
                startActivity(i);
            }
        });

        String title = getIntent().getStringExtra("name");
        if (title == null) {
            title = "N/A";
        }
        tvName.setText(title);
        String category = getIntent().getStringExtra("category");
        if (category == null) {
            category = "N/A";
        }
        tvCategory.setText(category);

        ParseObject object = getIntent().getParcelableExtra("Plc");
        ParseFile image = object.getParseFile("Image");
        if(image == null) Toast.makeText(DetailsActivity.this, "no", Toast.LENGTH_SHORT).show();
        image.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // Decode the Byte[] into
                    // Bitmap
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    // initialize
                    //ImageView image = (ImageView) findViewById(R.id.image);
                    // Set the Bitmap into the
                    // ImageView
                    ivImage.setImageBitmap(bmp);
                } else {
                    Log.d("test",
                            "Problem load image the data.");
                }
            }
        });

        instanceID = object.getObjectId();

        queryPosts();

    }

    private void queryPosts() {
        ParseQuery<Submission> query = ParseQuery.getQuery(Submission.class);
        query.include(Submission.KEY_USER);
        query.findInBackground(new FindCallback<Submission>() {
            @Override
            public void done(List<Submission> objects, ParseException e) {
                if (e != null) {
                    return;
                }
                for (Submission submission : objects) {
                    if(submission.getObject().getObjectId().equals(instanceID)){
                        int rating = submission.getRating();
                        ratingBar.setRating(rating);
                        String status = submission.getStatus();
                        if (status == null) {
                            status = "N/A";
                        }
                        tvStatus.setText(status);
                    }

                }
            }
        });

    }
}
