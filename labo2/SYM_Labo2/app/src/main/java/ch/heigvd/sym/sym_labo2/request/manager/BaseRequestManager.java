package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;

import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;

/**
 * Created by Daniel on 29.10.2017.
 */

public abstract class BaseRequestManager {
    protected CommunicationEventListener listener;

    public abstract String sendRequest(String request, String url) throws MalformedURLException;

    public void setCommunicationEventListener (CommunicationEventListener l) {
        listener = l;
    }
}
