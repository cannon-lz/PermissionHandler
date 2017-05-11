package com.zhangly.app;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.permission.library.annotation.Complete;
import com.permission.library.annotation.Permissions;
import com.permission.library.annotation.Refused;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvTest = (TextView) findViewById(R.id.tv_test);
        mTvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera("", v);
            }
        });
    }

    @Permissions(value = {Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, isShowRationale = true)
    public void openCamera(String arg1, View view) {
        Toast.makeText(MainActivity.this, "open camera", Toast.LENGTH_SHORT).show();
    }

    @Refused
    public void onRefused(List<String> refusedPermissions) {
        Toast.makeText(this, String.format("open camera refused %s", Arrays.toString(refusedPermissions.toArray())), Toast.LENGTH_SHORT).show();
    }

    @Complete
    public void onComplete() {
        Toast.makeText(this, "open camera complete", Toast.LENGTH_SHORT).show();
    }

}
