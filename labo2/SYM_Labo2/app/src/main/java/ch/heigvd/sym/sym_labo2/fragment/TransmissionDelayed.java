package ch.heigvd.sym.sym_labo2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.logging.Logger;

import ch.heigvd.sym.sym_labo2.R;
import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.manager.DelayedRequestManager;

/**
 * A simple fragment to allow the user to send a delayed transmission.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class TransmissionDelayed extends Fragment {

    private static final Logger log = Logger.getLogger(TransmissionDelayed.class.getSimpleName());

    private View view;
    private TextView textView;
    private Spinner spinner;
    private DelayedRequestManager manager;

    public TransmissionDelayed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transmission_delayed, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        final Button button = (Button) view.findViewById(R.id.bDeferred);
        textView = (TextView) view.findViewById(R.id.textInfoDelayed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bDeferred:
                        try {
                            String content = ((CharSequence)spinner.getSelectedItem()).toString();
                            manager.sendRequest(content, "https://sym.iict.ch/rest/txt");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        textView.setText(R.string.waiting_rsp);
                    default:
                }
            }
        });

        manager = new DelayedRequestManager();
        manager.setCommunicationEventListener(new CommunicationEventListener() {
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
}
