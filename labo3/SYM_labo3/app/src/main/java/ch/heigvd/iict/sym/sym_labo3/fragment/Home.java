package ch.heigvd.iict.sym.sym_labo3.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.heigvd.iict.sym.sym_labo3.R;

/**
 * Main fragment. Shown when the application is launched.
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
