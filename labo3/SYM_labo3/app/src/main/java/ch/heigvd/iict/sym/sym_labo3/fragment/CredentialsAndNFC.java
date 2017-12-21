package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.widget.Toast;

import ch.heigvd.iict.sym.sym_labo3.MainActivity;

/**
 * Fragment for entering credentials. Need password AND tag to login.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
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
