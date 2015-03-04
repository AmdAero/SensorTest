package com.example.brecht.sensortest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Brecht on 4/03/2015.
 */

public class RootLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.rootlogin, container, false);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        /*
        * When this container fragment is created, we fill it with our first
        * "real" fragment! This will be the LoginFragment
        */

        transaction.replace(R.id.root_login, new LoginFragment());
        transaction.commit();
        return view;
    }
}
