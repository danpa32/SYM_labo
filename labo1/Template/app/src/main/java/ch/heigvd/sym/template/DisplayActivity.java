package ch.heigvd.sym.template;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = DisplayActivity.class.getSimpleName();
    private static final int REQUEST_READ_PHONE_STATE = 999;

    private TextView email = null;
    private TextView imei = null;
    private ImageView image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        Intent intent = getIntent();

        this.email = (TextView) findViewById(R.id.email);
        email.setText(intent.getStringExtra("emailEntered"));

        this.imei = (TextView) findViewById(R.id.imei);
        imei.setText(System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

        this.image = (ImageView) findViewById(R.id.image);
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filename = "perso.jpg";
        File imageFile = new File(folder + File.separator + filename);
        image.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
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
