package com.example.applicatioprojectxd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.applicatioprojectxd.Adapter.ViewPagerRegisterAdapter;
import com.example.applicatioprojectxd.Fragment.FragmentLogin;
import com.example.applicatioprojectxd.Fragment.FragmentRegister;

import com.example.applicatioprojectxd.Tab;
import com.example.applicatioprojectxd.databinding.ActivityRegisterBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements FragmentRegister.onClickInterFace {

    ActivityRegisterBinding binding;
    ArrayList<Tab> arrayListTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayListTab = new ArrayList<>();


        arrayListTab.add(new Tab("Service provider", 0));
        arrayListTab.add(new Tab("Customer", 1));
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(FragmentRegister.newInstance("", ""));
        fragmentArrayList.add(FragmentRegister.newInstance("", ""));

        ViewPagerRegisterAdapter adapter = new ViewPagerRegisterAdapter(this, fragmentArrayList);
        binding.vp2Register.setAdapter(adapter);



        new TabLayoutMediator(binding.tabLayoutRegister, binding.vp2Register, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(arrayListTab.get(position).getName());


            }
        }).attach();


    }


    @Override
    public void onFragmentInterFace(TextInputLayout layout) {
        binding.tabLayoutRegister.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            // عندما تحدد عليه
            public void onTabSelected(TabLayout.Tab tab) {



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
                Toast.makeText(RegisterActivity.this, "onTabReselected", Toast.LENGTH_SHORT).show();
            }
        });

    }
}