package com.vickzkater.caripuskesmas;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CariFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private double latitude, longitude;
    public static final int REQUEST_LOCATION_CODE = 99;

    LatLng latLng;
    String title;
    MarkerOptions markerOptions = new MarkerOptions();
    public static final String ID = "id";
    public static final String TITLE = "nama";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    private String url = "http://kiniditech.com/android/maps/";
    String tag_json_obj = "json_obj_req";

    double end_lat, end_lng;
    int lengthRadius = 3000;
    int getAreaDone;
    double nearLat, nearLng;
    double nearDistance = lengthRadius;
    String nearTitle, viaTransport = "Roda Empat";

    public CariFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cari, container, false);

        // Get mode transport
        final Spinner modeTransport = v.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.mode_transport, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        modeTransport.setAdapter(adapter);

        Button btn_cari = v.findViewById(R.id.B_cari);
        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Sedang mencari...", Toast.LENGTH_SHORT).show();
                getMarkers();
                viaTransport = modeTransport.getSelectedItem().toString();
                Toast.makeText(getActivity(), "Menampilkan jalur via "+viaTransport, Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapmain);
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
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                    else //permission is denied
                    {
                        Toast.makeText(getActivity(), "Permission is Denied!!!", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
        }
    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
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
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        Toast.makeText(getActivity(), "Sedang mencari...", Toast.LENGTH_SHORT).show();
        getMarkers();
    }

    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        client.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();
        }

        LatLng currentDinat = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions currentMarker = new MarkerOptions();
        currentMarker.position(currentDinat);
        currentMarker.title("Lokasi Anda");
        currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mMap.addMarker(currentMarker);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentDinat, 12));

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, (com.google.android.gms.location.LocationListener) this);
        }
    }

    // Fungsi get JSON marker
    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("tempat");
                    JSONArray jsonArray = new JSONArray(getObject);

                    mMap.clear();

                    // set marker current location
                    LatLng currentDinat = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    MarkerOptions currentMarker = new MarkerOptions();
                    currentMarker.position(currentDinat);
                    currentMarker.title("Lokasi Anda");
                    currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    mMap.addMarker(currentMarker);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentDinat, 15));

                    getAreaDone = 0;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        end_lat = Double.parseDouble(jsonObject.getString(LAT));
                        end_lng = Double.parseDouble(jsonObject.getString(LNG));

                        // Menambah data marker untuk di tampilkan ke google map
                        addMarker(end_lat, end_lng, title);
                    }

                    // set marker nearest place
                    markerOptions.snippet("Distance = "+nearDistance+" m");
                    LatLng add_position = new LatLng(nearLat, nearLng);
                    markerOptions.position(add_position);
                    markerOptions.title(nearTitle);
                    mMap.addMarker(markerOptions);

                    Toast.makeText(getActivity(), "Ditemukan Puskesmas terdekat dari lokasi Anda", Toast.LENGTH_LONG).show();

                    // set polyline from current location to nearest place
                    String urlApi = "";
                    Object dataTransfer[];
                    dataTransfer = new Object[3];
                    urlApi = getDirectionsUrl();
                    GetDirectionsData getDirectionsData = new GetDirectionsData();
                    dataTransfer[0] = mMap;
                    dataTransfer[1] = urlApi;
                    dataTransfer[2] = new LatLng(nearLat, nearLng);
                    getDirectionsData.execute(dataTransfer);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private String getDirectionsUrl()
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+nearLat+","+nearLng);

        if(viaTransport.equals("Roda Dua")){
            googleDirectionsUrl.append("&avoid=tolls");
        }else if(viaTransport.equals("Pejalan Kaki")){
            googleDirectionsUrl.append("&mode=walking");
        }

        googleDirectionsUrl.append("&key=AIzaSyCqtL9e8PkDUdjEbN_E8tphF2grHzG2F2s");

        return googleDirectionsUrl.toString();
    }

    private void addMarker(double mark_lat, double mark_lng, final String title) {
        // calculate distance between 2 locations (current location - target location)
        float results[] = new float[10];
        Location.distanceBetween(latitude, longitude, mark_lat, mark_lng, results);

        // set radius
        if(results[0] <= lengthRadius){
            getAreaDone++;

            if(nearDistance > results[0]){
                nearDistance = results[0];
                nearLat = mark_lat;
                nearLng = mark_lng;
                nearTitle = title;
            }
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Koneksi internet Anda sedang bermasalah, silahkan coba lagi.", Toast.LENGTH_LONG).show();
    }
}
