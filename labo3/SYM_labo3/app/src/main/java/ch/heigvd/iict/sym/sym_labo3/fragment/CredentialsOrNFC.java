package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.auth.LoggedAccess;
import ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask;
import ch.heigvd.iict.sym.sym_labo3.utils.TagRequest;
import ch.heigvd.iict.sym.sym_labo3.utils.listener.ICommunicationEventListener;

import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.setupForegroundDispatch;
import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.stopForegroundDispatch;

public class CredentialsOrNFC extends Fragment {
    private EditText password;
    private TextView nfcPassText;
    private TextView nfcPassDevice;
    private Button btnSubmit;

    private NfcAdapter nfcAdapter;

    private String currAuthTag;
    private final String TAG = this.getClass().getName();

    private final static String DEFAULT_PASS = "test";

    public CredentialsOrNFC() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this.getContext());

        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this.getContext(), "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credentials_and_nfc, container, false);

        //Set GUI objects
        password = (EditText) view.findViewById(R.id.psw);
        nfcPassText = (TextView) view.findViewById(R.id.nfc_indication_txt);
        nfcPassDevice = (TextView) view.findViewById(R.id.nfc_and_pass_device);
        btnSubmit = (Button) view.findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currAuthTag == null) {
                    if (password.length() == 0) {
                        Toast.makeText(getContext(), "You need a password or NFC TAG to be logged !", Toast.LENGTH_LONG).show();
                    } else if (!password.getText().toString().equals(DEFAULT_PASS)) {
                        Toast.makeText(getContext(), "Wrong password !", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getActivity().getBaseContext(), LoggedAccess.class);
                        Bundle b = new Bundle();
                        b.putString("AUTH_TAG", null);
                        intent.putExtras(b);
                        startActivity(intent);
                        getActivity().finish();
                    }
                } else {
                    Intent intent = new Intent(getActivity().getBaseContext(), LoggedAccess.class);
                    Bundle b = new Bundle();
                    b.putString("AUTH_TAG", currAuthTag);
                    intent.putExtras(b);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        if (!nfcAdapter.isEnabled()) {
            nfcPassText.setText(R.string.nfc_disabled_txt);
        } else {
            nfcPassText.setText(R.string.nfc_indication);
        }

        return view;
    }

    public void handleIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            if (currAuthTag == null) {
                nfcPassDevice.setText(R.string.no_nfc_tag_txt);
                return;
            }
        }
        new NdefReaderTask().execute(new TagRequest(tag, new ICommunicationEventListener() {
            @Override
            public boolean handleServerResponse(ArrayList<String> response) {
                currAuthTag = response.get(1);
                nfcPassDevice.setText("Auth TAG : " + currAuthTag);
                return true;
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        setupForegroundDispatch(this.getActivity(), nfcAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            stopForegroundDispatch(this.getActivity(), nfcAdapter);
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

