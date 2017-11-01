package ch.heigvd.sym.sym_labo2.request.manager;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.heigvd.sym.sym_labo2.request.AsyncSendRequest;
import ch.heigvd.sym.sym_labo2.request.RequestInfo;
import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Created by daniel on 30.10.17.
 */

public class DeferredRequestManager extends BaseRequestManager implements RequestResult {
    RequestInfo requestInfo;

    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");

        requestInfo = new RequestInfo(new URL(url), "POST", headers, request);

        new AsyncSendRequest(requestInfo, this).execute();

        return "All is good";
    }

    @Override
    public void onFinished(String output) {
        if (output == null) {
            final DeferredRequestManager manager = this;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new AsyncSendRequest(requestInfo, manager).execute();
                }
            }, 10000, 5000);
        }
    }
}
