package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ch.heigvd.iict.sym.sym_labo3.R;

public class CredentialsAndNFC extends Fragment {
    private View view;
    private EditText password;
    private TextView nfcPassText;
    private TextView nfcPassDevice;
    private Button btnSubmit;

    private NfcAdapter nfcAdapter;

    private String currAuthTag;
    private final String TAG = this.getTag();

    private OnFragmentInteractionListener mListener;

    public CredentialsAndNFC() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this.getContext());

        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this.getContext(), "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_credentials_and_nfc, container, false);

        //Set GUI objects
        password = (EditText) view.findViewById(R.id.psw);
        nfcPassText = (TextView) view.findViewById(R.id.nfc_indication_txt);
        nfcPassDevice = (TextView) view.findViewById(R.id.nfc_and_pass_device);
        btnSubmit = (Button) view.findViewById(R.id.button);

        if (!nfcAdapter.isEnabled()) {
            nfcPassText.setText("NFC is disabled.");
        } else {
            nfcPassText.setText(R.string.nfc_indication);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO -> do something
            }
        });

        handleIntent(this.getActivity().getIntent());

        return view;
    }

    private void handleIntent(Intent intent) {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
