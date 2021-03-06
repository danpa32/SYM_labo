package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import ch.heigvd.sym.sym_labo2.request.AsyncSendRequest;
import ch.heigvd.sym.sym_labo2.request.Model.RequestInfo;
import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Request manager to send XML content.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class XMLRequestManager extends BaseRequestManager {

    private static final Logger LOG = Logger.getLogger(XMLRequestManager.class.getSimpleName());

    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");

        RequestInfo requestInfo = new RequestInfo(new URL(url), "POST", headers, request);

        new AsyncSendRequest(requestInfo, this).execute();

        LOG.info(requestInfo.toString());
        return requestInfo.toString();
    }
}
