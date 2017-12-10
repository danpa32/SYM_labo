package ch.heigvd.iict.sym.sym_labo3.utils.listener;


import java.util.ArrayList;
import java.util.EventListener;

/**
 * The interface to specify a simple listener.
 *
 */
public interface ICommunicationEventListener extends EventListener {
    /**
     * Handle server response boolean.
     *
     * @param response the response
     * @return boolean if the response has been well processed
     */
    boolean handleServerResponse(ArrayList<String> response);
}
