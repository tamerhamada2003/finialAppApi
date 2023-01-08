package com.example.applicatioprojectxd.OnBoardingAndSplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.applicatioprojectxd.Activity.AllWorkActivity;
import com.example.applicatioprojectxd.Activity.HomeServiceProviderActivity;
import com.example.applicatioprojectxd.Activity.LoginActivity;
import com.example.applicatioprojectxd.Constant.Constant;
import com.example.applicatioprojectxd.OnBoardingAndSplash.OnBoardingOneActivity;
import com.example.applicatioprojectxd.R;


public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        preferences = getSharedPreferences(Constant.FILE, MODE_PRIVATE);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (preferences.getBoolean(Constant.OnBoardingThree, false) &&
                        preferences.getBoolean(Constant.LOGIN_CUSTOMER, false)) {
                    startActivity(new Intent(getBaseContext(), AllWorkActivity.class));

                    Log.d("TAG", "run: " + 1);
                } else if (preferences.getBoolean(Constant.OnBoardingThree, false) &&
                        preferences.getBoolean(Constant.LOGIN_SERVICE_PROVIDER, false)) {
                    Log.d("TAG", "run: " + 2);

                    startActivity(new Intent(getBaseContext(), HomeServiceProviderActivity.class));
                } else if (preferences.getBoolean(Constant.OnBoardingThree, false)) {
                    Log.d("TAG", "run: " + 3);
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));


                } else {
                    Intent intent = new Intent(getBaseContext(),
                            OnBoardingOneActivity.class);
                    startActivity(intent);
                }


                finish();


            }
        });
        thread.start();


    }
}