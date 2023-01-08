package com.example.applicatioprojectxd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Classes.ItemDetails;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.Fragment.FragmentAllWork;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetails;
import com.example.applicatioprojectxd.Fragment.FragmentItemDetailsTow;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.ActivityAllWorkBinding;
import com.google.android.material.navigation.NavigationBarView;

//بمثابه واجهه Home
public class AllWorkActivity extends AppCompatActivity implements FragmentAllWork.OnClick,
        FragmentItemDetails.onClickListenerItemDetails, FragmentItemDetailsTow.OnClickSendDataItemDetails {


    ActivityAllWorkBinding binding;

    RequestQueue queue;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Home Customer");

        queue = Volley.newRequestQueue(this);

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);
        editor = preferences.edit();



        binding.BottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint({"NonConstantResourceId", "ResourceAsColor"})
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_service: //عند الضغط على bottomNavigationView  اظهر ال service

                        // ما تسمحله يضغط على الايتم كمان مره ويعيد ارسال بيانات Item اذا كان بنفس ال
                        if (item.isChecked()) {
                        } else {
                            showFragmentService();
                            getSupportActionBar().show();

                        }
                        return true;
                    case R.id.nav_orders:
                        getSupportActionBar().hide();

                        showFragmentItemDetails();

                        return true;
                }
                return false;
            }
        });
    }


    //اضهر الفراجمنت الخاصه بتفاصيل الطلب
    private void showFragmentItemDetails() {
        FragmentItemDetails fragmentItemDetails = FragmentItemDetails.newInstance();
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.fragment_select_service, fragmentItemDetails).commit();

    }


    //اضهر الفراجمنت الخاصه بلمهن الصناعيه
    public void showFragmentService() {
        FragmentAllWork fragmentAllWork = FragmentAllWork.newInstance();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_select_service,
                fragmentAllWork).commit();
    }


    @Override
    //الانتقال عند الضغط على مهنه من المهن الصناعيه
    public void onClickSelectService(int position) {

        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), ProblemActivity.class);
        intent.putExtra(Constant.ID_ALL_WORK, position); //ارسال الايدي الخاص بلمهنه الصناعه
        startActivity(intent);
    }

    @Override
    //fragmentItemDetails استقبلنا الداله من فراجمنت
    public void onClickTabName(String tabNameItemDetails) {

        // FragmentItemDetailsTow والأن سوف نقوم بارسال اسم التاب المضغوط عليه في فراجمنت
        FragmentItemDetailsTow fragmentItemDetailsTow = FragmentItemDetailsTow.
                newInstance(tabNameItemDetails);


        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragmentContainerViewItemDetails,
                fragmentItemDetailsTow).commit();

    }


    @Override
    //ارسال اوبجكت الى واجهه التفاصيل
    public void clickIntent(ItemDetails itemDetails) {
        Intent intent = new Intent(getBaseContext(), DetailsOrderActivity.class);
        intent.putExtra(Constant.Serializable, itemDetails);

        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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


                editor.remove(Constant.LOGIN_CUSTOMER).commit();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
                return true;
        }
        return false;

    }
}