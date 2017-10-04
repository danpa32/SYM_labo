package ch.heigvd.sym.template;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.System;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_PHONE_STATE);
        } else {
            setAvatarImage();
        }
    }

    private void setAvatarImage() {
        this.image = (ImageView) findViewById(R.id.image);
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String filename = "perso.jpg";
        File imageFile = new File(folder + File.separator + filename);
        if(imageFile.canRead()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            image.setImageBitmap(bitmap);
            //image.setImageURI(Uri.fromFile(imageFile));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setAvatarImage();
                }
                return;
            }
        }
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
