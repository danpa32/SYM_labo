package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.heigvd.iict.sym.sym_labo3.R;

/**
 * Fragment responsible for reading of code bar and displaying the information contained in it.
 * Use the zxing library.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class BarCode extends Fragment {

    // Button used to activate the 1D barcode scanner.
    private Button scan1D = null;

    // Button used to activate the 2D barcode scanner.
    private Button scan2D = null;

    // Content of the barcode.
    private TextView resultScan = null;

    public BarCode() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_code, container, false);

        scan1D = view.findViewById(R.id.scan1D);
        scan2D = view.findViewById(R.id.scan2D);
        resultScan = view.findViewById(R.id.scan_result);

        scan1D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(BarCode.this);
                integrator.setBeepEnabled(false);
                integrator.initiateScan(IntentIntegrator.ONE_D_CODE_TYPES);
            }
        });

        scan2D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(BarCode.this);
                integrator.setBeepEnabled(false);
                integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });

        return view;
    }

    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "No content", Toast.LENGTH_LONG).show();
            } else {
                resultScan.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
