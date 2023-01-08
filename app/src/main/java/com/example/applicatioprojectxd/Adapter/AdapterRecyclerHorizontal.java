package com.example.applicatioprojectxd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatioprojectxd.Classes.DataImage;
import com.example.applicatioprojectxd.databinding.CustomItemHorizontalBinding;

import java.util.ArrayList;

public class AdapterRecyclerHorizontal extends RecyclerView.Adapter<AdapterRecyclerHorizontal.ViewHolder> {

    ArrayList<DataImage> arrayList;

    public AdapterRecyclerHorizontal(ArrayList<DataImage> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemHorizontalBinding binding = CustomItemHorizontalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataImage dataImage = arrayList.get(position);
        holder.binding.imageViewHorizontal.setImageResource(dataImage.getImage());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        CustomItemHorizontalBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomItemHorizontalBinding.bind(itemView);
        }
    }
}
