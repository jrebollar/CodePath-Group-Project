package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.All_Gender:
                if (checked)
                {
                    // Put some meat on the sandwich
                    Toast.makeText(SettingsActivity.this, "All Gender restrooms will be shown", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // Remove the meat
                    Toast.makeText(SettingsActivity.this, "All Gender restrooms will not be shown", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.Men_Women:
                if (checked)
                {
                    // Cheese me
                    Toast.makeText(SettingsActivity.this, "Men/Women restrooms will be shown", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // I'm lactose intolerant
                    Toast.makeText(SettingsActivity.this, "Men/Women restrooms will not be shown", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}