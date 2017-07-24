package com.example.neelb.sampleapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GPSActivity extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    Criteria criteria;
    String bestProvider;
    TextView andoverDist;
    TextView lexingtonDist;
    TextView actonDist;
    TextView yourCords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        andoverDist = (TextView) findViewById(R.id.andoverDist);
        lexingtonDist = (TextView) findViewById(R.id.lexidist);
        actonDist = (TextView) findViewById(R.id.actonDist);
        yourCords = (TextView) findViewById(R.id.textView14);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return  ;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lat = lastKnownLocation.getLatitude();
            double log = lastKnownLocation.getLongitude();
            System.out.println("User Cords are at: " + lat + " and " + log);
            yourCords.setText("Your cordinates are: "+lat+" and "+log);
            actonDist.setText("Acton: "+dist(lat,log,42.4851,-71.4328));
            andoverDist.setText("Andover: "+dist(lat,log,42.6583,-71.1368));
            lexingtonDist.setText("Acton: "+dist(lat,log,42.4430,-71.2290));
        }
        else{
            System.out.println("Startup locationManage Giving the Null!");
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
            locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);

        }
        }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public static double dist(double x1,double y1, double x2,double y2){
        return Math.sqrt((Math.abs(x1-x2)*Math.abs(x1-x2)) +( Math.abs(y1-y2)* Math.abs(y1-y2)));
    }
    @Override
    public void onLocationChanged(Location location) {
        //Hey, a non null location! Sweet!
        double lat = location.getLatitude();
        double log = location.getLongitude();
        System.out.println("User Cords are at: "+lat+" and "+log);
        yourCords.setText("Your cordinates are: "+lat+" and "+log);
        actonDist.setText("Acton: "+dist(lat,log,42.4851,-71.4328));
        andoverDist.setText("Andover: "+dist(lat,log,42.6583,-71.1368));
        lexingtonDist.setText("Acton: "+dist(lat,log,42.4430,-71.2290));
        //remove location callback:
        locationManager.removeUpdates(this);
    }

    }

