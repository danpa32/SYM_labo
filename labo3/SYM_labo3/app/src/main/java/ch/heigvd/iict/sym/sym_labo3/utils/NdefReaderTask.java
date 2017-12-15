package ch.heigvd.iict.sym.sym_labo3.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Background task for reading the data from a tag. Do not block the UI thread while reading.
 *
 * @author Ralf Wondratschek (orignal)
 *
 */
public class NdefReaderTask extends AsyncTask<Tag,Void,List<String>> {
    private IOnResult resultHandler;

    /**
     * Constructor.
     * @param resultHandler The handler which will receive the result of the task.
     */
    public NdefReaderTask(IOnResult resultHandler) {
        this.resultHandler = resultHandler;
    }

    /**
     * Read the content of the tag in the background.
     * @param params The tag which content should be read. (Only the first element of the array is used)
     * @return The list of content.
     */
    @Override
    protected List<String> doInBackground(Tag... params) {
        Tag tag = params[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return null;
        }

        ArrayList<String> results = null;
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();

            results = new ArrayList<>();
            for (NdefRecord ndefRecord : ndefMessage.getRecords()) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    results.add(readText(ndefRecord));
                }
            }

            ndef.close();
        } catch (IOException | FormatException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Read the content of NDEF text tag.
     * @param record The NDEF record.
     * @return The content of the record as a string.
     * @throws UnsupportedEncodingException if the encoding is not supported.
     */
    private static String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

    @Override
    protected void onPostExecute(List<String> result) {
        resultHandler.handleResult(result);
    }

    /**
     * Start listening for NFC tag.
     * @param activity The activity that should listen.
     * @param adapter The adapter used to listen for NFC tag.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            System.err.println("MalformedMimeTypeException");
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * Stop listening for NFC tag.
     * @param activity The activity that should stop listening.
     * @param adapter The adapter used to listen for NFC tag.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    /**
     * Interface to handle the result of the reader.
     */
    public interface IOnResult {
        void handleResult(List<String> result);
    }
}
