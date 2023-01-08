package com.example.applicatioprojectxd.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatioprojectxd.Classes.HomeServiceProvider;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.CustomItemHomeProviderBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHomeProvider extends RecyclerView.Adapter<AdapterHomeProvider.ProviderViewHolder> {

    ArrayList<HomeServiceProvider> arrayList;


    OnClickListenerHomeProvider onClick;

    public AdapterHomeProvider(ArrayList<HomeServiceProvider> arrayList, OnClickListenerHomeProvider onClick) {
        this.arrayList = arrayList;
        this.onClick = onClick;

    }

    @NonNull

    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CustomItemHomeProviderBinding binding = CustomItemHomeProviderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ProviderViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, @SuppressLint("RecyclerView") int position) {

        HomeServiceProvider serviceProvider = arrayList.get(position);

        holder.binding.idOrderHome.setText("#" + serviceProvider.getOrderId());
        holder.binding.tvServiceWorkHome.setText(serviceProvider.getNameWork());


        holder.binding.tvDateHome.setText(serviceProvider.getCreatedAt());

        holder.binding.tvNameUserTowHome.setText(serviceProvider.getNameUser());


        String photo = serviceProvider.getPhoto();

        Picasso.get().load(photo).fit().centerInside()
                .placeholder(R.drawable.background).
                into(holder.binding.imageViewProblemHome);

        holder.binding.btnDetailsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickHome(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ProviderViewHolder extends RecyclerView.ViewHolder {

        CustomItemHomeProviderBinding binding;

        public ProviderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemHomeProviderBinding.bind(itemView);
        }
    }



    public interface OnClickListenerHomeProvider {
        void onClickHome(int position);
    }


}
