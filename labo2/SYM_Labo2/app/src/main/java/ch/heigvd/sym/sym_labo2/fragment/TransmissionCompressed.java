package ch.heigvd.sym.sym_labo2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Logger;

import ch.heigvd.sym.sym_labo2.R;
import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.manager.CompressedRequestManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransmissionCompressed extends Fragment implements View.OnClickListener{

    private static final Logger log = Logger.getLogger(TransmissionCompressed.class.getSimpleName());

    private View view;
    private TextView textView;
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
        button.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCompressed:
                try {
                    manager.sendRequest("echo", "https://sym.iict.ch/rest/txt");
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
