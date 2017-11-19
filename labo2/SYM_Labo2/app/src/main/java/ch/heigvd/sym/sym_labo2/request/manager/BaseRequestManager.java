package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;

import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Base request manager to set the communication event listener.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public abstract class BaseRequestManager implements RequestResult {
    protected CommunicationEventListener listener;

    /**
     * Send the request content to the specified url.
     * @param request The content of the request.
     * @param url The URL of the destination server.
     * @return The content sent to the server.
     * @throws MalformedURLException if the URL is not valid.
     */
    public abstract String sendRequest(String request, String url) throws MalformedURLException;

    /**
     * Set the communication event listener that should fire when a response is received.
     * @param l The communication event listener.
     */
    public void setCommunicationEventListener (CommunicationEventListener l) {
        listener = l;
    }

    @Override
    public void onFinished(String output) {
        listener.handleServerResponse(output);
    }
}
