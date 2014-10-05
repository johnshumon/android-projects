package me.shumon.simplecurrentlocation;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements LocationListener{

    // Google Map
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setuping locatiomanager to perfrom location related operations
        LocationManager _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        _locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        // To change the map type to Satellite
        // googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // To show our current location in the map with dot
        // googleMap.setMyLocationEnabled(true);


        // To listen action whenever we click on the map
        googleMap.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
				/*
				 * LatLng:Class will give us selected position lattigude and
				 * longitude values
				 */
                Toast.makeText(getApplicationContext(), latLng.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

//        try {
//            // Loading map
//            initilizeMap();
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onLocationChanged(Location _location) {
        double _lat = _location.getLatitude();
        double _lng = _location.getLongitude();

        LatLng _curntPos = new LatLng(_lat, _lng);

        // object to pass our current location to the map
        MarkerOptions _markerOptions = new MarkerOptions();
        _markerOptions.position(_curntPos);

        // Zooming to our current location with zoom level 17.0f
        googleMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(_curntPos, 17f));

        googleMap.addMarker(_markerOptions);

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

    /**
     * function to load map. If map is not created it will create it for you
     * */
//    private void initilizeMap() {
//        if (googleMap == null) {
//            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
//                    R.id.map)).getMap();
//
//            googleMap.setMyLocationEnabled(true);
//
//            // check if map is created successfully or not
//            if (googleMap == null) {
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }



//    @Override
//    protected void onResume() {
//        super.onResume();
//        initilizeMap();
//    }

}
