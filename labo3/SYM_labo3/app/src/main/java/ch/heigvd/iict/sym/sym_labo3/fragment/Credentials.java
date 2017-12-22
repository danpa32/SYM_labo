package ch.heigvd.iict.sym.sym_labo3.fragment;

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

import java.util.List;

import ch.heigvd.iict.sym.sym_labo3.MainActivity;
import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.auth.LoggedAccess;
import ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask;

/**
 * Fragment for entering credentials.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public abstract class Credentials extends Fragment implements INfcHandler {
    protected EditText password;
    protected TextView nfcPassText;
    protected TextView nfcPassDevice;
    protected Button btnSubmit;

    protected LoggedAccess access;
    protected String currAuthTag = null;

    protected final static String DEFAULT_PASS = "test";

    public Credentials() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        access = ((MainActivity)this.getActivity()).getLoggedAccess();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credentials_and_nfc, container, false);

        //Set GUI objects
        password = view.findViewById(R.id.psw);
        nfcPassText = view.findViewById(R.id.nfc_indication_txt);
        nfcPassDevice = view.findViewById(R.id.nfc_and_pass_device);
        btnSubmit = view.findViewById(R.id.button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCredential();
            }
        });

        // Check if NFC is enabled
        if (!NfcAdapter.getDefaultAdapter(getActivity()).isEnabled()) {
            nfcPassText.setText(R.string.nfc_disabled_txt);
        } else {
            nfcPassText.setText(R.string.nfc_indication);
        }

        return view;
    }

    abstract protected void submitCredential();

    /**
     * Read the detected tag in the background and show the result.
     * @param tag The detected tag.
     */
    @Override
    public void onNfcTagDetected(Tag tag) {
        new NdefReaderTask(new NdefReaderTask.IOnResult() {
            @Override
            public void handleResult(List<String> result) {
                if(result.isEmpty()) {
                    Toast.makeText(getActivity(), "TAG is empty", Toast.LENGTH_SHORT).show();
                } else {
                    currAuthTag = result.get(0);
                    nfcPassDevice.setText("Auth TAG : " + currAuthTag);
                }
            }
        }).execute(tag);
    }
}
