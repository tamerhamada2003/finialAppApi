package com.example.applicatioprojectxd.Classes;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicatioprojectxd.Fragment.DialogFragment;
import com.example.applicatioprojectxd.Fragment.DialogFragmentInternet;
import com.example.applicatioprojectxd.R;
import com.example.applicatioprojectxd.databinding.FragmentDialogInternetBinding;
import com.example.applicatioprojectxd.listener.OnClickAllWork;
import com.example.applicatioprojectxd.listener.OnClickRateProvider;

public class CheckConnectivity extends BroadcastReceiver {
    OnClickRateProvider onClickRateProvider;


    Context context;

    public CheckConnectivity(Context context, OnClickRateProvider onClickRateProvider) {
        this.onClickRateProvider = onClickRateProvider;
        this.context = context;
    }


    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                onClickRateProvider.onClick();
            } else {
                Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
            }
        }
    }
}