package com.vickzkater.caripuskesmas;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vicky on 16/11/2017.
 */

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if(!googlePlaceJson.isNull("name"))
            {
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity"))
            {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name", placeName);
            googlePlacesMap.put("vicinity", vicinity);
            googlePlacesMap.put("lat", latitude);
            googlePlacesMap.put("lng", longitude);
            googlePlacesMap.put("reference", reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;
    }

    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placeMap = null;

        for(int i = 0;i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }

    public int parseTotalDirections(String jsonData)
    {
        JSONObject jsonObject;
        int totalRute = 0;
        JSONArray testArray = null;

        try {
            jsonObject = new JSONObject(jsonData);
            testArray = jsonObject.getJSONArray("routes");
            totalRute = testArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return totalRute;
    }

    public String[] parseDirections(String jsonData, int routeNum)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            //jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs"); // show distance & duration
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(routeNum).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPaths(jsonArray);
    }

    public String[] getPaths(JSONArray googleStepsJson)
    {
        int count = googleStepsJson.length();
        String[] polylines = new String[count];

        for(int i = 0; i<count; i++)
        {
            try {
                polylines[i] = getPath(googleStepsJson.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return polylines;
    }

    public String getPath(JSONObject googlePathJson)
    {
        String polyline = "";
        try {
            polyline = googlePathJson.getJSONObject("polyline").getString("points");
        } catch (JSONException e) {

        }
        return polyline;
    }

    public List<String> parseListRoutes(int totalRoute, String jsonData)
    {
        List<String> routes = new ArrayList<>();
        routes.add("1. Semua Rute");
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        String distance;

        for(int i = 0; i<totalRoute; i++){
            try {
                jsonObject = new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("routes").getJSONObject(i).getJSONArray("legs"); // show distance & duration
                distance = jsonArray.getJSONObject(0).getJSONObject("distance").getString("text");
                routes.add(String.valueOf(i+2)+". Jarak: "+distance);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return routes;
    }
}
