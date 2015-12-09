package alraffin.positiontest1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMap();
    }


    private void setUpMap() {

        mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mMap.setMyLocationEnabled(true);

        try {
            LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            double latitude = 0.0;
            double longitude = 0.0;
            // look at the GPS provider first since it is generally more accurate
            if(locationGPS != null) {
                Log.d("GPSLocation", "lat: " + locationGPS.getLatitude() + ", long: " + locationGPS.getLongitude());
                latitude = locationGPS.getLatitude();
                longitude = locationGPS.getLongitude();
            }

            // if the GPS is not available, look at the network provider
            else if (locationNet != null) {
                Log.d("NETLocation", "lat: " + locationNet.getLatitude() + ", long: " + locationNet.getLongitude());
                latitude = locationNet.getLatitude();
                longitude = locationNet.getLongitude();
            }

            Log.d("COUCOU", "lat: " + latitude + " long: " + longitude);

            LatLng latLng = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
        }

        catch (SecurityException e){
            e.printStackTrace();
            Log.d("COUCOU", "IN CATCH");
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        setUpMap();
    }


}
