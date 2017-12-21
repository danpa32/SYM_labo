package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.widget.Toast;

import ch.heigvd.iict.sym.sym_labo3.MainActivity;

/**
 * Fragment for entering credentials. Need password OR tag to login.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class CredentialsOrNFC extends Credentials {

    public CredentialsOrNFC() {
        // Required empty public constructor
    }

    @Override
    protected void submitCredential() {
        if (currAuthTag != null || password.getText().toString().equals(DEFAULT_PASS)) {
            access.setAsLogged(currAuthTag);
            ((MainActivity)CredentialsOrNFC.this.getActivity()).reopenFragment();
        } else {
            if (password.length() == 0) {
                Toast.makeText(getContext(), "You need a password or NFC TAG to be logged !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Wrong password !", Toast.LENGTH_LONG).show();
            }
        }
    }
}
