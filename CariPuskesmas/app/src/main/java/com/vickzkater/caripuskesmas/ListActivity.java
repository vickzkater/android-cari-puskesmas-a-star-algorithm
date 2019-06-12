package com.vickzkater.caripuskesmas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vickzkater.caripuskesmas.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{
    private GoogleMap mMap;
    private GoogleApiClient client;
    private double lastLat = -6.1303444;
    private double lastLng = 106.8160502;
    public static final int REQUEST_LOCATION_CODE = 99;
    MarkerOptions markerOptions = new MarkerOptions();
    String title;
    //public static final String ID = "id";
    public static final String TITLE = "nama";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    String tag_json_obj = "json_obj_req";
    double end_lat, end_lng;
    int lengthRadius = 3000;
    int getAreaDone, maxArea = 20;
    EditText edit_radius;
    Spinner spinPlaces;
    List<String> vPlaces;
    long tmpRadius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        spinPlaces = findViewById(R.id.spin_places);
        edit_radius = findViewById(R.id.edit_getradius);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapcari);
        mapFragment.getMapAsync(this);

        Button btn_action = findViewById(R.id.B_find_route);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPlace = spinPlaces.getSelectedItem().toString();
                Bundle bundle = new Bundle();
                    bundle.putString("target_place", selectedPlace);
                    bundle.putDouble("from_lat", lastLat);
                    bundle.putDouble("from_lng", lastLng);
                Intent findIntent = new Intent(ListActivity.this, RouteActivity.class);
                    findIntent.putExtras(bundle);
                startActivity(findIntent);
            }
        });

        Button btn_refresh = findViewById(R.id.B_find_places);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMarkers();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission is granted
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                    else //permission is denied
                    {
                        Toast.makeText(this, "Permission is Denied!!!", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        getMarkers();
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLat = location.getLatitude();
        lastLng = location.getLongitude();

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    private void getMarkers() {
        String url = "http://YOUR_URL/index.php?from="+lastLat+","+lastLng;
        Log.d("myLog", "Post to URL:"+url); // console log
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);

                try {
                    Toast.makeText(ListActivity.this, "Sedang mencari...", Toast.LENGTH_SHORT).show();

                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("tempat");
                    JSONArray jsonArray = new JSONArray(getObject);

                    mMap.clear();
                    vPlaces = new ArrayList<>();

                    // set marker current location
                    LatLng currentDinat = new LatLng(lastLat, lastLng);
                    MarkerOptions currentMarker = new MarkerOptions();
                    currentMarker.position(currentDinat);
                    currentMarker.title("Lokasi Anda");
                    currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(currentMarker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentDinat, 12));

                    getAreaDone = 0;
                    // Get radius
                    tmpRadius = Long.parseLong(edit_radius.getText().toString());
                    // check maximum value of Radius (max value of int)
                    if(tmpRadius > 2147483647){
                        Toast.makeText(ListActivity.this, "Radius yang dimasukkan terlalu besar (Max 2147483647), silahkan ubah dan coba lagi.", Toast.LENGTH_LONG).show();
                    }else{
                        lengthRadius = Integer.parseInt(edit_radius.getText().toString());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            title = jsonObject.getString(TITLE);
                            end_lat = Double.parseDouble(jsonObject.getString(LAT));
                            end_lng = Double.parseDouble(jsonObject.getString(LNG));

                            // Menambah data marker untuk di tampilkan ke google map
                            addMarker(end_lat, end_lng, title);
                        }

                        if(getAreaDone > 0){
                            if(getAreaDone == maxArea){
                                Toast.makeText(ListActivity.this, "Ditemukan "+String.valueOf(getAreaDone)+" Puskesmas terdekat (MAX "+maxArea+" lokasi)",
                                        Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(ListActivity.this, "Ditemukan "+String.valueOf(getAreaDone)+" Puskesmas terdekat", Toast.LENGTH_LONG).show();
                            }

                            // adding data of nearby places to spinner
                            ArrayAdapter<String> adapterPlaces;
                            adapterPlaces = new ArrayAdapter<>(getApplicationContext(),
                                    R.layout.my_spinner_item, R.id.tv_promo_txt, vPlaces);
                            adapterPlaces.setDropDownViewResource(R.layout.my_spinner_item);
                            spinPlaces.setAdapter(adapterPlaces);
                        }else{
                            Toast.makeText(ListActivity.this, "Puskesmas tidak ditemukan dalam radius ("+String.valueOf(lengthRadius)+" m), silahkan ubah nilai Radius",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        if(tmpRadius < 2147483647) {
            AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        }
    }

    private void addMarker(double mark_lat, double mark_lng, final String title) {
        // calculate distance between 2 locations (current location - target location)
        float results[] = new float[10];
        Location.distanceBetween(lastLat, lastLng, mark_lat, mark_lng, results);

        // check radius
        if(results[0] <= lengthRadius){
            getAreaDone++;
            // adding to List<String>
            vPlaces.add(title.substring(10)+" ("+String.format("%.2f", results[0])+"m)");
            // adding to snippet (pin-map description)
            markerOptions.snippet("Distance = "+String.format("%.2f", results[0])+" m");

            LatLng add_position = new LatLng(mark_lat, mark_lng);
            markerOptions.position(add_position);
            markerOptions.title(title);
            mMap.addMarker(markerOptions);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(ListActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(ListActivity.this, "Koneksi internet perangkat sedang bermasalah, silahkan coba lagi", Toast.LENGTH_LONG).show();
    }

}
