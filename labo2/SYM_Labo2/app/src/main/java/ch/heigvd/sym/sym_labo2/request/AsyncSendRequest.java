package ch.heigvd.sym.sym_labo2.request;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HttpsURLConnection;

import ch.heigvd.sym.sym_labo2.Utils.CompressRequest;

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

            // For compressed request
            boolean compressedRequest = false;
            // set headers
            for (Map.Entry<String, String> header : requestInfo.getHeaders().entrySet()) {
                if (header.getKey().equals("X-Content-Encoding")) {
                    compressedRequest = true;
                }
                httpsURLConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            if (compressedRequest) {
                byte[] bytes = requestInfo.getBody().getBytes("UTF-8");
                bytes = CompressRequest.compress(bytes);
            } else {
                // send data
                PrintWriter writer = new PrintWriter(
                                        new OutputStreamWriter(httpsURLConnection.getOutputStream()));
                writer.write(requestInfo.getBody());
                writer.flush();
                writer.close();
            }

            // get response
            BufferedReader reader = null;
            String line;
            StringBuilder response = new StringBuilder();
            if (compressedRequest) {
                Inflater inflater = new Inflater(true);
                reader = new BufferedReader(
                            new InputStreamReader(
                                new InflaterInputStream(httpsURLConnection.getInputStream(), inflater)));
            } else {
                reader = new BufferedReader(
                            new InputStreamReader(httpsURLConnection.getInputStream()));
            }

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\n");
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
