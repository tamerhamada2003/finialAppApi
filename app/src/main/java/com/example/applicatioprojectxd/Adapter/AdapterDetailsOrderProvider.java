package com.example.applicatioprojectxd.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatioprojectxd.Classes.DataProvider;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.CustomItemProviderBinding;
import com.example.applicatioprojectxd.listener.OnClickRadio;
import com.example.applicatioprojectxd.listener.OnClickRateProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDetailsOrderProvider extends RecyclerView.Adapter<AdapterDetailsOrderProvider.DetailsOrderViewHolder> {

    ArrayList<DataProvider> arrayList;

    OnClickRadio clickRadio;

    public AdapterDetailsOrderProvider(ArrayList<DataProvider> arrayList, OnClickRadio onClickRadio) {
        this.arrayList = arrayList;
        this.clickRadio = onClickRadio;

    }

    @NonNull
    @Override
    public DetailsOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CustomItemProviderBinding binding
                = CustomItemProviderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailsOrderViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsOrderViewHolder holder, int position) {

        DataProvider dataProvider = arrayList.get(position);
        holder.binding.tvNameProviderCustom.setText(dataProvider.getNameProvider());

        String photo = dataProvider.getPhotoProvider();


        if (photo != null && !photo.isEmpty()) {
            Picasso.get().load(photo).fit().centerInside()
                    .placeholder(R.drawable.tamer1).
                    into(holder.binding.imageViewCustomProvider);
        }


        holder.binding.radioButtonSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // قيمة  listener اذا كان ضاغط عليه خزن في
                if (b) {
                    clickRadio.onClickRadioButton(true);
                }
            }
        });




    }

    @Override
    public int getItemCount() {

        return arrayList.size();


    }

    class DetailsOrderViewHolder extends RecyclerView.ViewHolder {

        CustomItemProviderBinding binding;

        public DetailsOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemProviderBinding.bind(itemView);
        }
    }
}
