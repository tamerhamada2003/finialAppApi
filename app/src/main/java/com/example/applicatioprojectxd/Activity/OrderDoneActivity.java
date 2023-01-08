package com.example.applicatioprojectxd.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.MulitPart.DataPart;
import com.example.applicatioprojectxd.MulitPart.VolleyMultipartRequest;
import com.example.applicatioprojectxd.Snackbar.SnackBar;
import com.example.applicatioprojectxd.databinding.ActivityOrderDoneBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OrderDoneActivity extends AppCompatActivity {

    ActivityOrderDoneBinding binding;

    RequestQueue queue;


    JsonObjectRequest jsonObjectRequest;

    SharedPreferences preferences;
    DialogFragment dialogFragment;

    String detailsLocation, phoneLocation, textProblem, convertIdToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);


        getAllDataLocationAndProblem();


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                    Uri.parse(preferences.getString(Constant.IMAGE_PROBLEM, null)));

            uploadBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        binding.btnGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), AllWorkActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //داله لاستقبال جميع احتياجات انشاء الطلب
    private void getAllDataLocationAndProblem() {
        Intent intent = getIntent();
        if (intent != null) {
            detailsLocation = intent.getStringExtra(Constant.DETAILS_LOCATION);
            phoneLocation = intent.getStringExtra(Constant.PHONE_LOCATION);
            textProblem = intent.getStringExtra(Constant.TEXT_PROBLEM_Location);
            int idAllWork = intent.getIntExtra(Constant.ID_ALL_WORK_LOCATION, -1);
            convertIdToString = String.valueOf(idAllWork);


        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        showDialog();

        String url = "https://studentucas.awamr.com/api/create/order";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        stopDialog();
                        try {
                            JSONObject JSONObject = new JSONObject(new String(response.data));

                            if (JSONObject.getBoolean("success")) {


                                Toast.makeText(OrderDoneActivity.this, "aaaaaaaaaaaaaaaa" +
                                        "", Toast.LENGTH_SHORT).show();
                                SnackBar.snackBar(binding.tvDone,
                                        JSONObject.getString("message"));
                            } else {
                                SnackBar.snackBar(binding.tvDone,
                                        JSONObject.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        stopDialog();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError", "" + error.getMessage());
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("work_id", convertIdToString);
                map.put("details", textProblem);
                map.put("details_address", detailsLocation);
                map.put("phone", phoneLocation);
                map.put("lat", "22222");
                map.put("long", "222");
                return map;

            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imageName = System.currentTimeMillis();

                //send the bitmap here
                params.put("photos[]", new DataPart(imageName +
                        ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Nzc2N2JmYS1mZWVhLTQ0MTEtOWE0My0wYTliNzE4Y2YwZmEiLCJqdGkiOiIyZjQ1ZTNmMmIzZDQxYjUwNWZlNDE1MjgzNzI1ZjY5MTk5ODM2OTBjODNkMDZiYjdmZmNiN2M3OWYyZGM5OTBjNjIyZmU2YTJkMDljZmE0ZSIsImlhdCI6MTY3MTMxMjU4NSwibmJmIjoxNjcxMzEyNTg1LCJleHAiOjE3MDI4NDg1ODUsInN1YiI6IjU1Iiwic2NvcGVzIjpbXX0.kR_ja-WDY7kllQtFi8MPrE1gPypTJVtAYzEn842GHAMM7Abi7tCQAVRCJT5AW7JPUeXfssKnVNAACKDcmqiTTBdq944qhfZnHOD58IUhN5hWotpxPNzvPjF_U963d9faBIupxql06g4SPMhd-FwBYlEcbhYBk-gZthgvaOYJAp9fkEOxKOO3FILyISR888yPErPRuxzJ7sfLRgIQrVuiNZIC1oJqGAloPUzs_9156j7yYQCJn8xNua7y5VLizApEEeEhOO_GWiN9LSTdB_OchfDC7Nw8dwHKsf33NNqKwZy0NNUO5mPD8WmJx_RSGY4GTT0z1K1U19Ew6xB5nKoaZgQEnUq1HiqL5CyJDx5DHG8golSlE-LLCzijBMTZYeOJkrxBLzHwAMvvsgyS3rvHMybKB--6-2N7Dqgij-Msd5PA6JiLc0dDqW7cQd7EUHPzUocUOmJDf_-_waTHWzDmbk8C_sjwFi9KtycFValFV3RUqcO4yj7_5-3HtXDY3i2hznJqXXB_ANgK0AqfHRO5ukg5gmHi2wOEh-f3BdnqRmh523G8zonaJXPSo3ONo7zZm73Zobgbm4Gc6RK_Gp5tnZNhy98-1OSaxfXfDVXagz_oWgxdqOiQT7Fx3fre08Xf0SIR2x8rhQ8bqXTSv-c8tt_clp8-Ycpx-udS02we4_g");

                return map;

            }

            ;
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    private void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getSupportFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();

    }


}
