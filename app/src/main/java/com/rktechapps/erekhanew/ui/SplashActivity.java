package com.rktechapps.erekhanew.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rktechapps.erekhanew.R;
import com.rktechapps.erekhanew.apputil.AppConfig;

public class SplashActivity extends AppCompatActivity {

    private AppConfig appConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appConfig = new AppConfig(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                validateLogin();
            }
        },2000);
    }
    private void validateLogin()
    {
        if(appConfig.checkLoginStatus())
            startActivity(new Intent(this, DashboardActivity.class));
        else
            startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
