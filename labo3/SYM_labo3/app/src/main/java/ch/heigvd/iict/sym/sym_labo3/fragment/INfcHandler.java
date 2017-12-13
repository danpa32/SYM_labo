package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.nfc.Tag;
import android.nfc.tech.Ndef;

/**
 * Interface for fragment that want to deal with NFC tags.
 */
public interface INfcHandler {
    void onNfcTagDetected(Tag tag);
}
