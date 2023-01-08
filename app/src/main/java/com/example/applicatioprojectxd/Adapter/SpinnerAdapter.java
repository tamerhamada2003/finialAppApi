package com.example.applicatioprojectxd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.applicatioprojectxd.Classes.AllWork;
import com.example.applicatioprojectxd.R;

import java.util.ArrayList;
import java.util.List;


public class SpinnerAdapter extends BaseAdapter {

    private List<AllWork> allWorks;

    public SpinnerAdapter(ArrayList<AllWork> allWorks) {
        this.allWorks = allWorks;
    }

    public List<AllWork> getAllWork() {
        return allWorks;
    }

    public void setAllWork(List<AllWork> categories) {
        this.allWorks = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return allWorks.size();
    }

    @Override
    public AllWork getItem(int i) {
        return allWorks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return allWorks.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_work_spinner, null, false);
        }
        TextView tv = v.findViewById(R.id.textView6);
        AllWork category = getItem(i);
        tv.setText(category.getName());
        return v;
    }
}

