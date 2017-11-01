package ch.heigvd.sym.sym_labo2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ch.heigvd.sym.sym_labo2.request.CommunicationEventListener;
import ch.heigvd.sym.sym_labo2.request.manager.DeferredRequestManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransmissionDeferred extends Fragment implements View.OnClickListener {

    private View view;
    private TextView textView;
    private DeferredRequestManager manager;

    public TransmissionDeferred() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transmission_deferred, container, false);

        final Button button = (Button) view.findViewById(R.id.bDeferred);
        textView = (TextView) view.findViewById(R.id.textInfoDeferred);
        button.setOnClickListener(this);

        manager = new DeferredRequestManager();
        manager.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(String response) {
                textView.setText(Html.fromHtml("<b>Response:<\b>"));
                textView.append("\n");
                textView.append(response);
                return true;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bDeferred:
                try {
                    manager.sendRequest("echo", "https://sym.iict.ch/rest/txt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textView.setText("Waiting...");
        }
    }
}
