package com.example.applicatioprojectxd.Activity;

import static com.android.volley.Request.Method.POST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.ViewPagerAdapter;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.Fragment.FragmentRegister;

import com.example.applicatioprojectxd.Classes.Tab;
import com.example.applicatioprojectxd.databinding.ActivityRegisterBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements FragmentRegister.onClickInterFace {

    ActivityRegisterBinding binding;
    ArrayList<Tab> arrayListTab;

    DialogFragment dialogFragment;
    RequestQueue queue;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JsonObjectRequest jsonObjectRequest;


    JSONObject jsonObjectToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();
        getSupportActionBar().hide();


        queue = Volley.newRequestQueue(this);
        arrayListTab = new ArrayList<>();


        Tab tabServiceProvider = new Tab("Service provider", 1);
        Tab tabCustomer = new Tab("Customer", 2);


        arrayListTab.add(tabServiceProvider);
        arrayListTab.add(tabCustomer);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(FragmentRegister.newInstance(tabServiceProvider.getName()));
        fragmentArrayList.add(FragmentRegister.newInstance(tabCustomer.getName()));

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragmentArrayList);
        binding.vp2Register.setAdapter(adapter);


        new TabLayoutMediator(binding.tabLayoutRegister, binding.vp2Register, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(arrayListTab.get(position).getName());


            }
        }).attach();


        binding.imageViewBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    // Spinner داله لاخفاء واظهار حقل
    public void onFragmentInterFace(Spinner spinner) {
        binding.tabLayoutRegister.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            // عندما تحدد عليه
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Service provider")) {
//                    Toast.makeText(RegisterActivity.this, "Service provider",
//                            Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.VISIBLE);
                } else {
//                    Toast.makeText(RegisterActivity.this, "Customer", Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.GONE);

                }
            }

            @Override
            // عندما تسيب التحديد عليه

            public void onTabUnselected(TabLayout.Tab tab) {
                //ااعمل كود هان يا تامر

            }

            @Override
            //عندما تعيد اختيارة اكثر من مرة
            public void onTabReselected(TabLayout.Tab tab) {
                //ااعمل كود هان يا تامر
            }
        });

    }

    @Override

    public void onFragmentClick() {
        finish();
    }

    @Override
    //Customer داله لاستقبال بينانات من الفراجمنت خاصه
    public void onClickButtonRegisterCustomer(String fullName, String email, String phone, String password) {

        postRegisterCustomerRequest(fullName, email, phone, password);
//        Toast.makeText(this, "" + fullName + "\n" + email + "\n" + phone + "\n" + password, Toast.LENGTH_SHORT).show();
    }

    // request Customer Post
    private void postRegisterCustomerRequest(String fullName, String email, String phone, String password) {

        showDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            String url = "https://studentucas.awamr.com/api/auth/register/user";

            jsonObject.put("name", fullName);
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObjectRequest = new JsonObjectRequest(POST, url,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //رساله اذا نجح
                    try {
                        if (response.getBoolean("success")) {
//
                            stopDialog();

                            Toast.makeText(RegisterActivity.this, "" +
                                    response.getString("message"), Toast.LENGTH_SHORT).show();


                            jsonObjectToken = response.getJSONObject("data");
                            startActivity(new Intent(getBaseContext(), AllWorkActivity.class));

                            saveTokenCustomer(jsonObjectToken.getString("token"));
                            //shared checkbox


                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    stopDialog();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        queue.add(jsonObjectRequest);
    }


    @Override
    //ServiceProvider داله لاستقبال بينانات من الفراجمنت خاصه
    public void onClickButtonRegisterServiceProvider(String fullName, String email, String phone,
                                                     String password, String spinnerWorkId) {
        postRegisterServiceProviderRequest(fullName, email, phone, password, spinnerWorkId);

        Toast.makeText(this, "ss ??" + spinnerWorkId, Toast.LENGTH_SHORT).show();
    }


    //Request  ServiceProvider register   خاص ب
    private void postRegisterServiceProviderRequest(String fullName, String email, String phone,
                                                    String password, String workIdSpinner) {

        showDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            String url = "https://studentucas.awamr.com/api/auth/register/delivery";

            jsonObject.put("name", fullName);
            jsonObject.put("email", email);
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            jsonObject.put("work_id", workIdSpinner);


            jsonObjectRequest = new JsonObjectRequest(POST, url,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //رساله اذا نجح
                    try {
                        if (response.getBoolean("success")) {
                            stopDialog();
                            Toast.makeText(RegisterActivity.this, "" +
                                    response.getString("message"), Toast.LENGTH_SHORT).show();


                            jsonObjectToken = response.getJSONObject("data");
                            startActivity(new Intent(getBaseContext(), HomeServiceProviderActivity.class));
                            saveTokenProvider(jsonObjectToken.getString("token"));
                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    stopDialog();

                    Toast.makeText(RegisterActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        queue.add(jsonObjectRequest);
    }

    public void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getSupportFragmentManager(), null);
    }


    private void stopDialog() {
        dialogFragment.onStop();
    }


    private void saveTokenProvider(String tokenProvider) {
        editor.putString(Constant.TOKEN_PROVIDER, tokenProvider).commit();
    }


    private void saveTokenCustomer(String tokenCustomer) {
        editor.putString(Constant.TOKEN_CUSTOMER, tokenCustomer).commit();
    }

}