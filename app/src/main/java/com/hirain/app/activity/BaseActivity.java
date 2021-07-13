package com.hirain.app.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
//    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        intent = new Intent(this, NetWorkService.class);
//        startService(intent);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(intent);
    }

}
