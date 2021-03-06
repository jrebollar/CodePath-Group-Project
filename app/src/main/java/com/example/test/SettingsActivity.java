package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class SettingsActivity extends AppCompatActivity {



    private RadioGroup radioGroup;
    private RadioButton all_genderrb, men_womenrb, menrb, womenrb, allrb;
    private Button button;
    private TextView textView;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        radioGroup = (RadioGroup) findViewById(R.id.Filterrg);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.all_genderrb) {
                    Toast.makeText(getApplicationContext(), "All-Gender Restrooms will be shown",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.men_womenrb) {
                    Toast.makeText(getApplicationContext(), "Men/Women's Restrooms will be shown",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.menrb) {
                    Toast.makeText(getApplicationContext(), "Men's Restrooms will be shown",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.womenrb) {
                    Toast.makeText(getApplicationContext(), "Women's Restrooms will be shown",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "All Restrooms will be shown",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        all_genderrb = (RadioButton) findViewById(R.id.all_genderrb);
        men_womenrb = (RadioButton) findViewById(R.id.men_womenrb);
        menrb = (RadioButton) findViewById(R.id.menrb);
        womenrb = (RadioButton) findViewById(R.id.womenrb);
        allrb = (RadioButton) findViewById(R.id.allrb);
        textView = (TextView) findViewById(R.id.text);

        button = (Button)findViewById(R.id.saveBtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MapsActivity.class);
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if(selectedId == all_genderrb.getId()) {
                    i.putExtra("filter", "All Gender");
                    i.putExtra("filterSet", true);
                    i.putExtra("singleGender", false);
                    //textView.setText("All Gender Restrooms will be shown");
                } else if(selectedId == men_womenrb.getId()) {
                    i.putExtra("filter", "Men/Women");
                    i.putExtra("filterSet", true);
                    i.putExtra("singleGender", false);
                    //textView.setText("Men/Women's Restrooms will be shown");
                }else if(selectedId == menrb.getId()) {
                    i.putExtra("filter", "Men");
                    i.putExtra("filterSet", true);
                    i.putExtra("singleGender", true);
                    //textView.setText("Men's Restrooms will be shown");
                } else if(selectedId == womenrb.getId()) {
                    i.putExtra("filter", "Women");
                    i.putExtra("filterSet", true);
                    i.putExtra("singleGender", true);
                    //textView.setText("Women's Restrooms will be shown");
                } else {
                    i.putExtra("filter", "All");
                    i.putExtra("filterSet", false);
                    i.putExtra("singleGender", false);
                    //textView.setText("All Restrooms will be shown");
                }
                Toast.makeText(getApplicationContext(), "Filter has been applied", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

        btnLogout = findViewById(R.id.btnLogOut);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                ParseUser.logOut();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}