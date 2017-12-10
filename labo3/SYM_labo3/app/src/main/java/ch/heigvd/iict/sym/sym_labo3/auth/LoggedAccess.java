package ch.heigvd.iict.sym.sym_labo3.auth;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ch.heigvd.iict.sym.sym_labo3.MainActivity;
import ch.heigvd.iict.sym.sym_labo3.R;
import ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask;
import ch.heigvd.iict.sym.sym_labo3.utils.TagRequest;
import ch.heigvd.iict.sym.sym_labo3.utils.listener.ICommunicationEventListener;

import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.setupForegroundDispatch;
import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.stopForegroundDispatch;

public class LoggedAccess extends AppCompatActivity {
    private TextView title = null;
    private Button btnAuthMax = null;
    private Button btnAuthMedium = null;
    private Button btnAuthLow = null;
    private TextView securityText = null;
    private TextView resultText = null;

    private NfcAdapter nfcAdapter = null;

    private static final int HIGH_AUTH = 10;
    private static final int MEDIUM_AUTH = 7;
    private static final int LOW_AUTH = 4;
    private static final int NO_AUTH = 0;
    private int MY_SECURITY_LEVEL = 0;

    private static final int HIGH_ACCESS = 3;
    private static final int MEDIUM_ACCESS = 2;
    private static final int LOW_ACCESS = 1;
    private static final int NO_ACCESS = 0;

    private String currAuthTag = null;
    private long tAuthStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_access);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.title_logged);
        btnAuthMax = (Button) findViewById(R.id.btn_auth_max);
        btnAuthMedium = (Button) findViewById(R.id.btn_auth_medium);
        btnAuthLow = (Button) findViewById(R.id.btn_auth_low);
        securityText = (TextView) findViewById(R.id.security_logged);
        resultText = (TextView) findViewById(R.id.result_logged);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            currAuthTag = b.getString("AUTH_TAG");
            if (currAuthTag != null) {
                securityText.setText("No TAG has been entered !");
            }
        }
        tAuthStart = System.currentTimeMillis();
        MY_SECURITY_LEVEL = HIGH_AUTH;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        btnAuthMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSecurityLevel() >= HIGH_ACCESS) {
                    resultText.setText("Access granted to high secure data");
                } else {
                    resultText.setText("Your access level is not high enough. Please refresh your security or get a promotion from your superior");
                }
            }
        });

        btnAuthMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSecurityLevel() >= MEDIUM_ACCESS) {
                    resultText.setText("Access granted to medium secure data");
                } else {
                    resultText.setText("Your access level is not high enough. Please refresh your security or get a promotion from your superior");
                }
            }
        });

        btnAuthLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSecurityLevel() >= LOW_ACCESS) {
                    resultText.setText("Access granted to low secure data");
                } else {
                    resultText.setText("Your access level is not high enough. Please refresh your security or get a promotion from your superior");
                }
            }
        });
    }

    private int getSecurityLevel() {
        long tAuthAccess = System.currentTimeMillis();
        long tDelta = tAuthAccess - tAuthStart;
        double timeAuth = (tDelta / 1000.0) / 60.0; // in minutes

        MY_SECURITY_LEVEL -= (int) timeAuth;

        if (MY_SECURITY_LEVEL <= HIGH_AUTH && MY_SECURITY_LEVEL > MEDIUM_AUTH) {
            return HIGH_ACCESS;
        } else if (MY_SECURITY_LEVEL <= MEDIUM_AUTH && MY_SECURITY_LEVEL > LOW_AUTH) {
            return MEDIUM_ACCESS;
        } else if (MY_SECURITY_LEVEL <= LOW_AUTH && MY_SECURITY_LEVEL > NO_AUTH) {
            return LOW_ACCESS;
        } else {
            return NO_ACCESS;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupForegroundDispatch(this, nfcAdapter);
    }

    private void handleIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }
        new NdefReaderTask().execute(new TagRequest(tag, new ICommunicationEventListener() {
            @Override
            public boolean handleServerResponse(ArrayList<String> response) {
                if (currAuthTag == null) {
                    currAuthTag = response.get(1);
                }

                if (response.get(1).equals(currAuthTag)) {
                    tAuthStart = System.currentTimeMillis();
                    securityText.setText("Your security TAG has been updated !");
                } else {
                    securityText.setText("Cannot update security level ! You're TAG is not wrong or unreadable.");
                }
                return true;
            }
        }));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            stopForegroundDispatch(this, nfcAdapter);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
