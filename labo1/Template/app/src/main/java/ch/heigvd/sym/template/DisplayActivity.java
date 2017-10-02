package ch.heigvd.sym.template;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = DisplayActivity.class.getSimpleName();
    private static final int REQUEST_READ_PHONE_STATE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        Intent intent = getIntent();

        final TextView email = (TextView) findViewById(R.id.email);
        final TextView imei = (TextView) findViewById(R.id.imei);

        email.setText(intent.getStringExtra("emailEntered"));
        imei.setText(System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart called");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume called");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart called");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy called");
        super.onDestroy();
    }
}
