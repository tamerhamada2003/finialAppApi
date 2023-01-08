package com.example.applicatioprojectxd.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.Validation.Validation;
import com.example.applicatioprojectxd.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {
    FragmentLoginBinding binding;
    OnClickListener listener;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TAB_NAME_LOGIN = "tabName";
//    private static final String ARG_PASSWORD = "password";

    // TODO: Rename and change types of parameters
    private String tabName;
//    private String password;

    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickListener) {
            listener = (OnClickListener) context; //interFace الداله الي موجودة في الاكتيفيتي خزنا في
        } else {
            throw new ClassCastException("الرحاء انك تعمل  implements");
        }
    }

    @Override //لما تتدمر الفراجمنت خلي interFace فارغ
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static FragmentLogin newInstance(String tabName) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_NAME_LOGIN, tabName);
//        args.putString(ARG_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = preferences.edit();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_TAB_NAME_LOGIN);
//            password = getArguments().getString(ARG_PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);


        clickedButton();






        binding.tvSignInLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickTvRegisterIntent();
            }
        });




        return binding.getRoot();


    }

    //Login اكشن على فيو شاشه ال
    public interface OnClickListener {

        void onClickButtonLoginCustomer(String email, String password);


        void onClickButtonLoginServiceProvider(String email, String password);

        void onClickTvRegisterIntent();

        void saveLogin(boolean b);

    }


    //تستدعى عند الضغط على الزر وعمل فحص هل هوا في التاب الاول ام التاني
    private void clickedButton() {
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tabName.equals("Service provider")) {

                    //  ServiceProvider الخاصه بLogin ارسال البيانات لواجهه ال
                    listener.
                            onClickButtonLoginServiceProvider(binding.editTextEmailLogin.getText()
                                            .toString()
                                    , binding.editTextPasswordLogin.getText().toString());


                    if (binding.checkBoxRemember.isChecked()) {
                        Toast.makeText(getActivity(), "provider", Toast.LENGTH_SHORT).show();
                        editor.putBoolean(Constant.SAVE_LOGIN_PROVIDER, true).commit();
                    }


                } else {
                    //  Customer الخاصه بLogin ارسال البيانات لواجهه ال
                    listener.onClickButtonLoginCustomer(binding.editTextEmailLogin.getText().toString()
                            , binding.editTextPasswordLogin.getText().toString());


                    if (binding.checkBoxRemember.isChecked()) {
                        Toast.makeText(getActivity(), "Customer", Toast.LENGTH_SHORT).show();
                        editor.putBoolean(Constant.SAVE_LOGIN_CUSTOMER, true).commit();
                    }


                }
            }
        });
    }


    //      وعمل فحص هل هوا في التاب الاول ام التاني  Check Box تستدعى عند الضغط على ال
    private void checkedRememberMe() {


        if (tabName.equals("Service provider")) {
            listener.saveLogin(binding.checkBoxRemember.isChecked());

        } else {
            listener.saveLogin(binding.checkBoxRemember.isChecked());
        }


    }


}
