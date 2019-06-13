package com.vickzkater.caripuskesmas;

import android.Manifest;
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
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.vickzkater.caripuskesmas.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private static final String TAG = "RouteActivity";
    private GoogleMap mMap;
    private GoogleApiClient client;
    private double lastLat = -6.1303444;
    private double lastLng = 106.8160502;
    public static final int REQUEST_LOCATION_CODE = 99;
    MarkerOptions markerOptions = new MarkerOptions();
    String title;
    public static final String TITLE = "nama";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    String tag_json_obj = "json_obj_req";
    double end_lat, end_lng;
    int routeSelected;
    Spinner spinRoutes;
    String targetPlace;
    TextView text_place;
    double nearLat, nearLng;
    double nearDistance;
    String nearTitle, viaTransport = "Roda Empat";
    List<String> vRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        // get data from prev activity
        Bundle bundle = getIntent().getExtras();
        lastLat = bundle.getDouble("from_lat");
        lastLng = bundle.getDouble("from_lng");

        // set text target place name
        targetPlace = bundle.getString("target_place");
        text_place = findViewById(R.id.txt_targetplace);
        text_place.setText(targetPlace);

        spinRoutes = findViewById(R.id.spin_route);
        routeSelected = spinRoutes.getSelectedItemPosition();

        // Get mode transport
        final Spinner modeTransport = findViewById(R.id.spin_via);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mode_transport, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeTransport.setAdapter(adapter);

        Button btn_cari = findViewById(R.id.B_show);
        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RouteActivity.this, "Sedang mencari rute...", Toast.LENGTH_LONG).show();
                viaTransport = modeTransport.getSelectedItem().toString();
                routeSelected = spinRoutes.getSelectedItemPosition();
                getMarkers(routeSelected);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maprute);
        mapFragment.getMapAsync(this);
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
                    else // permission is denied
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
        Toast.makeText(this, "Sedang menyusun rute...", Toast.LENGTH_LONG).show();
        getMarkers(0);
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

    private void getMarkers(final int routeNo) {
        String query = "";
        try {
            query = URLEncoder.encode(targetPlace, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://[YOUR_URL]/index.php?from="+lastLat+","+lastLng+"&target="+query;
        Log.d(TAG, "getMarkers - url >> "+url);
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "getMarkers - response >> "+response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("tempat");
                    JSONArray jsonArray = new JSONArray(getObject);

                    mMap.clear();

                    // set marker current location
                    LatLng currentDinat = new LatLng(lastLat, lastLng);
                    MarkerOptions currentMarker = new MarkerOptions();
                    currentMarker.position(currentDinat);
                    currentMarker.title("Lokasi Anda");
                    currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(currentMarker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentDinat, 14));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        end_lat = Double.parseDouble(jsonObject.getString(LAT));
                        end_lng = Double.parseDouble(jsonObject.getString(LNG));

                        // Menambah data marker untuk di tampilkan ke google map
                        addMarker(end_lat, end_lng, title);
                    }

                    // set marker nearest place
                    markerOptions.snippet("Distance = "+String.format("%.2f", nearDistance)+" m");
                    LatLng add_position = new LatLng(nearLat, nearLng);
                    markerOptions.position(add_position);
                    markerOptions.title(nearTitle);
                    mMap.addMarker(markerOptions);

                    // set polyline from current location to target place
                    String urlApi;
                    Object dataTransfer[];
                    dataTransfer = new Object[3];
                    urlApi = getDirectionsUrl();

                    Log.d(TAG, "getMarkers - urlApi >> "+urlApi);

                    GetDirectionsData getDirectionsData = new GetDirectionsData();
                    dataTransfer[0] = mMap;
                    dataTransfer[1] = urlApi;
                    dataTransfer[2] = new LatLng(nearLat, nearLng);

                    vRoutes = getDirectionsData.getListRoutes(dataTransfer);
                    // adding data of nearby places to spinner
                    ArrayAdapter<String> adapterData;
                    adapterData = new ArrayAdapter<>(getApplicationContext(),
                            R.layout.my_spinner_item, R.id.tv_promo_txt, vRoutes);
                    adapterData.setDropDownViewResource(R.layout.my_spinner_item);
                    spinRoutes.setAdapter(adapterData);
                    spinRoutes.setSelection(routeSelected);

                    if(routeNo == 0){
                        getDirectionsData.execute(dataTransfer);
                        Toast.makeText(RouteActivity.this, "Rute telah berhasil ditampilkan", Toast.LENGTH_SHORT).show();
                    }else if(routeNo > 0){
                        int pilihan = routeNo - 1;
                        getDirectionsData.displaySelectedRoute(pilihan, dataTransfer);
                        Toast.makeText(RouteActivity.this, "Rute telah berhasil ditampilkan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RouteActivity.this, "Rute gagal ditampilkan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage());
                Toast.makeText(RouteActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void addMarker(double mark_lat, double mark_lng, final String title) {
        // calculate distance between 2 locations (current location - target location)
        float results[] = new float[10];
        Location.distanceBetween(lastLat, lastLng, mark_lat, mark_lng, results);

        nearDistance = results[0];
        nearLat = mark_lat;
        nearLng = mark_lng;
        nearTitle = title;
    }

    private String getDirectionsUrl() {
        String googleApiKey = "YOUR_API_KEY";

        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin=").append(lastLat).append(",").append(lastLng);
        googleDirectionsUrl.append("&destination=").append(nearLat).append(",").append(nearLng);

        if(viaTransport.equals("Roda Dua")){
            googleDirectionsUrl.append("&avoid=tolls");
        }else if(viaTransport.equals("Pejalan Kaki")){
            googleDirectionsUrl.append("&mode=walking");
        }

        googleDirectionsUrl.append("&alternatives=true");
        googleDirectionsUrl.append("&key=").append(googleApiKey);

        return googleDirectionsUrl.toString();
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

    }
}
