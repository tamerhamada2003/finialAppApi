package com.example.applicatioprojectxd.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentDialogBinding;
import com.example.applicatioprojectxd.databinding.FragmentDialogInternetBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogFragmentInternet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentInternet extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match

    OnClickConnection onClickConnection;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickConnection) {
            onClickConnection = (OnClickConnection) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onClickConnection = null;
    }

    public DialogFragmentInternet() {
        // Required empty public constructor
    }

    public static DialogFragmentInternet newInstance() {
        DialogFragmentInternet fragment = new DialogFragmentInternet();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentDialogInternetBinding binding =
                FragmentDialogInternetBinding.inflate(getLayoutInflater());


        binding.btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickConnection.onClickConnection();
            }

        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getDialog().setCancelable(false);

        return binding.getRoot();
    }


    public interface OnClickConnection {
        void onClickConnection();
    }
}