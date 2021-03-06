package com.example.test;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.test.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private String filter = "All";
    private boolean haveFilter = false;
    private boolean isSingleGender = false;

    // Buttons
    public static final String TAG = "MapsActivity";
    private boolean clicked = false;
    FloatingActionButton btnMenu;
    FloatingActionButton btnSettings;
    FloatingActionButton btnAdd;
    // Buttons end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        filter = intent.getStringExtra("filter");
        haveFilter = intent.getBooleanExtra("filterSet", false);
        isSingleGender = intent.getBooleanExtra("singleGender", false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Buttons
        btnMenu = (FloatingActionButton) findViewById(R.id.btnMenu);
        btnSettings = (FloatingActionButton) findViewById(R.id.btnSettings);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapsActivity.this, "btnMenu Button!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btnMenu Button!");

                onMenuButtonClicked();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MapsActivity.this, "btnSettings Button!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btnSettings Button!");
                startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, AddNewActivity.class));
            }
        });
        // Buttons end
    }

    // Button logic
    private void onMenuButtonClicked() {
        setVisibilty(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisibilty(Boolean clicked) {
        if(!clicked) {
            btnSettings.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
        }
        else {
            btnSettings.setVisibility(View.INVISIBLE);
            btnAdd.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation(Boolean clicked) {
        Animation rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        Animation toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        if(!clicked) {
            btnMenu.startAnimation(rotateClose);
            btnSettings.startAnimation(fromBottom);
            btnAdd.startAnimation(fromBottom);
        }
        else {
            btnMenu.startAnimation(rotateOpen);
            btnSettings.startAnimation(toBottom);
            btnAdd.startAnimation(toBottom);
        }
    }
    // Button logic end

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*
        This prevents the user from scrolling.
        Im going to keep it here just in case I need to use it to lock CPP's location.
        -->googleMap.getUiSettings().setScrollGesturesEnabled(false);
         */

        mMap = googleMap;

        // Add a marker in CPP and move the camera
        LatLng cpp = new LatLng(34.0583, -117.8218);
        //mMap.addMarker(new MarkerOptions().position(cpp).title("Marker at CPP"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cpp));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // sets zoom

        showRestroomsInMap(mMap);
    }

    private void showRestroomsInMap(final GoogleMap googleMap){
        String[] detailsID = new String[36];
        String[] status = {""};
        String[] rstrmID = new String[36];

        //ParseQuery<ParseUser> query = ParseUser.getQuery();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("place");
        query.whereExists("Location");

        if(isSingleGender) {
            ArrayList<String> list = new ArrayList<>();
            list.add("Men/Women");
            list.add(filter);
            query.whereContainedIn("Category", list);
        } else if(haveFilter) query.whereEqualTo("Category", filter);

        query.findInBackground((restrooms, e) -> {
            if (e == null) {
                for(int i = 0; i < restrooms.size(); i++) {
                    LatLng rrLocation = new LatLng(restrooms.get(i).getParseGeoPoint("Location").getLatitude(), restrooms.get(i).getParseGeoPoint("Location").getLongitude());
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(rrLocation).title(restrooms.get(i).getString("Name")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    marker.setTitle(restrooms.get(i).getString("name"));
                    marker.setSnippet(restrooms.get(i).getString("Category"));
                    detailsID[i] = restrooms.get(i).getObjectId();

                }
            } else {
                // handle the error
                Log.d("restroom", "Error: " + e.getMessage());
            }

            /*ParseQuery<ParseObject> query1 = new ParseQuery<>("restrooms");
            query1.whereExists("Rating");
            query1.findInBackground((objects, e1) -> {
                for (int j = 0; j < objects.size(); j++) {
                    rstrmID[j] = objects.get(j).getParseObject("PlacePointer").getObjectId();
                }
            });*/

            googleMap.setOnMarkerClickListener(marker1 -> {

                String name = marker1.getTitle();
                String category = marker1.getSnippet();

                Intent i1 = new Intent(MapsActivity.this, DetailsActivity.class);

                /*query.getInBackground(detailsID[Integer.parseInt(marker1.getId().replaceAll("[^0-9]", ""))], new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            i1.putExtra("Plc", object);
                        } else {
                            Log.e(TAG, "Error retrieving user", e);
                            Toast.makeText(MapsActivity.this, "in the here", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                try {
                    i1.putExtra("Plc", query.get(detailsID[Integer.parseInt(marker1.getId().replaceAll("[^0-9]", ""))]));
                } catch (ParseException parseException) {
                    Log.e(TAG, "Error retrieving user", parseException);
                    Toast.makeText(MapsActivity.this, "in the here", Toast.LENGTH_SHORT).show();
                }

                /*try {
                    i1.putExtra("status", query1.get(rstrmID[Integer.parseInt(marker1.getId().replaceAll("[^0-9]", ""))]));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }*/

                // moved i1 declaration stmt so I can add the current object thx -Rebecca
                i1.putExtra("name", name);
                i1.putExtra("category", category);

                //i1.putExtra("rating", rating[0]);
                startActivity(i1);
                return false;
            });
        });

        ParseQuery.clearAllCachedResults();
    }
}