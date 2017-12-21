package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.nfc.Tag;

/**
 * Interface for fragment that want to deal with NFC tags.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public interface INfcHandler {
    void onNfcTagDetected(Tag tag);
}
