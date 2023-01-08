package com.example.applicatioprojectxd.Fragment;

import static com.android.volley.Request.Method.GET;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicatioprojectxd.Adapter.AdapterRecyclerHorizontal;
import com.example.applicatioprojectxd.Adapter.AllWorkAdapter;
import com.example.applicatioprojectxd.Classes.AllWork;
import com.example.applicatioprojectxd.Classes.DataImage;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentAllWorkBinding;
import com.example.applicatioprojectxd.listener.OnClickAllWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentAllWork extends Fragment {

    FragmentAllWorkBinding binding;
    DialogFragment dialogFragment;

    String name;
    int id;
    RequestQueue requestQueue; //تقوم بتشغيل أي Request
    ArrayList<AllWork> allWorkArrayList;
    AllWork work;

    AllWorkAdapter adapter;

    OnClick onClick;


    public FragmentAllWork() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClick)
            onClick = (OnClick) context;
        else {
            throw new  ClassCastException ("الرحاء انك تعمل  implements");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onClick=null;
    }

    public static FragmentAllWork newInstance() {
        FragmentAllWork fragment = new FragmentAllWork();
        Bundle args = new Bundle();
//        args.putString(NAME, name);
//        args.putInt(ID, id);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        allWorkArrayList = new ArrayList<>();
        //INIT
        requestQueue = Volley.newRequestQueue(getActivity());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            name = getArguments().getString(NAME);
//            id = getArguments().getInt(ID);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllWorkBinding.inflate(getLayoutInflater(), container, false);
        adapterRecyclerViewHorizontal();

        getAllDataAllWork(); //الخاص بريكوست المهن الصناعيه


        return binding.getRoot();
    }


    private void adapterRecyclerViewHorizontal() {
        ArrayList<DataImage> arrayList = new ArrayList<>();

        arrayList.add(new DataImage(1, R.drawable.image));
        arrayList.add(new DataImage(2, R.drawable.image2));
        arrayList.add(new DataImage(3, R.drawable.image3));

        AdapterRecyclerHorizontal adapter = new AdapterRecyclerHorizontal(arrayList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false);
        binding.RecyclerViewHorizontal.setAdapter(adapter);
        binding.RecyclerViewHorizontal.setLayoutManager(layoutManager);
        binding.RecyclerViewHorizontal.setHasFixedSize(true);
    }



    private void getAllDataAllWork() {
        showDialog();
        String uri = "https://studentucas.awamr.com/api/all/works";

        StringRequest stringRequest = new StringRequest(GET, uri, new Response.Listener<String>() { // API طلب على ال
            @Override //حاله النجاح
            public void onResponse(String response) {

                stopDialog();
                try {

                    JSONObject jsonObject = new JSONObject(response); //JSONObject {}  << response لأنو أول بداية ال

                    Log.d("API", "onResponse: " + jsonObject.getJSONArray("data"));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    JSONObject jsonObject1;
                    for (int i = 0; i < jsonArray.length(); i++) {


                        jsonObject1 = jsonArray.getJSONObject(i); //JSONObject بتجيب كل البيانات التي بداخل
                        name = jsonObject1.getString("name");
                        id = jsonObject1.getInt("id");

                        work = new AllWork(name, id);

                        Log.e("MyData", "" + name + id);
                        adapterRecyclerViewAllWork (work);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override//حاله الفشل
            public void onErrorResponse(VolleyError error) {
                stopDialog();

            }
        });
        requestQueue.add(stringRequest);
    }





//    Gird خاصه بلمهن الصناعيه
    public void adapterRecyclerViewAllWork(AllWork allWork) {

        allWorkArrayList.add(allWork);
        adapter = new AllWorkAdapter(allWorkArrayList, new OnClickAllWork() {
            @Override
            //الانتقال عند الضغط على مهنه من المهن الصناعيه
            public void onClickSelectService(int position) {

                onClick.onClickSelectService(position);
            }
        });
        binding.RecyclerViewAllWork.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewAllWork.setLayoutManager(manager);
    }


    private void showDialog() {
        dialogFragment = DialogFragment.newInstance("Loading ...");
        dialogFragment.show(getChildFragmentManager(),null);
    }


    private void stopDialog() {
        dialogFragment.onStop();

    }

    //أكشن عند الضغط على مهنه من المهن الصناعيه
    public interface OnClick{
        void onClickSelectService(int position);
    }


}