package com.routinetracker.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.routinetracker.R;
import com.routinetracker.databinding.ActivityMainBinding;
import com.routinetracker.utils.StaticUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;
    //    private GPSTracker gps;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    void initComponents() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        activityMainBinding.txtShareCurrentLocation.setOnClickListener(this);
        activityMainBinding.txtShowCurrentUserLocations.setOnClickListener(this);
        activityMainBinding.txtStartTracking.setOnClickListener(this);
        activityMainBinding.txtStopTracking.setOnClickListener(this);
        activityMainBinding.txtViewUsers.setOnClickListener(this);
        activityMainBinding.txtViewTodaysLocations.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    private void getLocation() {
        if (checkOrAskLocationPermission()) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    StaticUtils.showToast(this, "Your Location is - \nLatitude: " + latitude + "\nLongitude: " + longitude);
                }
            });
           /* gps = new GPSTracker(this);
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                StaticUtils.showToast(this, "Your Location is - \nLatitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                gps.showSettingsAlert();
            }*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initComponents();
    }

    private boolean checkOrAskLocationPermission() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            StaticUtils.showToast(this, "Please enable Location Services.");
            buildAlertMessageNoGps();
            return false;
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return false;
        } else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
            } else {
                StaticUtils.showToast(this, "Location Permission Denied");
            }
        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS is disabled. Do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtShareCurrentLocation:
                StaticUtils.showToast(this, getString(R.string.module_under_development));
                getLocation();
                break;
            case R.id.txtShowCurrentUserLocations:
                StaticUtils.showToast(this, getString(R.string.module_under_development));
                break;
            case R.id.txtStartTracking:
                StaticUtils.showToast(this, getString(R.string.module_under_development));
                getLocation();
                break;
            case R.id.txtStopTracking:
                StaticUtils.showToast(this, getString(R.string.module_under_development));
                break;
            case R.id.txtViewUsers:
                startActivity(new Intent(MainActivity.this, UsersListActivity.class));
                break;
            case R.id.txtViewTodaysLocations:
                StaticUtils.showToast(this, getString(R.string.module_under_development));
                break;
            default:
                break;
        }
    }
}