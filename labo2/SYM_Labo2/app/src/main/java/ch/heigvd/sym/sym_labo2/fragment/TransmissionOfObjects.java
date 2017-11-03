package ch.heigvd.sym.sym_labo2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Logger;

import ch.heigvd.sym.sym_labo2.R;
import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.manager.JSONRequestManager;
import ch.heigvd.sym.sym_labo2.request.manager.XMLRequestManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransmissionOfObjects extends Fragment implements View.OnClickListener{

    private static final Logger log = Logger.getLogger(TransmissionOfObjects.class.getSimpleName());

    private View view;
    private TextView textView;
    private JSONRequestManager managerJson;
    private XMLRequestManager managerXml;

    public TransmissionOfObjects() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transmission_of_objects, container, false);

        final Button jsonButton = (Button) view.findViewById(R.id.bJSON);
        final Button xmlButton = (Button) view.findViewById(R.id.bXML);
        textView = (TextView) view.findViewById(R.id.textInfoObject);
        jsonButton.setOnClickListener(this);
        xmlButton.setOnClickListener(this);

        managerJson = new JSONRequestManager();
        managerJson.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(String response) {
                textView.setText(Html.fromHtml("\n"));
                textView.append(response);
                return true;
            }
        });

        managerXml = new XMLRequestManager();
        managerXml.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(String response) {
                textView.setText("\n");
                textView.append(response);
                return true;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bJSON:
                try {
                    String json = "{\"Hello\":\"World\"}";
                    managerJson.sendRequest(json, "https://sym.iict.ch/rest/json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView.setText("Waiting...");
                break;
            case R.id.bXML:
                try {
                    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                "<!DOCTYPE directory SYSTEM \"http://sym.iict.ch/directory.dtd\">\n" +
                                "<directory />";
                    managerXml.sendRequest(xml, "https://sym.iict.ch/rest/xml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView.setText("Waiting...");
                break;
            default:
                log.warning("The button doesn't exists !");
        }
    }
}
