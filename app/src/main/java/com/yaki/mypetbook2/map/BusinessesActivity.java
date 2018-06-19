package com.yaki.mypetbook2.map;

        import android.app.Dialog;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GoogleApiAvailability;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapFragment;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.yaki.mypetbook2.R;

        import java.io.IOException;
        import java.util.List;
        import java.util.Locale;

public class BusinessesActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private CheckBox CB_all;
    private CheckBox CB_vet;
    private CheckBox CB_PetShop;
    private CheckBox CB_DayCare;
    private CheckBox CB_DogWalker;
    private CheckBox CB_HairSalon;
    private Button find;
    private Button go;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServiceAvailable()) {
            setContentView(R.layout.activity_businesses);
            go = (Button) findViewById(R.id.go_btn);

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Log.e("geoLocate", "before function");
                        geoLocate(view);
                        Log.e("geoLocate", "after function");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            initMap();
        } else {
            Toast.makeText(this, "No Google Maps", Toast.LENGTH_LONG).show();
        }

    }

    public void geoLocate(View view) throws IOException {
        Log.e("geoLocate", "in function");
        EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();
        Log.e("location", location + "");

        Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
        Log.e("after gc", "");
        List<Address> list = gc.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        setMarker(new LatLng(lat,lng),1);
        //  goToLocationZoom(lat, lng, 15);



    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);

    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    // checks if the user has google map service
    public boolean googleServiceAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) { // if he has
            return true;
        } else if (api.isUserResolvableError(isAvailable)) { // if he hasn't but it can be fixed
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else { // if he hasn't and can't be fixed
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    /*    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        */

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }



    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location == null){
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);

            setMarker(ll,0);
        }
    }

    public void setMarker(LatLng ll, int flag) {
        Marker marker = null;
        MarkerOptions options = null;

        if(marker != null) {
            marker.remove();
        }

        if(flag == 0) {
            options = new MarkerOptions()
                    .title("my location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .position(ll);
        }
        else {
            options = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    .position(ll);
        }

        marker = mGoogleMap.addMarker(options);
    }
}