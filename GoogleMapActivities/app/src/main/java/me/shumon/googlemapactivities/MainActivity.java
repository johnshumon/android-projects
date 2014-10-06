package me.shumon.googlemapactivities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;


import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;



public class MainActivity extends FragmentActivity implements LocationListener{
    GoogleMap _googlMap;
//    Location _getinCurntPos;
    LatLng _curntPos;
    MarkerOptions _markerOptions;
    private static final LatLng LONDON_EYE = new LatLng(51.503441200000000000, -0.119678199999953000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setuping locatiomanager to perfrom location related operations
        LocationManager _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        _locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);

        _googlMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        if (_googlMap == null){
            Toast.makeText(this, "Google map not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onLocationChanged(Location _location) {
        double _lat = _location.getLatitude();
        double _lng = _location.getLongitude();

        _curntPos = new LatLng(_lat, _lng);

        // object to pass our current location to the map
        _markerOptions = new MarkerOptions();
        _markerOptions.position(_curntPos);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sethybrid:
                _googlMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case R.id.menu_setterrain:
                _googlMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            case R.id.menu_showtraffic:
                _googlMap.setTrafficEnabled(true);
                break;

            case R.id.menu_zoomin:
                _googlMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;

            case R.id.menu_zoomout:
                _googlMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;

            case R.id.menu_gotolocation:
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(LONDON_EYE)     // Sets the center of the map to Golden Gate Bridge
                        .zoom(17)               // Sets the zoom
                        .bearing(90)            // Sets the orientation of the camera to east
                        .tilt(30)               // Sets the tilt of the camera to 30 degrees
                        .build();               // Creates a CameraPosition from the builder
                _googlMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        cameraPosition));
                break;

            case R.id.menu_addmarker:
                // ---using the default marker---

                _googlMap.addMarker(new MarkerOptions()
                    .position(LONDON_EYE)
                    .title("London Eye") .snippet("London, England")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                break;

            case R.id.menu_showcurrentlocation:
                _googlMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_curntPos, 17f));
                _googlMap.addMarker(_markerOptions);

//                _googlMap.setMyLocationEnabled(true);
//                Location _curntLoc = _googlMap.getMyLocation();
//                LatLng _curntLatLng = new LatLng(_curntLoc.getLatitude(), _curntLoc.getLongitude());

//                CameraPosition _curntPos = new CameraPosition.Builder().target(_curntLatLng)
//                        .zoom(17).bearing(90).tilt(30).build();
//                _googlMap.animateCamera(CameraUpdateFactory.newCameraPosition(_curntPos));
//                _markerOptions.position(_curntLatLng);
                break;

            case R.id.menu_lineconnecttwopoints:
                _googlMap.addMarker(new MarkerOptions()
                        .position(LONDON_EYE).title("London Eye").snippet("London, England")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                _googlMap.addPolyline(new PolylineOptions()
                        .add(_curntPos, LONDON_EYE).width(5).color(Color.RED));
                _googlMap.addMarker(_markerOptions.position(_curntPos)
                        .title("Your Location").snippet("That's where you are!"));

        }
        return true;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
