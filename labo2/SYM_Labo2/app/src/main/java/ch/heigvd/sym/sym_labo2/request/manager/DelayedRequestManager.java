package ch.heigvd.sym.sym_labo2.request.manager;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import ch.heigvd.sym.sym_labo2.request.AsyncSendRequest;
import ch.heigvd.sym.sym_labo2.request.Model.RequestInfo;

/**
 * Created by daniel on 30.10.17.
 * <p>
 * TODO -> Création d'une file d'attente
 */

public class DelayedRequestManager extends BaseRequestManager {
    private RequestInfo requestInfo;
    private Queue<RequestInfo> pendingRequests;
    private CustomTimerTask timerTask;

    public DelayedRequestManager() {
        super();
        pendingRequests = new LinkedList<>();
    }

    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");

        requestInfo = new RequestInfo(new URL(url), "POST", headers, request);

        new AsyncSendRequest(requestInfo, this).execute();

        return requestInfo.toString();
    }

    @Override
    public void onFinished(String output) {
        if (output == null) {
            Log.i("DelayedRequest", "Request failed");

            /*
             When the request hasn't been processed, add it to the waiting queue and schedule the
             queue processing if not already done.
              */
            if (timerTask == null || !timerTask.scheduled) {
                timerTask = new CustomTimerTask();
                new Timer().schedule(timerTask, 5000);
            }

            pendingRequests.add(requestInfo);
            Log.i("DelayedRequest", "Request queued");
            displayQueueState();
        } else {
            Log.i("DelayedRequest", "Request processed");
            displayQueueState();
            listener.handleServerResponse(output);
            processNextWaitingRequest();
        }
    }

    private void processNextWaitingRequest() {
        if (!pendingRequests.isEmpty()) {
            final DelayedRequestManager manager = this;
            new AsyncSendRequest(pendingRequests.remove(), manager).execute();
            Log.i("DelayedRequest", "Processing request");
        }
    }

    private void displayQueueState() {
        String display = "<";

        int queueSize = pendingRequests.size();
        for (int i = 0; i < queueSize; i++) {
            display += "R";
            if (i < queueSize - 1) {
                display += ",";
            }
        }

        display += ">";
        Log.i("DelayedRequest", "Queue state : " + display);
    }

    private class CustomTimerTask extends TimerTask {
        private boolean scheduled = true;

        @Override
        public void run() {
            processNextWaitingRequest();
            scheduled = false;
        }
    }
}
