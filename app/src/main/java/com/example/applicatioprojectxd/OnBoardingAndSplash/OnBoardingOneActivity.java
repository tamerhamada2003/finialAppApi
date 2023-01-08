package com.example.applicatioprojectxd.OnBoardingAndSplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.applicatioprojectxd.R;


public class OnBoardingOneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boording_one);
        getSupportActionBar().hide();


        Button button =findViewById(R.id.btn_onBoardingOne);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(), OnBoardingTowActivity.class);
                startActivity(intent);
            }
        });
    }
}