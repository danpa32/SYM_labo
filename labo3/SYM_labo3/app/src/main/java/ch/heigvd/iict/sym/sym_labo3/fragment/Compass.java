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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.a3dcompassapp.OpenGLRenderer;

/**
 * This class displays a fragment containing a compass, represented by a 3D arrow.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class Compass extends Fragment implements SensorEventListener {
    /**
     * This constant is a threshold used by the low-pass filter to avoid small variations.
     */
    private static final float ALPHA = 0.8f;

    // OpenGL
    private OpenGLRenderer openGLRenderer = null;
    private GLSurfaceView m3DView = null;

    // Sensors
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor geomagneticSensor;

    private float[] gravityVector = {0, 0, 0};
    private float[] geomagneticVector = {0, 0, 0};

    // Use the identity rotation matrix as default rotation matrix
    private float[] rotationMatrix = {
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f};

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

        if (accelerometerSensor == null || geomagneticSensor == null) {
            Log.e("Compass", "Error, all sensor aren't available");
        }

        // We create the 3D renderer
        this.openGLRenderer = new OpenGLRenderer(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();

        // This class is a SensorEventListener, it can listen to sensors events
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, geomagneticSensor, SensorManager.SENSOR_DELAY_GAME);
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

    /**
     * This low-pass filter is used to filter low variations on a vector
     */
    private float[] lowPassFilter(float[] input, float[] output) {
        if (output == null) return input;

        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }

        return output;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Check if accuracy is sufficient
        if (sensorEvent.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            Log.i("Compass", "Accuracy is bad");
            return;
        }

        // Read value from the sensor
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Apply the low-pass filter
            gravityVector = lowPassFilter(sensorEvent.values, gravityVector);
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // Apply the low-pass filter
            geomagneticVector = lowPassFilter(sensorEvent.values, geomagneticVector);
        }

        // Calculate and update the rotation matrix
        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravityVector, geomagneticVector)) {
            rotationMatrix = openGLRenderer.swapRotMatrix(rotationMatrix);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Do nothing
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, geomagneticSensor);
        sensorManager.unregisterListener(this, accelerometerSensor);
    }
}
