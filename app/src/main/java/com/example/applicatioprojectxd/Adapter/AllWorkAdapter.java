package com.example.applicatioprojectxd.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatioprojectxd.Classes.AllWork;
import com.example.applicatioprojectxd.databinding.CustomItemGridViewBinding;
import com.example.applicatioprojectxd.listener.OnClickAllWork;

import java.util.ArrayList;


public class AllWorkAdapter extends RecyclerView.Adapter<AllWorkAdapter.WorkViewHolder> {

    ArrayList<AllWork> allWorkArrayList;
    OnClickAllWork interFace;

    public AllWorkAdapter(ArrayList<AllWork> allWorkArrayList, OnClickAllWork interFace) {
        this.allWorkArrayList = allWorkArrayList;
        this.interFace = interFace;
    }


    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CustomItemGridViewBinding binding = CustomItemGridViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WorkViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, @SuppressLint("RecyclerView") int position) {

        AllWork work = allWorkArrayList.get(position);

        holder.binding.tvIdAllWork.setText(String.valueOf(work.getId()));
        holder.binding.tvProfession.setText(work.getName());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interFace.onClickSelectService(work.getId());

            }
        });

    }


    @Override
    public int getItemCount() {
        return allWorkArrayList.size();
    }

    class WorkViewHolder extends RecyclerView.ViewHolder {
        CustomItemGridViewBinding binding;

        public WorkViewHolder(View ItemVew) {
            super(ItemVew);
            binding = CustomItemGridViewBinding.bind(ItemVew);
        }
    }
}

