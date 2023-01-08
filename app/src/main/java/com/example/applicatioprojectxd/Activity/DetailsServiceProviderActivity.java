package com.example.applicatioprojectxd.Activity;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Classes.HomeServiceProvider;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.ActivityDetailsServiceProviderAvtivityBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailsServiceProviderActivity extends AppCompatActivity {

    ActivityDetailsServiceProviderAvtivityBinding binding;
    RequestQueue queue;

    double longitude, latitude;
    HomeServiceProvider homeServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsServiceProviderAvtivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        if (intent != null) {
            homeServiceProvider = (HomeServiceProvider)
                    intent.getSerializableExtra(Constant.SERVICE_PROVIDER_DETAILS);

            binding.tvCalenderOrderDetails.setText(homeServiceProvider.getCreatedAt());

            binding.tvWorkServiceProvider.setText(homeServiceProvider.getNameWork());

            binding.tvProblemOrderDetails.setText(homeServiceProvider.getDetails());

            //خاصه بلخريطه
            longitude = homeServiceProvider.getLongitude();
            latitude = homeServiceProvider.getLatitude();

//            Toast.makeText(this, ""+longitude+"\n"+latitude, Toast.LENGTH_SHORT).show();

            if (homeServiceProvider.getPhoto() != null && !homeServiceProvider.getPhoto().isEmpty()) {
                Picasso.get().load(homeServiceProvider.getPhoto()).fit().centerInside()
                        .placeholder(R.drawable.background_home).
                        into(binding.imageViewOrderDetails);
            }

            binding.tvNameOrderDetails.setText(homeServiceProvider.getNameUser());


        }

        binding.tvLocationOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentMap = new Intent(getBaseContext(), MapActivity.class);
                intentMap.putExtra(Constant.LOCATION_LATITUDE, latitude);
                intentMap.putExtra(Constant.LOCATION_LONGITUDE, longitude);
                startActivity(intentMap);
            }
        });


        binding.imageViewBackServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postCreateOffer();

            }
        });
    }

    private void postCreateOffer() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("order_id", homeServiceProvider.getOrderId());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://studentucas.awamr.com/api/create/Offer";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST,
                url,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(DetailsServiceProviderActivity.this,
                                "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsServiceProviderActivity.this,
                                "" + response.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailsServiceProviderActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiJiNWVjZDJhY2JiMDMyODRkMmUwMzk5YmE0MDdiNGNmMThkMDFhN2IyNTQ2ZDZhY2I1MTNjYjc3NTYwNTdkN2VjOTkyMDI2NTM5OGFhMDNlYyIsImlhdCI6MTY3MDY4MjA2MSwibmJmIjoxNjcwNjgyMDYxLCJleHAiOjE3MDIyMTgwNjEsInN1YiI6IjE2MCIsInNjb3BlcyI6W119.tWlKO2xXOEexeA6Ym_jRZFAPXek0BwD8hzmz6IZP_DuvXgf2GVriGRqycwk0HrMMMYbEuVLL904wsp1YbAf8jFgfVR8VgwKRKlqmjuHJvF5X2nXl2oQbGxKBU1JW7IXGrm7IAtaTWt_eKo1T1Mo1CPmi9hoNpfAJ2bNcqrIAlJoRqUmSv8ukQujfG8OROpTRMvHUGK_uZJRgMY_0A2p7g1vek0eH4rEx0qeXrKWY6oTwvPflpMF9MeIylg5_4wQRGBeMx9HSYzmnnbKyCg5JGrj_xPYlIp72DcfCilxkIqAqSCZLJd3lsP4KJe_7I-dv_VezWGgMovxgzmUTjOsijkeXnpqEQOmWl74_jlWlbqJt0z0vz_wzHLYvhMC48f6Q2Idv20uCEUKqMUm2MMt-eeB2YUBHOCU4J4Ai34-k8GKukua8Jp9QwBN_aMnY22Qrnt7SqxyJcg-lW6S74xk7hkxOiepFbV0azvrzli_kr7XnQqch2EVF22NT89gEbc1BEDILo31AHZGktLjOdpOzWwW4fo-tENNcniKdsOYTCgUP1j-Inq09qvXkF_7vLgabPuhYkc3vUzfve-aZdmx4cIzydlhgKKIV1CSfTVJ_7QBjN8NPcHurFTzUI9FsqwvBOKNsHxAUGHlPL8EfVFtXTrJ_PqvBiCThCm3cVaOO6eo");

                return map;
            }
        };

        queue.add(jsonObjectRequest);


    }


}