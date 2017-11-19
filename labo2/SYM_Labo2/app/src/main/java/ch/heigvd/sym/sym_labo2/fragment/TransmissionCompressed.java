package ch.heigvd.sym.sym_labo2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.logging.Logger;

import ch.heigvd.sym.sym_labo2.R;
import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.manager.CompressedRequestManager;

/**
 * A simple fragment to allow the user to send a compressed transmission.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class TransmissionCompressed extends Fragment {

    private static final Logger log = Logger.getLogger(TransmissionCompressed.class.getSimpleName());

    private View view;
    private TextView textView;
    private Spinner spinner;
    private CompressedRequestManager manager;

    public TransmissionCompressed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transmission_compressed, container, false);

        final Button button = (Button) view.findViewById(R.id.bCompressed);
        textView = (TextView) view.findViewById(R.id.textInfoCompressed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bCompressed:
                        try {
                            String content = ((CharSequence)spinner.getSelectedItem()).toString();
                            manager.sendRequest(content, "https://sym.iict.ch/rest/txt");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        textView.setText(R.string.waiting_rsp);
                        break;
                    default:
                        log.warning("The button doesn't exists !");
                }
            }
        });

        spinner = (Spinner) view.findViewById(R.id.spinner);

        manager = new CompressedRequestManager();
        manager.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(String response) {
                textView.setText(response);
                return true;
            }
        });

        return view;
    }

}
