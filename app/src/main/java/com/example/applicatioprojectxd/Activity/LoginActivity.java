package com.example.applicatioprojectxd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.applicatioprojectxd.Adapter.ViewPagerLoginAdapter;
import com.example.applicatioprojectxd.Fragment.FragmentLogin;
import com.example.applicatioprojectxd.databinding.ActivityLoginBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ArrayList<String>tabArrayList=new ArrayList<>();
        tabArrayList.add("Service provider");
        tabArrayList.add("Customer");


        ArrayList<Fragment> arrayListFragment = new ArrayList<>();
        arrayListFragment.add(FragmentLogin.newInstance("",""));
        arrayListFragment.add(FragmentLogin.newInstance("",""));

        ViewPagerLoginAdapter viewPager = new ViewPagerLoginAdapter(this, arrayListFragment);
        binding.vp2Login.setAdapter(viewPager);


        new TabLayoutMediator(binding.tabLayoutLogin, binding.vp2Login, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabArrayList.get(position));

            }
        }).attach();


    }
}