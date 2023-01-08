package com.example.applicatioprojectxd.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicatioprojectxd.Classes.ItemDetails;
import com.example.applicatioprojectxd.databinding.CustomItemDetailsBinding;
import com.example.applicatioprojectxd.listener.OnClickAllWork;
import com.example.applicatioprojectxd.listener.OnClickItemDetails;

import java.util.ArrayList;

public class AdapterItemDetailsOrder extends RecyclerView.Adapter<AdapterItemDetailsOrder.OrderViewHolder> {

    ArrayList<ItemDetails> arrayList;

    OnClickItemDetails onClickItemDetails;

    public AdapterItemDetailsOrder(ArrayList<ItemDetails> arrayList, OnClickItemDetails onClickItemDetails) {
        this.arrayList = arrayList;
        this.onClickItemDetails = onClickItemDetails;

    }


    //details item service provider
    public AdapterItemDetailsOrder(ArrayList<ItemDetails> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomItemDetailsBinding binding = CustomItemDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new OrderViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ItemDetails itemDetails = arrayList.get(position);

        holder.binding.dateOrder.setText(itemDetails.getUnderWayCreatedAt());
        holder.binding.numberOrder.setText("Order #" + itemDetails.getOrderId());
        holder.binding.tvServiceType2.setText(itemDetails.getNameWork());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemDetails.onClickItemDetails(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        CustomItemDetailsBinding binding;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemDetailsBinding.bind(itemView);
        }
    }
}
