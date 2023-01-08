package com.example.applicatioprojectxd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.ActivityLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityLocationBinding binding;

    String textProblem;
    int idALlWork;


    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        getIntentDataProblem();


        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.image_view_map);

        supportMapFragment.getMapAsync(LocationActivity.this);

        binding.btnNextDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.editTextPhoneLocation.getText().toString().isEmpty() &&
                        !binding.editTextPhoneLocation.getText().toString().isEmpty()) {

                    sendDataProblemAndDataLocationAndIdWork();

                } else {
                    Toast.makeText(LocationActivity.this, "Please All Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getBaseContext());
        fetchLastLocation();

    }


    //  والايدي الخاص بلمهنه الصناعيه Problem داله لاستقبال مدخلات اليوز في واجهه ال
    private void getIntentDataProblem() {
        Intent intent = getIntent();
        if (intent != null) {
            textProblem = intent.getStringExtra(Constant.TEXT_PROBLEM);
            idALlWork = intent.getIntExtra(Constant.SEND_ID_ALL_Work, -1);

        }
    }


    //داله لارسال بيانات من واجهه المشكله وبيانات من واحهه الموقع والايدي الخاص بلمهن الصناعيه الى واجهه انشاء طلب
    private void sendDataProblemAndDataLocationAndIdWork() {
        Intent intent = new Intent(getBaseContext(), OrderDoneActivity.class);


        intent.putExtra(Constant.DETAILS_LOCATION, binding.etTextLocation.getText().toString());
        intent.putExtra(Constant.PHONE_LOCATION, binding.editTextPhoneLocation.getText().toString());

        intent.putExtra(Constant.TEXT_PROBLEM_Location, textProblem);

        intent.putExtra(Constant.ID_ALL_WORK_LOCATION, idALlWork);
        startActivity(intent);
        finish();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest
                            .permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                }
            }
        });
    }

    //جلب اخر موقع

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng map = new LatLng(31.530682, 34.48802);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(map, 15), 3000, null);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(map);
        googleMap.addMarker(markerOptions);


        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }


}