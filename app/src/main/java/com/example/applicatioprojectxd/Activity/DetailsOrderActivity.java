package com.example.applicatioprojectxd.Activity;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.AdapterDetailsOrderProvider;
import com.example.applicatioprojectxd.Classes.DataProvider;
import com.example.applicatioprojectxd.Classes.ItemDetails;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.ActivityDetailsOrderBinding;
import com.example.applicatioprojectxd.listener.OnClickRadio;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsOrderActivity extends AppCompatActivity {

    ActivityDetailsOrderBinding binding;

    RequestQueue queue;


    private int orderId, providerId;
    private String itemName, photo, workName, detailsProblem, createdAt;


    JsonObjectRequest jsonObjectRequest;

    //provider
    String nameProvider, imageProvider;
    DataProvider dataProvider;
    ArrayList<DataProvider> dataProviderArrayList;

    DialogFragment dialogFragment;
    ItemDetails itemDetails;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        queue = Volley.newRequestQueue(this);
        dataProviderArrayList = new ArrayList<>();
        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();


        Intent intent = getIntent();
        if (intent != null) {

            itemDetails = (ItemDetails) intent.getSerializableExtra(Constant.Serializable);


            if (itemDetails.getNameItem().equals("Pending")) {
                binding.tvTypeDetailsOrder.setText("Pending");
                binding.btnDetailsOrderProvider.setText("Confirm selection");
                dataItemDetails();

                fillDataDetailsOrder();


            } else if (itemDetails.getNameItem().equals("Underway")) {
                binding.tvTypeDetailsOrder.setText("Underway");
                binding.btnDetailsOrderProvider.setText("Close Order");
                Toast.makeText(this, "tamer hamada", Toast.LENGTH_SHORT).show();

                dataItemDetails();
                fillDataDetailsOrder();


            } else if (itemDetails.getNameItem().equals("Completed")) {

                binding.tvTypeDetailsOrder.setText("Completed");
                dataItemDetails();
                fillDataDetailsOrder();
            }
        }


    }

    //request AllOffer
    //الخاص بعرض المتقدمين على الطلب من صاحب المهنه
    private void getDataAllOfferByIdOrder() {


        showDialog();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("order_id", orderId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(POST,
                "https://studentucas.awamr.com/api/get/all/offer",
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                stopDialog();
                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(DetailsOrderActivity.this,
                                "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONArray jsonArrayData = response.getJSONArray("data");

                        for (int i = 0; i < jsonArrayData.length(); i++) {

                            JSONObject jsonObject = jsonArrayData.getJSONObject(i);
                            nameProvider = jsonObject.getString("name");

                            imageProvider = jsonObject.getString("photo");

                            dataProvider = new DataProvider(nameProvider, imageProvider);
//                            Log.d("email", "onResponse: " + nameProvider);
                            adapterDetailsOrderProvider(dataProvider);
                        }
                    } else {
                        Toast.makeText(DetailsOrderActivity.this,
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
                stopDialog();
                Toast.makeText(DetailsOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer " + preferences.getString(Constant.TOKEN_CUSTOMER, null));

                return map;
            }
        };
        queue.add(jsonObjectRequest);
    }


    //  جلبنا البينانات  عند الضغط على القائمه الي فيها الطلبات
    private void dataItemDetails() {
        orderId = itemDetails.getOrderId();
        photo = itemDetails.getPhoto();
        itemName = itemDetails.getNameItem();
        createdAt = itemDetails.getUnderWayCreatedAt();
        workName = itemDetails.getNameWork();
        detailsProblem = itemDetails.getDetailsProblem();
        providerId = itemDetails.getProviderId();

        getDataAllOfferByIdOrder();

    }


    //تعبثه البيانات في التصميم
    private void fillDataDetailsOrder() {
        binding.tvServiceTypeOrderDetails.setText("Craftsman" + workName);

        binding.tvCreatedAtOrderDetails.setText(createdAt);
        binding.tvDetailsProblemDetailsOrder.setText(detailsProblem);

        binding.tvOrderIdDetailsOrder.setText("Order #" + orderId);

        if (photo != null && !photo.isEmpty()) {
            Picasso.get().load(photo).fit().centerInside()
                    .placeholder(R.drawable.tamer1).
                    into(binding.imageViewOrderDetails);

        }

    }


    //adapterDetailsOrderProvider
    private void adapterDetailsOrderProvider(DataProvider dataProvider) {

        dataProviderArrayList.add(dataProvider);

        AdapterDetailsOrderProvider adapter = new AdapterDetailsOrderProvider(dataProviderArrayList, new OnClickRadio() {
            @Override
            public void onClickRadioButton(boolean state) {
                // وضغط على الزر رح يقوم بتحويل من قيد التنفيذ الى مكتمل radio اذا صغط على
                if (state) {

                    if (itemDetails.getNameItem().equals("Pending")) {
                        binding.btnDetailsOrderProvider.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                postAcceptOffer();  //عند الضغط على الزر تحويل الطلب من معلق الى قيد التنفيذ
                            }
                        });


                        //No request Close Order

                    }
                }


            }
        });


        binding.RecyclerViewProviderDetailsOrder.setAdapter(adapter);
        binding.RecyclerViewProviderDetailsOrder.setLayoutManager(new LinearLayoutManager(this));
        binding.RecyclerViewProviderDetailsOrder.setHasFixedSize(true);


    }


    //عند الضغط على الزر تحويل الطلب من معلق الى قيد التنفيذ
    private void postAcceptOffer() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("delivery_id", providerId);
            jsonObject.put("id", orderId);

            Toast.makeText(this, "" + providerId + "\n" + orderId, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "";
        jsonObjectRequest = new JsonObjectRequest(POST,
                "https://studentucas.awamr.com/api/accept/offer",
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {
                        Toast.makeText(DetailsOrderActivity.this,
                                "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailsOrderActivity.this,
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
                Toast.makeText(DetailsOrderActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer " + preferences.getString(Constant.TOKEN_CUSTOMER, null));

                return map;
            }
        };
        queue.add(jsonObjectRequest);


    }

    private void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getSupportFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();

    }


}