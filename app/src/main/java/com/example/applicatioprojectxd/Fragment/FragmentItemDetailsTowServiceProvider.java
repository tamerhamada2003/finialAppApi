package com.example.applicatioprojectxd.Fragment;

import static com.android.volley.Request.Method.GET;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.example.applicatioprojectxd.Adapter.AdapterItemDetailsOrder;
import com.example.applicatioprojectxd.Classes.ItemDetails;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentItemDetailsTowBinding;
import com.example.applicatioprojectxd.databinding.FragmentItemDetailsTowServiceProviderBinding;
import com.example.applicatioprojectxd.listener.OnClickItemDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentItemDetailsTowServiceProvider extends Fragment {
    FragmentItemDetailsTowServiceProviderBinding binding;
    private static final String ARG_NAME = "tab_name";
    private String tabName;
    RequestQueue queue;
    ArrayList<ItemDetails> arrayList;

    DialogFragment dialogFragment;

    String createdAt, workName;
    int orderId;

    JSONObject jsonObjectWork;

    ItemDetails itemDetails;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public FragmentItemDetailsTowServiceProvider() {
        // Required empty public constructor
    }


    public static FragmentItemDetailsTowServiceProvider newInstance(String tabName) {
        FragmentItemDetailsTowServiceProvider fragment = new FragmentItemDetailsTowServiceProvider();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, tabName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(getActivity());
        preferences = getActivity().getSharedPreferences(Constant.FILE, Context.MODE_PRIVATE);
        editor = preferences.edit();
        arrayList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailsTowServiceProviderBinding.inflate(getLayoutInflater());


        if (tabName != null) {

            if (tabName.equals("Pending")) {

//                getItemDataPending();

                Toast.makeText(getActivity(), "Pending", Toast.LENGTH_SHORT).show();
            } else if (tabName.equals("Underway")) {
                Toast.makeText(getActivity(), "Underway", Toast.LENGTH_SHORT).show();

                getItemDataUnderway();
            } else if (tabName.equals("Completed")) {

                Toast.makeText(getActivity(), "Completed", Toast.LENGTH_SHORT).show();

                getItemDataCompleted();
            }
        }
        return binding.getRoot();
    }


    //request Underway order service provider
    public void getItemDataUnderway() {
        showDialog();
        String url = "https://studentucas.awamr.com/api/order/un/complete/delivery";


        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i);
                        createdAt = jsonObject1.getString("created_at");
                        orderId = jsonObject1.getInt("id");


                        //خاص بلمهن الصناعيه
                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        workName = jsonObjectWork.getString("name");


                        itemDetails = new ItemDetails(orderId, createdAt, workName);
                        adapterRecyclerItemDetails(itemDetails);


                    }
                } catch (JSONException e) {

                    Log.d("error", "onResponse: " + e);

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onResponse: " + error.getMessage());

                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                stopDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiIxMmYyOGMzZWJjYTE2YWM1OWE4YTAxYTg2NmQyNGZmM2M4MmExMjAzZmY4MTM0ZDJjNGVlMDRiOTM3Mzk1MDQ1NTc5MDgyMzQ4NjZmYmRkYyIsImlhdCI6MTY3MTc0MDQzNSwibmJmIjoxNjcxNzQwNDM1LCJleHAiOjE3MDMyNzY0MzUsInN1YiI6IjE2MCIsInNjb3BlcyI6W119.WFJ4Zxm7lyfLLTN8Fk6kwm6u0VU1y1QI7vXHgB23zZulye3TjBJjM1LWaFjtZljGz1edvLHIW_QgVS6iUrc1d7qb4Z8f276u4HXfkc2Pj84J7WQmAfoJNVkyxnD4O6PW8v8U5L3IG3j_UIlj15pF1NSFy-NnXWAsmPz4JiMiAX6E6sHFPjYkcmTAtCQd01BWmezk2UgTfVuDeB8yaIB3Usa-HQ2YFapG2R_L29d0l6BNaDB3kol4cWYD5cLqJZPokA2jHaS5V6lVyRw-ocaMgspnHwrgtjJtLbbrHmRW6fZRrDj7kGxXR04VUpM7p7HRUIHaI0gszu4c21sGKVCDbyk5qJUlvQJ-zLenrz2ry7GB1jFxX9NzWP_qLlWcRzwveqCtmWeFrJoae87745NlEdRQX6OeAAw3mBrxJO9lflQvAn_kLt7dUAVGUE0zjDVIXW1CZ9Y0Nux2FZGaCxUVjq7lKBlfsMOob_mHVNGkNERwA58F5lvpsbIJEWcGks0gcIiC2oTjeIkUcT4Hew9PvC9KP4u3qI-_hXhEEnjmq5QECFvvD9H0XZl6CDngbjNTOPPPi8Ma6TLo_MqMvUSLvwFhdZhlBbXusFAy5r4I1q86nl-OyVd3_fzSa_bmO4E_3mPyXapgv92IHNoLil7ZpZwWRMR8KYIJdDin_5H5FAM");

                return map;
            }
        };
        queue.add(stringRequest);
    }


    //request Completed order service provider
    public void getItemDataCompleted() {
        showDialog();
        String url = "https://studentucas.awamr.com/api/order/complete/delivery";


        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i);
                        createdAt = jsonObject1.getString("created_at");
                        orderId = jsonObject1.getInt("id");


                        //خاص بلمهن الصناعيه
                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        workName = jsonObjectWork.getString("name");


                        itemDetails = new ItemDetails(orderId, createdAt, workName);
                        adapterRecyclerItemDetails(itemDetails);


                    }
                } catch (JSONException e) {

                    Log.d("error", "onResponse: " + e);

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onResponse: " + error.getMessage());

                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                stopDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer " + preferences.getString(Constant.TOKEN_PROVIDER, null));

                return map;
            }
        };
        queue.add(stringRequest);
    }


    public void adapterRecyclerItemDetails(ItemDetails itemDetails) {

        arrayList.add(itemDetails);
        AdapterItemDetailsOrder adapter = new AdapterItemDetailsOrder(arrayList);
        binding.RecyclerViewItemDetailsServiceProvider.setAdapter(adapter);
        binding.RecyclerViewItemDetailsServiceProvider.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.RecyclerViewItemDetailsServiceProvider.setHasFixedSize(true);
    }


    private void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getChildFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();
    }
}