ONLY WORKS FOR "DKI JAKARTA" AREAS!!!
You can change database values for using in another areas.

20 Dec 2017 - Version 1.1
*Set your Google Maps API Key in google_maps_api.xml
*Using dependencies:
    compile 'com.android.support:appcompat-v7:26.+'
    compile 'com.android.support:design:26.+'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    compile 'com.google.android.gms:play-services-maps:10.2.1'
    compile 'com.google.android.gms:play-services-location:10.2.1'
    compile 'com.android.support:support-v4:26.+'
    compile 'com.mcxiaoke.volley:library-aar:1.0.0'
    compile 'com.google.maps.android:android-maps-utils:0.5'
*Using permissions:
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
*Change value "url" in ListActivity.java to your URL, example: "http://YOUR_URL/index.php?from="
*Change value "url" in RouteActivity.java to your URL, example: "http://YOUR_URL/index.php?from="
*Set your Google Directions API Key to variable "googleApiKey" in RouteActivity.java (function getDirectionsUrl)

21 Nov 2017 - Version 1.0
