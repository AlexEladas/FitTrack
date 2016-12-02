package com.example.alexeladas.assignment4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    private double distance;
    private double time;
    private long lastStop ;
    private GoogleMap mMap;
    private boolean trackDistance;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Location mPreLocation;
    Marker mCurrLocationMarker;
    Chronometer mTimer;
    ViewFlipper vf;
    TextView dist;
    TextView pacetext;
    Button Resume;
    Button Stop;
    Button Save;
    public static String STARTFOREGROUND_ACTION = "com.truiton.foregroundservice.action.startforeground";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merged_view);
         mTimer = (Chronometer) findViewById(R.id.timer);
        vf= (ViewFlipper) findViewById(R.id.ViewFlipper);
        dist = (TextView) findViewById(R.id.distanceText);
        pacetext = (TextView) findViewById(R.id.paceText);
        Resume = (Button) findViewById(R.id.resumeButton);
        Stop = (Button) findViewById(R.id.stopButton);
        Save = (Button) findViewById(R.id.saveButton);
        MakeInvisible();
        lastStop = 0;
       // mTimer.setTextSize(20);
       // mTimer.setFormat("00:00:00");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    protected void onStop(){

        vf.showPrevious();
        super.onStop();


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(0);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    public void Start(View v){
      //  setContentView(R.layout.running_view);
        vf.showNext();
        mTimer.setTextSize(50);
        distance = 0;
        trackDistance = true;
        Log.d("Resume","hello");
        mTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chrono) {// Make the timer display hh:mm:ss

                time = SystemClock.elapsedRealtime()-chrono.getBase();

                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                chrono.setText(hh+":"+mm+":"+ss);
            }
        });
        if(lastStop==0){
            Log.d("sup","hello");
        mTimer.setBase(SystemClock.elapsedRealtime());}

        mTimer.start();
        Intent startIntent = new Intent(MapsActivity.this, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);

    }

    public void stop(View v){
        //  setContentView(R.layout.running_view);

        lastStop =   SystemClock.elapsedRealtime()-mTimer.getBase() ;
        mTimer.stop();

        trackDistance = false;
        Stop.setVisibility(View.INVISIBLE);
        MakeVisible();

    }

    public void resume(View v){
        //  setContentView(R.layout.running_view);
        MakeInvisible();
        Stop.setVisibility(View.VISIBLE);


            mTimer.setBase(  SystemClock.elapsedRealtime() - lastStop);


        mTimer.start();
        trackDistance = true;

    }

    public void save(View v){
        //  setContentView(R.layout.running_view);
        SharedPreferences sharedPreferences = getSharedPreferences("Runs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Distance",String.valueOf(distance));
        editor.putString("Time",String.valueOf(time));
        editor.commit();
        DBHandler dbHandler = new DBHandler(this);

        Date date = new Date();
        long timestamp = date.getTime();
        Date otherDate = new Date(timestamp);
        String DateId = String.valueOf(otherDate);
        Log.d("Date",DateId);



        SharedPreferences sharedPreferences2 = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        String weight = sharedPreferences2.getString("Weight",null);


        Run run = new Run(DateId,(double)Math.round(distance*100d)/100d,time,Double.valueOf(weight));
        dbHandler.addRun(run);
        startActivity(new Intent(getApplicationContext(), Data.class));

        Intent stopIntent = new Intent(MapsActivity.this, ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);

    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        mPreLocation = mLastLocation;
        mLastLocation = location;
        if(trackDistance)
        {
            if(mPreLocation != null){
                if((mPreLocation.distanceTo(mLastLocation))<10.5){
                distance += (mPreLocation.distanceTo(mLastLocation))/1000;}
            dist.setText(String.valueOf((double)Math.round(distance*100d)/100d));

            }
            if((double)Math.round(((time/60000)/distance)*100d/100d)>30){
                pacetext.setText("0.0");
            }else
            pacetext.setText(String.valueOf((double)Math.round(((time/60000)/distance)*100d/100d)));

        Log.d("distance",String.valueOf(distance));
            Log.d("pace",String.valueOf(distance/(time/60000)));}
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        //markerOptions.title("Current Position");
      //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        /*if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/

    }

    public void MakeVisible(){// Mkae the BMI and BMR visible

        //  button.setVisibility(View.VISIBLE);
        Resume.setVisibility(View.VISIBLE);
        Save.setVisibility(View.VISIBLE);

    }

    public void MakeInvisible(){ //Make the BMI and BMR invisible
        // button.setVisibility(View.VISIBLE);
        Resume.setVisibility(View.INVISIBLE);
        Save.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
             //return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

}
