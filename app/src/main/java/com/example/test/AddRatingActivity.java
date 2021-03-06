package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddRatingActivity extends AppCompatActivity {

    private RatingBar starrb;
    private EditText commentet;
    private EditText statuset;
    private Button cancelbtn;
    private Button savebtn;

    private final String TAG = "AddRatingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rating);

        starrb = (RatingBar)findViewById(R.id.starrb);
        commentet = (EditText)findViewById(R.id.commentet);
        statuset = (EditText)findViewById(R.id.statuset);
        savebtn = (Button)findViewById(R.id.savebtn);
        cancelbtn = (Button)findViewById(R.id.cancelbtn);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddRatingActivity.this, MapsActivity.class);
                Toast.makeText(getApplicationContext(), "Comment canceled.", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddRatingActivity.this, MapsActivity.class);
                ParseObject currentPlace = getIntent().getParcelableExtra("plc");
                if(currentPlace == null) {
                    //Log.d(TAG, "Current place null");
                    Toast.makeText(AddRatingActivity.this, "CurrentPlace is null :(", Toast.LENGTH_SHORT).show();
                    return;
                }
                float rating = starrb.getRating();
                if(rating == 0) {
                    Toast.makeText(AddRatingActivity.this, "Please add a rating", Toast.LENGTH_SHORT).show();
                    return;
                }
                String comment = commentet.getText().toString();
                String status = statuset.getText().toString();

                savePost(rating, comment, status, currentPlace);

                Toast.makeText(getApplicationContext(), "Comment posted.", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }

    private void savePost(float rating, String comment, String status, ParseObject currentPlace) {
        Submission submission = new Submission();
        submission.setRating(rating);
        submission.setComment(comment);
        submission.setStatus(status);
        submission.setPlace(currentPlace);
        submission.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e("AddRatingActivity", "Error while saving", e);
                    Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT);
                }
                Log.i("AddRatingActivity", "Submission was posted successfully");
                starrb.setRating(0);
                commentet.setText("");
                statuset.setText("");
            }
        });
    }
}