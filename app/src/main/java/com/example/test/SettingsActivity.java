package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {



    private RadioGroup radioGroup;
    private RadioButton all_genderrb, men_womenrb, menrb, womenrb, allrb;
    private Button button;
    private TextView textView;

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
                /*int selectedId = radioGroup.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if(selectedId == all_genderrb.getId()) {
                    textView.setText("All Gender Restrooms will be shown");
                } else if(selectedId == men_womenrb.getId()) {
                    textView.setText("Men/Women's Restrooms will be shown");
                }else if(selectedId == menrb.getId()) {
                    textView.setText("Men's Restrooms will be shown");
                } else if(selectedId == womenrb.getId()) {
                    textView.setText("Women's Restrooms will be shown");
                } else {
                    textView.setText("All Restrooms will be shown");
                }*/
                Toast.makeText(getApplicationContext(), "Filter has been applied", Toast.LENGTH_SHORT).show();
            }
        });
    }

}