package ch.heigvd.sym.sym_labo2.request.Model;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * The content of the body and header of a request.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class RequestInfo {
    private URL url;
    private String requestType;
    private Map<String, String> headers;
    private String body;

    /**
     * Constructor
     * @param url The URL of the destination.
     * @param requestType The type of the HTTP request (e.g. GET, POST, PUT, PATCH, DELETE).
     * @param headers The headers of the request.
     * @param body The body of the request.
     */
    public RequestInfo(URL url, String requestType, Map<String, String> headers, String body) {
        this.url = url;
        this.requestType = requestType;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Get the URL of the request.
     * @return The URL as an URL object.
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Get the request HTTP type.
     * @return The type (e.g. GET, POST, PUT, PATCH, DELETE);
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Get the headers of the request.
     * @return The headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Get the body of the request.
     * @return The body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Get a textual representation of the request.
     * @return The textual representation.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Request Info: ");
        str.append(requestType);
        str.append(" ");
        str.append(url);
        str.append("\n");
        for (Map.Entry<String, String> entry : headers.entrySet()){
            str.append(entry.getKey());
            str.append(" : ");
            str.append(entry.getValue());
            str.append("\n");
        }
        str.append("\n");
        str.append(body);

        return str.toString();
    }
}
