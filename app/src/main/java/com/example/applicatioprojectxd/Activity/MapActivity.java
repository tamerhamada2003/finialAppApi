package com.example.applicatioprojectxd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.MapsFragment;
import com.example.applicatioprojectxd.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity {

    ActivityMapBinding binding;
    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        Intent intentMap = getIntent();
        if (intentMap != null) {
            longitude = intentMap.getDoubleExtra(Constant.LOCATION_LONGITUDE, -1);
            latitude = intentMap.getDoubleExtra(Constant.LOCATION_LATITUDE, -1);
        }
        sendDataFragmentMap();
    }



    //اظهر الخريطه
    private void sendDataFragmentMap() {
        MapsFragment mapFragment = MapsFragment.newInstance(latitude, longitude);
        getSupportFragmentManager().beginTransaction().
                replace(binding.fragmentContainerViewMap.getId(), mapFragment).commit();
    }
}