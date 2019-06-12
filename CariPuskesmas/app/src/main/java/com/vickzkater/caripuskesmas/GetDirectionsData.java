package com.vickzkater.caripuskesmas;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by vicky on 16/11/2017.
 */

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    LatLng latLng;

    String googleData;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        // get total routes
        int totalRoutes;
        DataParser parser2 = new DataParser();
        totalRoutes = parser2.parseTotalDirections(s);

        // generate/display the route
        for(int x=0; x<totalRoutes; x++){
            String[] directionsList;
            DataParser parser = new DataParser();
            directionsList = parser.parseDirections(s, x);
            displayDirection(directionsList, x);
        }

    }

    public void displayDirection(String[] directionList, int directionNum) {
        int count = directionList.length;
        for(int i = 0; i<count; i++)
        {
            PolylineOptions options = new PolylineOptions();
            switch(directionNum){
                case 1: options.color(Color.BLUE); break;
                case 2: options.color(Color.GREEN); break;
                case 3: options.color(Color.RED); break;
                case 4: options.color(Color.BLACK); break;
                case 5: options.color(Color.YELLOW); break;
            }
            options.width(10);
            options.addAll(PolyUtil.decode(directionList[i]));

            mMap.addPolyline(options);
        }
    }

    private String getGoogleData(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleData;
    }

    public int getTotalRoutes(Object... objects) {
        // get Google data from API
        String googledata = this.getGoogleData(objects);

        int totalRoutes = 0;
        DataParser parser = new DataParser();
        totalRoutes = parser.parseTotalDirections(googledata);

        return totalRoutes;
    }

    public void displaySelectedRoute(int orderNo, Object... objects) {
        // get Google data from API
        String googledata = this.getGoogleData(objects);

        String[] directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(googledata, orderNo);
        displayDirection(directionsList, orderNo);
    }

    public List<String> getListRoutes(Object... objects) {
        int totalRoutes = this.getTotalRoutes(objects);

        // get Google data from API
        String googledata = this.getGoogleData(objects);

        List<String> routes;
        DataParser parser = new DataParser();
        routes = parser.parseListRoutes(totalRoutes, googledata);

        return routes;
    }
}
