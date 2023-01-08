package com.example.applicatioprojectxd.Activity;

import static com.android.volley.Request.Method.POST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.AdapterHomeProvider;
import com.example.applicatioprojectxd.Classes.HomeServiceProvider;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.Fragment.FragmentAllWork;
import com.example.applicatioprojectxd.Fragment.FragmentHomeServiceProvider;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetails;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetailsServiceProvider;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetailsTow;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetailsTowServiceProvider;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.ActivityHomeServieProviderBinding;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeServiceProviderActivity extends AppCompatActivity implements
        FragmentHomeServiceProvider.OnClickHomeServiceProvider, FragmentItemDetailsServiceProvider.OnClickListenerItemDetailsServiceProvider {


    private ActivityHomeServieProviderBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeServieProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Home Service Provider");

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();

        binding.BottomNavigationViewServiceProvider.
                setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @SuppressLint({"NonConstantResourceId", "ResourceAsColor"})
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.nav_service: //عند الضغط على bottomNavigationView  اظهر ال service

                                // ما تسمحله يضغط على الايتم كمان مره ويعيد ارسال بيانات Item اذا كان بنفس ال
                                if (item.isChecked()) {
                                } else {
                                    getSupportActionBar().show();

                                    showFragmentHomeServiceProvider();
                                }
                                return true;
                            case R.id.nav_orders:
                                getSupportActionBar().hide();

                                showFragmentItemDetailsServiceProvider();

                                return true;
                        }
                        return false;
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClickPosition(HomeServiceProvider homeServiceProvider) {

        // ارسال اوبجكت
        Intent intent = new Intent(getBaseContext(), DetailsServiceProviderActivity.class);
        intent.putExtra(Constant.SERVICE_PROVIDER_DETAILS, homeServiceProvider);
        startActivity(intent);
    }


    @Override
    public void onClickTabName(String tabNameItemDetails) {
        // FragmentItemDetailsTow والأن سوف نقوم بارسال اسم التاب المضغوط عليه في فراجمنت
        FragmentItemDetailsTowServiceProvider serviceProvider = FragmentItemDetailsTowServiceProvider.
                newInstance(tabNameItemDetails);


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.FragmentItemDetailsServiceProvider,
                serviceProvider).commit();
    }


    // service provider اضهر الفراجمنت الخاصه بتفاصيل الطلب لل
    private void showFragmentItemDetailsServiceProvider() {
        FragmentItemDetailsServiceProvider serviceProvider = FragmentItemDetailsServiceProvider.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainerViewHomeServiceProvider, serviceProvider).commit();

    }


    //اظهر فراجمنت Home service provider
    public void showFragmentHomeServiceProvider() {
        FragmentHomeServiceProvider fragmentHomeServiceProvider = FragmentHomeServiceProvider.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainerViewHomeServiceProvider,
                fragmentHomeServiceProvider).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ment_out, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.out_menu:

                editor.remove(Constant.LOGIN_SERVICE_PROVIDER).commit();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                return true;
        }
        return false;

    }
}