package com.example.kazehaya.daws;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


public class StartScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
    }

    public void startApp(View view) {
        Intent intent = new Intent(this, MainCameraView.class);
        startActivity(intent);
        finish();
    }

    public void calibrate(View view) {
        //start Calibrate Activity
    }
}

