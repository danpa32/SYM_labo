package ch.heigvd.sym.sym_labo2.request;

/**
 * Interface to allow the interception of the response of a request.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public interface RequestResult {
    /**
     * Should be called with the response when it is received.
     * @param output The content of the response.
     */
    void onFinished(String output);
}
