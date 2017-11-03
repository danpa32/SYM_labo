package ch.heigvd.sym.sym_labo2.request.Model;

import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Daniel on 29.10.2017.
 */

public class RequestInfo {
    private URL url;
    private String requestType;
    private Map<String, String> headers;
    private String body;

    public RequestInfo(URL url, String requestType, Map<String, String> headers, String body) {
        this.url = url;
        this.requestType = requestType;
        this.headers = headers;
        this.body = body;
    }

    public URL getUrl() {
        return url;
    }

    public String getRequestType() {
        return requestType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Request Info");
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
