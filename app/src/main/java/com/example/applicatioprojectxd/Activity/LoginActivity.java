package com.example.applicatioprojectxd.Activity;

import static android.provider.Settings.ACTION_WIFI_ADD_NETWORKS;
import static android.provider.Settings.ACTION_WIFI_SETTINGS;
import static com.android.volley.Request.Method.POST;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.ViewPagerAdapter;
import com.example.applicatioprojectxd.Classes.CheckConnectivity;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.Fragment.DialogFragmentInternet;
import com.example.applicatioprojectxd.Fragment.FragmentLogin;
import com.example.applicatioprojectxd.Classes.Tab;
import com.example.applicatioprojectxd.Snackbar.SnackBar;
import com.example.applicatioprojectxd.databinding.ActivityLoginBinding;
import com.example.applicatioprojectxd.listener.OnClickRateProvider;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity implements FragmentLogin.OnClickListener, DialogFragmentInternet.OnClickConnection {

    private ActivityLoginBinding binding;
    private Tab tabServiceProvider;
    private Tab tabCustomer;
    private ArrayList<Tab> tabArrayList;

    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue queue;
    static DialogFragment dialogFragment;

    JSONObject jsonObjectToken;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();
        queue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();
        addTabTittleAndViewPagerData();


        new TabLayoutMediator(binding.tabLayoutLogin, binding.vp2Login, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabArrayList.get(position).getName());

            }
        }).attach();


    }


    // Request Customer login
    private void postLoginCustomerRequest(String email, String password) {
        showDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            String url = "https://studentucas.awamr.com/api/auth/login/user";

            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObjectRequest = new JsonObjectRequest(POST, url,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //رساله اذا نجح
                    stopDialog();
                    try {
                        if (response.getBoolean("success")) {


                            intent(AllWorkActivity.class);
                            jsonObjectToken = response.getJSONObject("data");

                            saveTokenCustomer(jsonObjectToken.getString("token"));
                            //shared checkbox
                            if (preferences.getBoolean(Constant.SAVE_LOGIN_CUSTOMER, false)) {
                                editor.putBoolean(Constant.LOGIN_CUSTOMER, true).commit();
                                editor.remove(Constant.LOGIN_SERVICE_PROVIDER).commit();
                            }

                            finish();

                        } else {

                            SnackBar.snackBar(binding.textViewBackground, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    stopDialog();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        queue.add(jsonObjectRequest);
    }

    private void postLoginProviderRequest(String email, String password) {


        showDialog();
        JSONObject jsonObject = new JSONObject();
        try {
            String url = "https://studentucas.awamr.com/api/auth/login/delivery";

            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObjectRequest = new JsonObjectRequest(POST, url,
                    jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //رساله اذا نجح
                    stopDialog();
                    try {
                        if (response.getBoolean("success")) {


                            jsonObjectToken = response.getJSONObject("data");
                            intent(HomeServiceProviderActivity.class);
                            saveTokenProvider(jsonObjectToken.getString("token"));


                            //shared checkbox
                            if (preferences.getBoolean(Constant.SAVE_LOGIN_PROVIDER, false)) {
                                editor.putBoolean(Constant.LOGIN_SERVICE_PROVIDER, true).commit();
                                editor.remove(Constant.LOGIN_CUSTOMER).commit();
                            }

                            finish();

                        } else {
                            SnackBar.snackBar(binding.textViewBackground, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    stopDialog();
                    Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        queue.add(jsonObjectRequest);
    }


    @Override //Customer  الخاصه بل  Login استقبال بيانات  من فراجمنت ال
    public void onClickButtonLoginCustomer(String email, String password) {
        postLoginCustomerRequest(email, password);


    }

    @Override //ServiceProvider  الخاصه بل  Login استقبال بيانات  من فراجمنت ال
    public void onClickButtonLoginServiceProvider(String email, String password) {
        postLoginProviderRequest(email, password);

    }

    @Override
    public void onClickTvRegisterIntent() {
        startActivity(new Intent(getBaseContext(), RegisterActivity.class));
    }


    @Override
    public void saveLogin(boolean b) {
    }

    private void addTabTittleAndViewPagerData() {

        //خاص ب Tab
        tabArrayList = new ArrayList<>();
        tabServiceProvider = new Tab("Service provider", 1);
        tabCustomer = new Tab("Customer", 2);

        tabArrayList.add(tabServiceProvider);
        tabArrayList.add(tabCustomer);


        //خاص ب viewPager
        ArrayList<Fragment> arrayListFragment = new ArrayList<>();
        arrayListFragment.add(FragmentLogin.newInstance(tabServiceProvider.getName()));
        arrayListFragment.add(FragmentLogin.newInstance(tabCustomer.getName()));

        ViewPagerAdapter viewPager = new ViewPagerAdapter(this, arrayListFragment);
        binding.vp2Login.setAdapter(viewPager);

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

    private void intent(Class c) {
        startActivity(new Intent(getBaseContext(), c));
    }


    @Override
    protected void onStart() {
        super.onStart();
        dialogInternet();
    }

    private void dialogInternet() {

        BroadcastReceiver networkChangeReceiver = new CheckConnectivity(this, new OnClickRateProvider() {
            @Override
            public void onClick() {
                DialogFragmentInternet dialogFragmentInternet = DialogFragmentInternet.newInstance();
                dialogFragmentInternet.show(getSupportFragmentManager(), null);
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    @Override
    public void onClickConnection() {
        Intent intent = new Intent();
        intent.setAction(ACTION_WIFI_SETTINGS);
        setResult(1, intent);
        startActivity(intent);
    }

}
