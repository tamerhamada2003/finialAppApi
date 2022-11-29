package com.example.applicatioprojectxd.Fragment;

import static androidx.constraintlayout.widget.ConstraintSet.GONE;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;

import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentRegsterBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class FragmentRegister extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    onClickInterFace interFace;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegister() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onClickInterFace){
            interFace = (onClickInterFace) context; //interFace الداله الي موجودة في الاكتيفيتي خزنا في
        }else {
            throw new  ClassCastException ("الرحاء انك تعمل  implements");
        }
    }

    @Override //لما تتدمر الفراجمنت خلي interFace فارغ
    public void onDetach() {
        super.onDetach();
        interFace=null;
    }


    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        FragmentRegsterBinding binding = FragmentRegsterBinding.inflate(inflater, container, false);
        TextInputLayout layout = binding.TextInputLayout7;
//        layout.setVisibility(View.GONE);
      interFace.onFragmentInterFace(layout);
        return binding.getRoot();


    }



    //أنشأنا كلاس وبداخل دااله
    public  interface  onClickInterFace{
        void onFragmentInterFace(TextInputLayout layout);
    }

}