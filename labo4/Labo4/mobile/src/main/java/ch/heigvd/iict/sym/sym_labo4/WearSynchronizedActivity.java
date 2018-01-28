package ch.heigvd.iict.sym.sym_labo4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import ch.heigvd.iict.sym.wearcommon.Constants;

/**
 * This activity will be listening to data sent by the wearable. Depending on the data received, the
 * background's color will change.
 *
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class WearSynchronizedActivity extends AppCompatActivity implements DataClient.OnDataChangedListener {

    private static final String TAG = WearSynchronizedActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearsynchronized);

        // Register as a listener to the data sent by the wearable
        Wearable.getDataClient(getApplicationContext()).addListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register as a listener to the data sent by the wearable
        Wearable.getDataClient(getApplicationContext()).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister as a listener to the data sent by the wearable
        Wearable.getDataClient(getApplicationContext()).removeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Unregister as a listener to the data sent by the wearable
        Wearable.getDataClient(getApplicationContext()).removeListener(this);
    }

    /**
     * Method used to update the background color of the activity
     * @param r The red composant (0...255)
     * @param g The green composant (0...255)
     * @param b The blue composant (0...255)
     */
    private void updateColor(int r, int g, int b) {
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(Color.argb(255, r,g,b));
    }

    /**
     * Method called when data change, read the color and update the background's color.
     * @param dataEventBuffer Data source buffer
     */
    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = dataEvent.getDataItem();

                // Update only if color has changed
                if (dataItem.getUri().getPath().compareTo(Constants.PATH_COLORS) == 0) {
                    DataMap map = DataMapItem.fromDataItem(dataItem).getDataMap();
                    updateColor(map.getInt(Constants.COLORS[0]),
                            map.getInt(Constants.COLORS[1]),
                            map.getInt(Constants.COLORS[2]));
                }
            }
        }
    }
}
