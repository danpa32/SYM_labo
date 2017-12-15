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
 * Fragment for entering credentials. Need password AND tag to login.
 */
public class CredentialsAndNFC extends Credentials {

    public CredentialsAndNFC() {
        // Required empty public constructor
    }

    @Override
    protected void submitCredential() {
        if (currAuthTag == null || password.length() == 0) {
            Toast.makeText(getContext(), "Missing password or NFC tag!", Toast.LENGTH_LONG).show();
        } else if (!password.getText().toString().equals(DEFAULT_PASS)) {
            Toast.makeText(getContext(), "Wrong password !", Toast.LENGTH_LONG).show();
        } else {
            access.setAsLogged(currAuthTag);
            ((MainActivity)CredentialsAndNFC.this.getActivity()).reopenFragment();
        }
    }
}
