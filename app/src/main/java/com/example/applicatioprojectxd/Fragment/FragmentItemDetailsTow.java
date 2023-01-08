package com.example.applicatioprojectxd.Fragment;

import static com.android.volley.Request.Method.GET;

import android.content.Context;
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
import com.example.applicatioprojectxd.Adapter.AdapterItemDetailsOrder;
import com.example.applicatioprojectxd.Classes.ItemDetails;
import com.example.applicatioprojectxd.Snackbar.SnackBar;
import com.example.applicatioprojectxd.databinding.FragmentItemDetailsTowBinding;
import com.example.applicatioprojectxd.listener.OnClickItemDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentItemDetailsTow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentItemDetailsTow extends Fragment {

    private static final String ARG_NAME = "tab_name";


    DialogFragment dialogFragment;
    private String tabName;

    String createdAt, workName, detailsProblem, photo, nameItem;
    int orderId, providerId;

    JSONObject jsonObjectWork;

    FragmentItemDetailsTowBinding binding;
    RequestQueue queue;
    ItemDetails itemDetails;
    ArrayList<ItemDetails> arrayList;
    JSONArray jsonArrayPhoto;
    JSONObject jsonObjectPhoto;
    OnClickSendDataItemDetails onClickSendDataItemDetails;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickSendDataItemDetails) {
//            context :  هي الاكتيفيتي
            onClickSendDataItemDetails = (OnClickSendDataItemDetails) context; //interFace الداله الي موجودة في الاكتيفيتي خزنا في
        }
    }

    @Override //لما تتدمر الفراجمنت خلي interFace فارغ
    public void onDetach() {
        super.onDetach();
        onClickSendDataItemDetails = null;
    }

    public FragmentItemDetailsTow() {
        // Required empty public constructor
    }

    public static FragmentItemDetailsTow newInstance(String tabName) {
        FragmentItemDetailsTow fragment = new FragmentItemDetailsTow();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, tabName);


        fragment.setArguments(args);
        return fragment;
    }


    public static FragmentItemDetailsTow newInstance() {
        FragmentItemDetailsTow fragment = new FragmentItemDetailsTow();
        Bundle args = new Bundle();


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(getActivity());
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


        //الفراجمنت الي فيها التصاميم الخاصه تفاصيل الطلب

        binding = FragmentItemDetailsTowBinding.inflate(inflater, container, false);


        if (tabName != null) {

            if (tabName.equals("Pending")) {

                getItemDataPending();

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


    //request Pending order
    public void getItemDataPending() {

        showDialog();
        String url = "https://studentucas.awamr.com/api/order/un/complete/user";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i);
                        createdAt = jsonObject1.getString("created_at");
                        orderId = jsonObject1.getInt("id");
                        detailsProblem = jsonObject1.getString("details");
                        nameItem = "Pending"; //علشان اعرف من وين جاي
                        providerId = jsonObject1.getInt("delivery_id");


                        //خاص بلمهن الصناعيه
                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        workName = jsonObjectWork.getString("name");

//                        Log.d("details", "createdAt: "+createdAt+"orderId :"+orderId+"serviceType"+workName);

                        itemDetails = new ItemDetails(orderId, createdAt,
                                workName, photo, nameItem, detailsProblem, providerId);
                        adapterRecyclerItemDetails(itemDetails);

                        //خاص بلصور

                        jsonArrayPhoto = jsonObject1.getJSONArray("photo_order");

                        for (int j = 0; j < jsonArrayPhoto.length(); j++) {

                            jsonObjectPhoto = jsonArrayPhoto.getJSONObject(i);

                            photo = jsonObjectPhoto.getString("photo");

                            Log.d("photo", "onResponse: " + photo);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                stopDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiIyZjQ1ZTNmMmIzZDQxYjUwNWZlNDE1MjgzNzI1ZjY5MTk5ODM2OTBjODNkMDZiYjdmZmNiN2M3OWYyZGM5OTBjNjIyZmU2YTJkMDljZmE0ZSIsImlhdCI6MTY3MTMxMjU4NSwibmJmIjoxNjcxMzEyNTg1LCJleHAiOjE3MDI4NDg1ODUsInN1YiI6IjU1Iiwic2NvcGVzIjpbXX0.kR_ja-WDY7kllQtFi8MPrE1gPypTJVtAYzEn842GHAMM7Abi7tCQAVRCJT5AW7JPUeXfssKnVNAACKDcmqiTTBdq944qhfZnHOD58IUhN5hWotpxPNzvPjF_U963d9faBIupxql06g4SPMhd-FwBYlEcbhYBk-gZthgvaOYJAp9fkEOxKOO3FILyISR888yPErPRuxzJ7sfLRgIQrVuiNZIC1oJqGAloPUzs_9156j7yYQCJn8xNua7y5VLizApEEeEhOO_GWiN9LSTdB_OchfDC7Nw8dwHKsf33NNqKwZy0NNUO5mPD8WmJx_RSGY4GTT0z1K1U19Ew6xB5nKoaZgQEnUq1HiqL5CyJDx5DHG8golSlE-LLCzijBMTZYeOJkrxBLzHwAMvvsgyS3rvHMybKB--6-2N7Dqgij-Msd5PA6JiLc0dDqW7cQd7EUHPzUocUOmJDf_-_waTHWzDmbk8C_sjwFi9KtycFValFV3RUqcO4yj7_5-3HtXDY3i2hznJqXXB_ANgK0AqfHRO5ukg5gmHi2wOEh-f3BdnqRmh523G8zonaJXPSo3ONo7zZm73Zobgbm4Gc6RK_Gp5tnZNhy98-1OSaxfXfDVXagz_oWgxdqOiQT7Fx3fre08Xf0SIR2x8rhQ8bqXTSv-c8tt_clp8-Ycpx-udS02we4_g");

                return map;
            }
        };
        queue.add(stringRequest);
    }


    //request Underway order
    public void getItemDataUnderway() {
        showDialog();
        String url = "https://studentucas.awamr.com/api/order/pending/user";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i);
                        createdAt = jsonObject1.getString("created_at");
                        orderId = jsonObject1.getInt("id");
                        detailsProblem = jsonObject1.getString("details");
                        nameItem = "Underway"; //علشان اعرف من وين جاي
                        providerId = jsonObject1.getInt("delivery_id");


                        //خاص بلمهن الصناعيه
                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        workName = jsonObjectWork.getString("name");

//                        Log.d("details", "createdAt: "+createdAt+"orderId :"+orderId+"serviceType"+workName);

                        itemDetails = new ItemDetails(orderId, createdAt,
                                workName, photo, nameItem, detailsProblem, providerId);
                        adapterRecyclerItemDetails(itemDetails);

                        //خاص بلصور

                        jsonArrayPhoto = jsonObject1.getJSONArray("photo_order");

                        for (int j = 0; j < jsonArrayPhoto.length(); j++) {

                            jsonObjectPhoto = jsonArrayPhoto.getJSONObject(i);

                            photo = jsonObjectPhoto.getString("photo");

                            Log.d("photo", "onResponse: " + photo);

                        }


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

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiIyZjQ1ZTNmMmIzZDQxYjUwNWZlNDE1MjgzNzI1ZjY5MTk5ODM2OTBjODNkMDZiYjdmZmNiN2M3OWYyZGM5OTBjNjIyZmU2YTJkMDljZmE0ZSIsImlhdCI6MTY3MTMxMjU4NSwibmJmIjoxNjcxMzEyNTg1LCJleHAiOjE3MDI4NDg1ODUsInN1YiI6IjU1Iiwic2NvcGVzIjpbXX0.kR_ja-WDY7kllQtFi8MPrE1gPypTJVtAYzEn842GHAMM7Abi7tCQAVRCJT5AW7JPUeXfssKnVNAACKDcmqiTTBdq944qhfZnHOD58IUhN5hWotpxPNzvPjF_U963d9faBIupxql06g4SPMhd-FwBYlEcbhYBk-gZthgvaOYJAp9fkEOxKOO3FILyISR888yPErPRuxzJ7sfLRgIQrVuiNZIC1oJqGAloPUzs_9156j7yYQCJn8xNua7y5VLizApEEeEhOO_GWiN9LSTdB_OchfDC7Nw8dwHKsf33NNqKwZy0NNUO5mPD8WmJx_RSGY4GTT0z1K1U19Ew6xB5nKoaZgQEnUq1HiqL5CyJDx5DHG8golSlE-LLCzijBMTZYeOJkrxBLzHwAMvvsgyS3rvHMybKB--6-2N7Dqgij-Msd5PA6JiLc0dDqW7cQd7EUHPzUocUOmJDf_-_waTHWzDmbk8C_sjwFi9KtycFValFV3RUqcO4yj7_5-3HtXDY3i2hznJqXXB_ANgK0AqfHRO5ukg5gmHi2wOEh-f3BdnqRmh523G8zonaJXPSo3ONo7zZm73Zobgbm4Gc6RK_Gp5tnZNhy98-1OSaxfXfDVXagz_oWgxdqOiQT7Fx3fre08Xf0SIR2x8rhQ8bqXTSv-c8tt_clp8-Ycpx-udS02we4_g");

                return map;
            }
        };
        queue.add(stringRequest);

    }


    //request Completed order
    public void getItemDataCompleted() {

        showDialog();
        String url = "https://studentucas.awamr.com/api/order/complete/user";
        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i);
                        createdAt = jsonObject1.getString("created_at");
                        orderId = jsonObject1.getInt("id");
                        detailsProblem = jsonObject1.getString("details");
                        nameItem = "Completed"; //علشان اعرف من وين جاي
                        providerId = jsonObject1.getInt("delivery_id");


                        //خاص بلمهن الصناعيه
                        jsonObjectWork = jsonObject1.getJSONObject("work");
                        workName = jsonObjectWork.getString("name");

//                        Log.d("details", "createdAt: "+createdAt+"orderId :"+orderId+"serviceType"+workName);

                        itemDetails = new ItemDetails(orderId, createdAt,
                                workName, photo, nameItem, detailsProblem, providerId);
                        adapterRecyclerItemDetails(itemDetails);

                        //خاص بلصور

                        jsonArrayPhoto = jsonObject1.getJSONArray("photo_order");

                        for (int j = 0; j < jsonArrayPhoto.length(); j++) {

                            jsonObjectPhoto = jsonArrayPhoto.getJSONObject(i);
                            photo = jsonObjectPhoto.getString("photo");
                            Log.d("photo", "onResponse: " + photo);

                        }


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

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiIyZjQ1ZTNmMmIzZDQxYjUwNWZlNDE1MjgzNzI1ZjY5MTk5ODM2OTBjODNkMDZiYjdmZmNiN2M3OWYyZGM5OTBjNjIyZmU2YTJkMDljZmE0ZSIsImlhdCI6MTY3MTMxMjU4NSwibmJmIjoxNjcxMzEyNTg1LCJleHAiOjE3MDI4NDg1ODUsInN1YiI6IjU1Iiwic2NvcGVzIjpbXX0.kR_ja-WDY7kllQtFi8MPrE1gPypTJVtAYzEn842GHAMM7Abi7tCQAVRCJT5AW7JPUeXfssKnVNAACKDcmqiTTBdq944qhfZnHOD58IUhN5hWotpxPNzvPjF_U963d9faBIupxql06g4SPMhd-FwBYlEcbhYBk-gZthgvaOYJAp9fkEOxKOO3FILyISR888yPErPRuxzJ7sfLRgIQrVuiNZIC1oJqGAloPUzs_9156j7yYQCJn8xNua7y5VLizApEEeEhOO_GWiN9LSTdB_OchfDC7Nw8dwHKsf33NNqKwZy0NNUO5mPD8WmJx_RSGY4GTT0z1K1U19Ew6xB5nKoaZgQEnUq1HiqL5CyJDx5DHG8golSlE-LLCzijBMTZYeOJkrxBLzHwAMvvsgyS3rvHMybKB--6-2N7Dqgij-Msd5PA6JiLc0dDqW7cQd7EUHPzUocUOmJDf_-_waTHWzDmbk8C_sjwFi9KtycFValFV3RUqcO4yj7_5-3HtXDY3i2hznJqXXB_ANgK0AqfHRO5ukg5gmHi2wOEh-f3BdnqRmh523G8zonaJXPSo3ONo7zZm73Zobgbm4Gc6RK_Gp5tnZNhy98-1OSaxfXfDVXagz_oWgxdqOiQT7Fx3fre08Xf0SIR2x8rhQ8bqXTSv-c8tt_clp8-Ycpx-udS02we4_g");

                return map;
            }
        };
        queue.add(stringRequest);

    }


    public void adapterRecyclerItemDetails(ItemDetails itemDetails) {

        arrayList.add(itemDetails);


        AdapterItemDetailsOrder adapter = new AdapterItemDetailsOrder(arrayList, new OnClickItemDetails() {
            @Override
            //    عند الضغط على ايتم معين
            //            //ابعت معاه رقم الطلب والصورة ومن وين جاي

            public void onClickItemDetails(int position) {
                ItemDetails itemDetails = arrayList.get(position);
                if (tabName.equals("Pending")) {

                    onClickSendDataItemDetails.clickIntent(itemDetails);


                } else if (tabName.equals("Underway")) {

                    onClickSendDataItemDetails.clickIntent(itemDetails);


                } else if (tabName.equals("Completed")) {

                    SnackBar.snackBar(binding.tv,"تم اكتمال الطلب");

                }
            }
        });
        binding.RecyclerViewItemDetails.setAdapter(adapter);
        binding.RecyclerViewItemDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.RecyclerViewItemDetails.setHasFixedSize(true);


    }


    public interface OnClickSendDataItemDetails {

        void clickIntent(ItemDetails itemDetails);
    }


    private void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getChildFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();
    }


}