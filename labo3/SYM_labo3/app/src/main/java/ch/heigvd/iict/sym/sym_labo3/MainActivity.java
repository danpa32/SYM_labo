package ch.heigvd.iict.sym.sym_labo3;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ch.heigvd.iict.sym.sym_labo3.auth.LoggedAccess;
import ch.heigvd.iict.sym.sym_labo3.fragment.BarCode;
import ch.heigvd.iict.sym.sym_labo3.fragment.Compass;
import ch.heigvd.iict.sym.sym_labo3.fragment.CredentialsAndNFC;
import ch.heigvd.iict.sym.sym_labo3.fragment.CredentialsOrNFC;
import ch.heigvd.iict.sym.sym_labo3.fragment.Home;
import ch.heigvd.iict.sym.sym_labo3.fragment.IBeacon;
import ch.heigvd.iict.sym.sym_labo3.fragment.INfcHandler;
import ch.heigvd.iict.sym.sym_labo3.fragment.LoggedCommands;

import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.setupForegroundDispatch;
import static ch.heigvd.iict.sym.sym_labo3.utils.NdefReaderTask.stopForegroundDispatch;

/**
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private int selectedNavItem;

    private NfcAdapter mNfcAdapter;

    private LoggedAccess loggedAccess = new LoggedAccess();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    /**
     * Reopen and recreate the last selected fragment.
     */
    public void reopenFragment() {
        displaySelectedScreen(selectedNavItem);
    }

    /**
     * Display the fragment associated with the itemId.
     * @param itemId The id of the nav element.
     */
    private void displaySelectedScreen(int itemId) {
        fragment = null;
        selectedNavItem = itemId;

        switch (itemId) {
            case R.id.nav_nfc_and_password:
                if(loggedAccess.getSecurityLevel() == LoggedAccess.NO_AUTH) {
                    fragment = new CredentialsAndNFC();
                } else {
                    fragment = new LoggedCommands();
                }
                break;
            case R.id.nav_nfc_or_password:
                if(loggedAccess.getSecurityLevel() == LoggedAccess.NO_AUTH) {
                    fragment = new CredentialsOrNFC();
                } else {
                    fragment = new LoggedCommands();
                }
                break;
            case R.id.nav_bar_code:
                fragment = new BarCode();
                break;
            case R.id.nav_ibeacon:
                fragment = new IBeacon();
                break;
            case R.id.nav_compass:
                fragment = new Compass();
                break;
            case R.id.nav_home:
                fragment = new Home();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // Recuperate the NFC tag.
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if(tag != null) {
            Toast.makeText(this, getString(R.string.tag_detected), Toast.LENGTH_SHORT).show();

            if (fragment instanceof INfcHandler) {
                ((INfcHandler) fragment).onNfcTagDetected(tag);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start listening for a NFC tag.
        if(mNfcAdapter!= null)
            setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop listening for a NFC tag.
        if (mNfcAdapter != null) {
            stopForegroundDispatch(this, mNfcAdapter);
        }
    }

    /**
     * Get security informations about authorization and login.
     * @return A LoggedAccess containing the informations.
     */
    public LoggedAccess getLoggedAccess() {
        return loggedAccess;
    }
}
