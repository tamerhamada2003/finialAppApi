package com.example.applicatioprojectxd.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.applicatioprojectxd.Adapter.ViewPagerAdapter;
import com.example.applicatioprojectxd.Classes.Tab;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentItemDetailsServiceProviderBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class FragmentItemDetailsServiceProvider extends Fragment {

    FragmentItemDetailsServiceProviderBinding binding;
    ArrayList<Tab> arrayListTab;

    OnClickListenerItemDetailsServiceProvider onClickListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickListenerItemDetailsServiceProvider) {
            onClickListener = (OnClickListenerItemDetailsServiceProvider) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClickListener = null;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentItemDetailsServiceProvider() {
        // Required empty public constructor
    }


    public static FragmentItemDetailsServiceProvider newInstance() {
        FragmentItemDetailsServiceProvider fragment = new FragmentItemDetailsServiceProvider();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemDetailsServiceProviderBinding.inflate(getLayoutInflater());

        ListenerTabLayoutItemDetails();
        fillDataTab();

        ViewPagerJoinTabLayout();

        return binding.getRoot();
    }


    // Tab داله تعمل لتعبئه ال
    private void fillDataTab() {
        arrayListTab = new ArrayList<>();

        Tab tabPending = new Tab("Pending", 1);
        Tab tabUnderway = new Tab("Underway", 2);
        Tab tabCompleted = new Tab("Completed", 3);

        arrayListTab.add(tabPending);
        arrayListTab.add(tabUnderway);
        arrayListTab.add(tabCompleted);


    }


    //داله ربط الفيو باجر بلتاب
    private void ViewPagerJoinTabLayout() {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        for (int i = 0; i < arrayListTab.size(); i++) {
            fragmentArrayList.add(FragmentItemDetailsTow.newInstance());
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), fragmentArrayList);
        binding.ViewPagerServiceProvider.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(binding.tabLayoutItemDetailsServiceProvider, binding.ViewPagerServiceProvider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(arrayListTab.get(position).getName());
            }
        }).attach();
    }


    //Home service provider  قمنا بارسال اسماء التاابات اى الاكتيفي الخاصه
    public interface OnClickListenerItemDetailsServiceProvider {
        void onClickTabName(String tabNameItemDetails);
    }


    //داله تستدعى عن د الضغط على التاب  وارسال النص الي بداخل التاب الى الاكتيفيتي
    public void ListenerTabLayoutItemDetails() {
        binding.tabLayoutItemDetailsServiceProvider.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().toString().equals("Pending")) {
                    onClickListener.onClickTabName(tab.getText().toString());
                } else if (tab.getText().toString().equals("Underway")) {

                    onClickListener.onClickTabName(tab.getText().toString());
                } else if (tab.getText().toString().equals("Completed")) {
                    onClickListener.onClickTabName(tab.getText().toString());

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}