package ch.heigvd.sym.sym_labo2.request.manager;

import java.net.MalformedURLException;

import ch.heigvd.sym.sym_labo2.request.RequestResult;

/**
 * Created by daniel on 30.10.17.
 */

public class DeferredRequestManager extends BaseRequestManager implements RequestResult {
    @Override
    public String sendRequest(String request, String url) throws MalformedURLException {
        return null;
    }

    @Override
    public void onFinished(String output) {

    }
}
