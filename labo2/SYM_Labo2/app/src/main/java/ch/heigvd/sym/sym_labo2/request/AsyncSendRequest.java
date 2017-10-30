package ch.heigvd.sym.sym_labo2.request;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Daniel on 29.10.2017.
 */
public class AsyncSendRequest extends AsyncTask<Void, String, String> {

    private RequestInfo requestInfo;
    private RequestResult result;

    public AsyncSendRequest(RequestInfo requestInfo, RequestResult result) {
        this.requestInfo = requestInfo;
        this.result = result;
    }

    @Override
    protected String doInBackground(Void... params) {
        String output = null;
        HttpsURLConnection httpsURLConnection = null;
        try {
            // Open the connection
            httpsURLConnection = (HttpsURLConnection) requestInfo.getUrl().openConnection();
            httpsURLConnection.setDoOutput(true);

            // set HTTP request type (DELETE, GET, HEAD, OPTIONS, POST, etc)
            httpsURLConnection.setRequestMethod(requestInfo.getRequestType());

            // set headers
            for (Map.Entry<String, String> header : requestInfo.getHeaders().entrySet()) {
                httpsURLConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            // send data
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpsURLConnection.getOutputStream()));
            writer.write(requestInfo.getBody());
            writer.flush();
            writer.close();

            // get response
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            output = response.toString();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpsURLConnection.disconnect();
        }

        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        this.result.onFinished(result);
    }
}
