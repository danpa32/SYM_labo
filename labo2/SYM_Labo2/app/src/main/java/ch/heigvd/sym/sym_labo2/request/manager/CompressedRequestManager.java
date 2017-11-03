package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import ch.heigvd.sym.sym_labo2.request.AsyncSendRequest;
import ch.heigvd.sym.sym_labo2.request.RequestInfo;
import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Created by daniel on 30.10.17.
 */

public class CompressedRequestManager extends BaseRequestManager implements RequestResult {
    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("X-Network", "CSD");
        headers.put("X-Content-Encoding", "deflate");

        RequestInfo requestInfo = new RequestInfo(new URL(url), "POST", headers, request);

        new AsyncSendRequest(requestInfo, this).execute();

        System.out.println(requestInfo.toString());
        return requestInfo.toString();
    }

    @Override
    public void onFinished(String output) {
        listener.handleServerResponse(output);
    }
}
