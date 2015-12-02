package com.enseirb.gl.burdigalaapp.florian.currentposition;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityTest3 {
//extends FragmentActivity implements OnMapReadyCallback {

    /*private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Acquire a reference to the system Location Manager
	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
		    // Called when a new location is found by the network location provider.

		    double latitude = location.getLatitude();

		    double longitude = location.getLongitude();
		    LatLng latLng = new LatLng(latitude, longitude);

		    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

		    mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {}

		public void onProviderEnabled(String provider) {}

		public void onProviderDisabled(String provider) {}
	    };
	
	// Register the listener with the Location Manager to receive location updates
	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

*/
}
