package com.example.applicatioprojectxd.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentDialogBinding;


public class DialogFragment extends androidx.fragment.app.DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITTLE = "tittle";

    // TODO: Rename and change types of parameters
    private String tittle;

    public DialogFragment() {
        // Required empty public constructor
    }


    public static DialogFragment newInstance(String tittle) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITTLE, tittle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tittle = getArguments().getString(ARG_TITTLE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentDialogBinding binding = FragmentDialogBinding.inflate(getLayoutInflater()
                , container, false);

//        binding.tvLoading.setText(tittle);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return binding.getRoot();
    }


}