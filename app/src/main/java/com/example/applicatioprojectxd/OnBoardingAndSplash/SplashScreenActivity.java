package com.example.applicatioprojectxd.OnBoardingAndSplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.applicatioprojectxd.OnBoardingAndSplash.OnBoardingOneActivity;
import com.example.applicatioprojectxd.R;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(getBaseContext(),
                        OnBoardingOneActivity.class);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }
}