package ch.heigvd.sym.sym_labo2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.heigvd.sym.sym_labo2.R;

/**
 * A simple fragment to welcome the user.
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
