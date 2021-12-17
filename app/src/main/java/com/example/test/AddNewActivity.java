package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.*;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddNewActivity extends AppCompatActivity {

    public static final String TAG = "AddNewActivity";

    private EditText etLocation;
    private Button btnPicture;
    private ImageView ivPhoto;
    private CheckBox cbMale;
    private CheckBox cbFemale;
    private CheckBox cbAllGender;
    private Button btnSubmit;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        etLocation = findViewById(R.id.etLocation);
        btnPicture = findViewById(R.id.btnPicture);
        ivPhoto = findViewById(R.id.ivPhoto);
        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);
        cbAllGender = findViewById(R.id.cbAllGender);
        btnSubmit = findViewById(R.id.btnSubmit);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = etLocation.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                MapsActivity maps = new MapsActivity();

                if (location.isEmpty()) {
                    Toast.makeText(AddNewActivity.this, "Restroom location is empty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (photoFile == null || ivPhoto.getDrawable() == null) {
                    Toast.makeText(AddNewActivity.this, "There is no image.", Toast.LENGTH_SHORT).show();
                } else if (!cbAllGender.isChecked() && !cbFemale.isChecked() && !cbMale.isChecked()) {
                    Toast.makeText(AddNewActivity.this, "Gender identifiers are unchecked.", Toast.LENGTH_SHORT).show();
                } else {
                    newRestroom(location, currentUser, photoFile);
                    //maps.saveCurrentUserLocation();
                    Toast.makeText(AddNewActivity.this, "New location added!", Toast.LENGTH_SHORT).show();
                    goMapsActivity();
                }
            }
        });

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewActivity.this, "Capture image.", Toast.LENGTH_SHORT).show();
                onLaunchCamera(ivPhoto);
            }
        });
    }

    private void newRestroom(String location, ParseUser currentUser, File photoFile) {
        Place newRestroom = new Place();
        newRestroom.setName(location);
        newRestroom.setImage(new ParseFile(photoFile));
        newRestroom.setUser(currentUser);

        if (cbFemale.isChecked() && cbAllGender.isChecked() || cbMale.isChecked() && cbAllGender.isChecked()) {
            newRestroom.setCategory("All Gender");
        }
        else if (cbFemale.isChecked() && cbMale.isChecked()) {
            newRestroom.setCategory("Men/Women");
        }
        else if (cbFemale.isChecked()) {
            newRestroom.setCategory("Women");
        }
        else if (cbMale.isChecked()) {
            newRestroom.setCategory("Men");
        }
        else if (cbAllGender.isChecked()) {
            newRestroom.setCategory("All Gender");
        }
        /*
        if(ActivityCompat.checkSelfPermission(AddNewActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddNewActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddNewActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            Location coords = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            ParseGeoPoint currentUserLocation = new ParseGeoPoint(coords.getLatitude(), coords.getLongitude());
            newRestroom.setLocation(currentUserLocation);
        }*/

        ParseGeoPoint currentUserLocation = new ParseGeoPoint(34.0583, -117.8218);
        newRestroom.setLocation(currentUserLocation);

        newRestroom.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(AddNewActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                etLocation.setText("");
                ivPhoto.setImageResource(0);
            }
        });
    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(AddNewActivity.this, "com.codepath.fileprovider.test", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.ivPhoto);
                ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goMapsActivity() {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }
}