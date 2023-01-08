package com.example.applicatioprojectxd.Snackbar;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SnackBar {


    public static void snackBar(TextView textView, String message) {
        Snackbar snackbar
                = Snackbar.make(textView, "onClicked", Snackbar.LENGTH_LONG);

        snackbar.setText(message);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        snackbar.show();

    }
}
