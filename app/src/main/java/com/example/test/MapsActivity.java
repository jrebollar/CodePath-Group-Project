package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.test.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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

        // Add a marker in Sydney and move the camera
        LatLng cpp = new LatLng(34.0583, -117.8218);
        mMap.addMarker(new MarkerOptions().position(cpp).title("Marker at CPP"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cpp));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // sets zoom

        showRestroomsInMap(mMap);
    }

    private void showRestroomsInMap(final GoogleMap googleMap){

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereExists("Location");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override  public void done(List<ParseUser> restrooms, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < restrooms.size(); i++) {
                        LatLng rrLocation = new LatLng(restrooms.get(i).getParseGeoPoint("Location").getLatitude(), restrooms.get(i).getParseGeoPoint("Location").getLongitude());
                        Marker marker = googleMap.addMarker(new MarkerOptions().position(rrLocation).title(restrooms.get(i).getString("Name")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        int finalI = i;
                        marker.setTitle(restrooms.get(finalI).getString("username"));
                        String name = marker.getTitle();
                        marker.setSnippet(restrooms.get(finalI).getString("Category"));
                        String category = marker.getSnippet();
                        googleMap.setOnMarkerClickListener(marker1 -> {
                            //String status = restrooms.get(finalI).getString("Status");
                            //int rating = restrooms.get(finalI).getNumber("Rating").intValue();
                            Intent i1 = new Intent(MapsActivity.this, DetailsActivity.class);
                            i1.putExtra("name", name);
                            //i1.putExtra("status", status);
                            i1.putExtra("category", category);
                            //i.putExtra("rating", rating);
                            startActivity(i1);
                            return false;
                        });
                    }
                } else {
                    // handle the error
                    Log.d("restroom", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery.clearAllCachedResults();
    }
}