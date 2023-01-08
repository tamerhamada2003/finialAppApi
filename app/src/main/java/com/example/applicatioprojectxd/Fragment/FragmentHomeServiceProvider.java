package com.example.applicatioprojectxd.Fragment;

import static com.android.volley.Request.Method.POST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Activity.DetailsServiceProviderActivity;
import com.example.applicatioprojectxd.Activity.HomeServiceProviderActivity;
import com.example.applicatioprojectxd.Adapter.AdapterHomeProvider;
import com.example.applicatioprojectxd.Classes.HomeServiceProvider;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentHomeServiceProviderBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentHomeServiceProvider extends Fragment {


    private RequestQueue queue;

    FragmentHomeServiceProviderBinding binding;
    private String detailsAddress, createdAt, nameWork, longitude, latitude, photoOrderHome, nameUser;
    private int idWork, orderId, userId;

    private JSONObject jsonObjectWork, jsonObjectPhoto, jsonObjectUser;


    private HomeServiceProvider homeServiceProvider;

    private ArrayList<HomeServiceProvider> homeServiceProviderArrayList;


    private AdapterHomeProvider adapter;


    private DialogFragment dialogFragment;

    OnClickHomeServiceProvider onClickHomeServiceProvider;

    SharedPreferences preferences;

    double convertLatitude, convertLongitude;

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof OnClickHomeServiceProvider) {
            onClickHomeServiceProvider = (OnClickHomeServiceProvider) context;
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickHomeServiceProvider = null;
    }


    public FragmentHomeServiceProvider() {
        // Required empty public constructor
    }


    public static FragmentHomeServiceProvider newInstance() {
        FragmentHomeServiceProvider fragment = new FragmentHomeServiceProvider();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences(Constant.FILE, Context.MODE_PRIVATE);
        homeServiceProviderArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeServiceProviderBinding.inflate(getLayoutInflater());

        getAllDataHomeServiceProvider();

        return binding.getRoot();
    }

    //request home service provider
    private void getAllDataHomeServiceProvider() {

        showDialog();

        String url = "https://studentucas.awamr.com/api/home/deliver";

        StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    //response convert JSONObject
                    JSONObject jsonObject = new JSONObject(response); //JSONObject {}  << response لأنو أول بداية ال

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i); //JSONObject بتجيب كل البيانات التي بداخل
                        detailsAddress = jsonObject1.getString("details_address");
                        createdAt = jsonObject1.getString("created_at");
                        userId = jsonObject1.getInt("user_id");


                        latitude = jsonObject1.getString("lat");
                        longitude = jsonObject1.getString("long");


                        try {
                            convertLatitude = Double.parseDouble(latitude);
                            convertLongitude = Double.parseDouble(longitude);
                            Log.d("lat", "onResponse: " + convertLatitude);
                            Log.d("lat", "onResponse: " + convertLongitude);

                        } catch (Exception e) {
                        }
//

                        //work

                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        idWork = jsonObjectWork.getInt("id");
                        nameWork = jsonObjectWork.getString("name");


                        //photo
                        jsonObjectPhoto = jsonObject1.getJSONObject("photo_order_home");
                        orderId = jsonObjectPhoto.getInt("order_id");
                        photoOrderHome = jsonObjectPhoto.getString("photo");


                        //user
                        jsonObjectUser = jsonObject1.getJSONObject("user");

                        nameUser = jsonObjectUser.getString("name");


                        homeServiceProvider = new
                                HomeServiceProvider(nameWork, idWork,
                                photoOrderHome, detailsAddress, createdAt, orderId, nameUser,
                                convertLongitude, convertLatitude);


                        adapterHomeServiceProvider(homeServiceProvider);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {

                stopDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + preferences.getString(Constant.TOKEN_PROVIDER, null));

                Log.d("Authorization", "getHeaders: "+preferences.getString(Constant.TOKEN_PROVIDER,null));
                return map;
            }
        };
        queue.add(stringRequest);
    }

    public void adapterHomeServiceProvider(HomeServiceProvider homeServiceProvider) {

        homeServiceProviderArrayList.add(homeServiceProvider);

        adapter = new AdapterHomeProvider(homeServiceProviderArrayList, new AdapterHomeProvider.OnClickListenerHomeProvider() {
            @Override
            public void onClickHome(int position) {

                // تم تخزين الداتا في اوبجكت
                HomeServiceProvider homeServiceProvider = homeServiceProviderArrayList.get(position);
                onClickHomeServiceProvider.onClickPosition(homeServiceProvider);


            }
        });

        binding.RecyclerViewHome.setAdapter(adapter);
        binding.RecyclerViewHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.RecyclerViewHome.setHasFixedSize(true);
    }


    public void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getChildFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();
    }

    public interface OnClickHomeServiceProvider {
        void onClickPosition(HomeServiceProvider homeServiceProvider);
    }


}