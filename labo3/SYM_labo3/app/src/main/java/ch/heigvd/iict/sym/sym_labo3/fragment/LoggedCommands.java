package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.heigvd.iict.sym.sym_labo3.MainActivity;
import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.auth.LoggedAccess;
import ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask;

/**
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class LoggedCommands extends Fragment implements INfcHandler {

    private TextView title = null;
    private Button btnAuthMax = null;
    private Button btnAuthMedium = null;
    private Button btnAuthLow = null;
    private Button btnAuthDisconnect = null;
    private Button btnRefresh = null;
    private TextView securityLvlText = null;
    private TextView securityText = null;
    private TextView resultText = null;

    private LoggedAccess access;

    public LoggedCommands() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        access = ((MainActivity)this.getActivity()).getLoggedAccess();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logged_commands, container, false);

        title = view.findViewById(R.id.title_logged);
        btnAuthMax = view.findViewById(R.id.btn_auth_max);
        btnAuthMedium = view.findViewById(R.id.btn_auth_medium);
        btnAuthLow = view.findViewById(R.id.btn_auth_low);
        btnAuthDisconnect = view.findViewById(R.id.btn_auth_disconnect);
        btnRefresh = view.findViewById(R.id.btn_refresh_auth_lvl);
        securityLvlText = view.findViewById(R.id.security_lvl);
        securityText = view.findViewById(R.id.security_logged);
        resultText = view.findViewById(R.id.result_logged);

        btnAuthMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (access.getSecurityLevel() >= LoggedAccess.HIGH_AUTH) {
                    resultText.setText("Access granted to high secure data");
                } else {
                    resultText.setText(R.string.unsufficient_auth_lvl);
                }
            }
        });

        btnAuthMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (access.getSecurityLevel() >= LoggedAccess.MEDIUM_AUTH) {
                    resultText.setText("Access granted to medium secure data");
                } else {
                    resultText.setText(R.string.unsufficient_auth_lvl);
                }
            }
        });

        btnAuthLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (access.getSecurityLevel() >= LoggedAccess.LOW_AUTH) {
                    resultText.setText("Access granted to low secure data");
                } else {
                    resultText.setText(R.string.unsufficient_auth_lvl);
                }
            }
        });

        btnAuthDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                access.setAsUnlogged();
                ((MainActivity)LoggedCommands.this.getActivity()).reopenFragment();
            }
        });

        refreshSecurityLevelInfo();
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshSecurityLevelInfo();
            }
        });

        return view;
    }

    /**
     * Refresh the information about the security level of the user.
     */
    private void refreshSecurityLevelInfo() {
        securityLvlText.setText("Last seen security level is " + access.getSecurityLevel());
    }

    @Override
    public void onNfcTagDetected(Tag tag) {
        new NdefReaderTask(new NdefReaderTask.IOnResult() {
            @Override
            public void handleResult(List<String> result) {
                if(result.isEmpty()) {
                    Toast.makeText(getActivity(), "TAG is empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (access.updateAuth(result.get(0))) {
                        refreshSecurityLevelInfo();
                        securityText.setText("Your security TAG has been updated !");
                    } else {
                        securityText.setText("Cannot update security level ! You're TAG does not correspond to the session.");
                    }
                }
            }
        }).execute(tag);
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
