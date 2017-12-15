package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.a3dcompassapp.OpenGLRenderer;


public class Compass extends Fragment implements SensorEventListener {
    // OpenGL
    private OpenGLRenderer openGLRenderer = null;
    private GLSurfaceView m3DView = null;

    // Sensors
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor geomagneticSensor;

    private float[] gravityVector = {0, 0, 0};
    private float[] geomagneticVector = {0, 0, 0};
    private float[] rotationMatrix = {0, 0, 0};

    public Compass() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize sensors
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        geomagneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, geomagneticSensor, SensorManager.SENSOR_DELAY_GAME);

        // we need fullscreen
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // We create the 3D renderer
        this.openGLRenderer = new OpenGLRenderer(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Link to GUI
        this.m3DView = view.findViewById(R.id.compass_opengl);

        // Init OpenGL surface view
        this.m3DView.setRenderer(this.openGLRenderer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Read value from the sensor
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravityVector = sensorEvent.values;
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagneticVector = sensorEvent.values;
        }

        // Update the rotation matrix
        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravityVector, geomagneticVector)) {
            rotationMatrix = openGLRenderer.swapRotMatrix(rotationMatrix);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
