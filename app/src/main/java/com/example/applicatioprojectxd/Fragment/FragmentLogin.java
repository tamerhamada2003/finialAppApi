package com.example.applicatioprojectxd.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EDIT_TEXT= "edit_text";
    private static final String ARG_PASSWORD = "password";

    // TODO: Rename and change types of parameters
    private String editText;
    private String password;

    public FragmentLogin() {
        // Required empty public constructor
    }


    public static FragmentLogin newInstance(String editText, String password) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_EDIT_TEXT, editText);
        args.putString(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            editText = getArguments().getString(ARG_EDIT_TEXT);
            password = getArguments().getString(ARG_PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLoginBinding binding =FragmentLoginBinding.inflate(inflater,container,false);


           return binding.getRoot();


    }


}