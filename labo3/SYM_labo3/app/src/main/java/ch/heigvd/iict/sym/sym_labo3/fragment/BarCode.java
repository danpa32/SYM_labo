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

public class BarCode extends Fragment {

    private Button scan1D = null;
    private Button scan2D = null;
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

        scan1D = (Button) view.findViewById(R.id.scan1D);
        scan2D = (Button) view.findViewById(R.id.scan2D);
        resultScan = (TextView) view.findViewById(R.id.scan_result);

        scan1D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(BarCode.this);
                integrator.initiateScan(IntentIntegrator.ONE_D_CODE_TYPES);
            }
        });

        scan2D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(BarCode.this);
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
