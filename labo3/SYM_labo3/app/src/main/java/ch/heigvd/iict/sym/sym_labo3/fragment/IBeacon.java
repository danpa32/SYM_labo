package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;

import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.model.BeaconSummary;

/**
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class IBeacon extends Fragment implements BeaconConsumer {
    protected static final String TAG = "Monitoring";

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconManager beaconManager;

    private View view;
    private TextView textView;
    // List of summarry info on Beacon. Use to not recreate all the list of Beacon but just update
    // the info
    private HashMap<String, BeaconSummary> beaconsList;
    private StringBuilder strBuilder;

    public IBeacon() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strBuilder = new StringBuilder();
        beaconsList = new HashMap<>();

        beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());

        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("This app needs location access");
                builder.setMessage("You need to grant this app location access so it can detect beacons");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ibeacon, container, false);

        textView = (TextView) view.findViewById(R.id.beacon_textview);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                // check if there is beacon
                if (collection.size() > 0) {
                    BeaconSummary curr;

                    for (Beacon b : collection) {
                        if (!beaconsList.containsKey(b.getBluetoothName())) {
                            // Create a summary of the beacon
                            curr = new BeaconSummary(
                                    b.getBluetoothName(),
                                    b.getId3().toString(),
                                    b.getId2().toString(),
                                    b.getBluetoothAddress(),
                                    b.getRssi(),
                                    b.getDistance());
                            beaconsList.put(curr.getName(), curr);
                        }
                        else {
                            curr = beaconsList.get(b.getBluetoothName());
                            curr.setDistance(b.getDistance());
                            curr.setRssi(b.getRssi());
                        }
                    }
                    strBuilder = new StringBuilder();
                    for (BeaconSummary b : beaconsList.values()) {
                        strBuilder.append("Name : ").append(b.getName())
                                .append("\nMinor : ").append(b.getMinor())
                                .append("\nMajor : ").append(b.getMajor())
                                .append("\nAddress : ").append(b.getAddress())
                                .append("\nRSSI : ").append(b.getRssi())
                                .append("\nDistance : ").append(b.getDistance())
                                .append("\n\n");
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(strBuilder.toString());
                        }
                    });
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationContext() {
        return this.getContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        this.getContext().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return this.getContext().bindService(intent, serviceConnection, i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                break;
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
