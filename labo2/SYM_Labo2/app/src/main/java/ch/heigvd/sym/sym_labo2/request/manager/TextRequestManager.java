package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import ch.heigvd.sym.sym_labo2.request.AsyncSendRequest;
import ch.heigvd.sym.sym_labo2.request.Model.RequestInfo;
import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Created by Daniel on 29.10.2017.
 */
public class TextRequestManager extends BaseRequestManager {

    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");

        RequestInfo requestInfo = new RequestInfo(new URL(url), "POST", headers, request);

        new AsyncSendRequest(requestInfo, this).execute();

        System.out.println(requestInfo.toString());
        return requestInfo.toString();
    }
}
