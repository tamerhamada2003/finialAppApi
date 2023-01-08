package com.example.applicatioprojectxd.Fragment;

import static com.android.volley.Request.Method.GET;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.SpinnerAdapter;

import com.example.applicatioprojectxd.Classes.AllWork;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.databinding.FragmentRegsterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentRegister extends Fragment {
    private FragmentRegsterBinding binding;
    private String fullName, email, phone, password;

    private RequestQueue queue;


    private ArrayList<AllWork> allWorkArrayList;
    private AllWork work;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_TAB_NAME_REGISTER = "tabName";

    onClickInterFace interFace;
    //    private String mParam1;
    private String tabNAme;

    public FragmentRegister() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onClickInterFace) {
            interFace = (onClickInterFace) context; //interFace الداله الي موجودة في الاكتيفيتي خزنا في
        } else {
            throw new ClassCastException("الرحاء انك تعمل  implements");
        }
    }

    @Override //لما تتدمر الفراجمنت خلي interFace فارغ
    public void onDetach() {
        super.onDetach();
        interFace = null;
    }


    public static FragmentRegister newInstance(String tabName) {
        FragmentRegister fragment = new FragmentRegister();
        Bundle args = new Bundle();
        args.putString(ARG_TAB_NAME_REGISTER, tabName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Initial();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabNAme = getArguments().getString(ARG_TAB_NAME_REGISTER);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegsterBinding.inflate(inflater, container, false);


        getAllDataSpinnerRequest();
        showAndDismissSpinner();


        //خاص بحفظ قيمه الايدي الخاص بلمهن الصناعيه
        binding.editTextSelectServiceRegisterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                editor.putInt(Constant.SAVE_ITEM_SPINNER, i + 1).commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvSignInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interFace.onFragmentClick();
            }
        });


        binding.buttonSignUpRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillData();
                int item = preferences.getInt(Constant.SAVE_ITEM_SPINNER, -1);
                String convertItemSpinner = String.valueOf(item);
                if (tabNAme.equals("Service provider")) {

                    interFace.onClickButtonRegisterServiceProvider(fullName, email, phone,
                            password, convertItemSpinner);


                } else if (tabNAme.equals("Customer")) {
                    interFace.onClickButtonRegisterCustomer(fullName, email, phone, password);

                }
            }
        });


        return binding.getRoot();


    }


    //Request Spinner خا ص بل
    private void getAllDataSpinnerRequest() {

        String uri = "https://studentucas.awamr.com/api/all/works";

        StringRequest stringRequest = new StringRequest(GET, uri, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

//                binding.ProgressBar.setVisibility(View.GONE);
                try {

                    //response convert JSONObject
                    JSONObject jsonObject = new JSONObject(response); //JSONObject {}  << response لأنو أول بداية ال

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i); //JSONObject بتجيب كل البيانات التي بداخل
                        String name = jsonObject1.getString("name");
                        int id = jsonObject1.getInt("id");

                        work = new AllWork(name, id);

                        Log.e("MyData", "" + name + id);
                        allWorkArrayList.add(work);
                        adapterSpinner(allWorkArrayList);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(stringRequest);
    }


    public void adapterSpinner(ArrayList<AllWork> allWork) {

        SpinnerAdapter adapter = new SpinnerAdapter(allWork);

        binding.editTextSelectServiceRegisterSpinner.setAdapter(adapter);


    }


    public void fillData() {
        fullName = binding.editTextFullNameRegister.getText().toString();
        email = binding.editTextEmailRegister.getText().toString();
        phone = binding.editTextPhoneRegister.getText().toString();
        password = binding.editTextPasswordRegister.getText().toString();


    }


    public interface onClickInterFace {
        void onFragmentInterFace(Spinner spinner); //Spinner ارسال بينانات الى الاكتيفيتي لعمل اخفاء واظهار لل

        void onFragmentClick();


        // Customer ارسال بيانات خاصه بل
        void onClickButtonRegisterCustomer(String fullName, String email, String phone, String password);


        // Provider ارسال بيانات خاصه بل
        void onClickButtonRegisterServiceProvider(String fullName, String email, String phone,
                                                  String password, String spinnerWorkId);
    }


    //  واظهارة Spinner داله لاخفاء حقل
    public void showAndDismissSpinner() {
        Spinner spinner = binding.editTextSelectServiceRegisterSpinner;
        interFace.onFragmentInterFace(spinner);

    }

    public void Initial() {
        queue = Volley.newRequestQueue(getActivity());
        allWorkArrayList = new ArrayList<>();
        preferences = getActivity().getSharedPreferences("file_spinner", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


}