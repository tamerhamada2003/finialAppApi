package com.example.applicatioprojectxd.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    public static final String ARG_LONGITUDE = "longitude";
    public static final String ARG_LATITUDE = "latitude";


    private double longitude, latitude;

    public static MapsFragment newInstance(double latitude, double longitude) {
        MapsFragment mapsFragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        mapsFragment.setArguments(args);
        return mapsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            longitude = getArguments().getDouble(ARG_LONGITUDE);
            latitude = getArguments().getDouble(ARG_LATITUDE);
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Add a marker in Sydney and move the camera
            LatLng map = new LatLng(latitude, longitude);


            Toast.makeText(getActivity(), "" + latitude, Toast.LENGTH_SHORT).show();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(map, 15),
                    5000, null);
            MarkerOptions mapMarker = new MarkerOptions();
            mapMarker.position(map);
            mapMarker.title("Service Provider");
            mapMarker.snippet("Welcome");


            googleMap.addMarker(mapMarker);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMapsBinding binding = FragmentMapsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}