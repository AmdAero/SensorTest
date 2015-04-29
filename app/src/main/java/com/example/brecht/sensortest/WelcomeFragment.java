package com.example.brecht.sensortest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeFragment extends Fragment{

    View view;

    Intent intent;
    String Username;
    TextView Welkom;
    TextView UsernameTextView;
    JSONObject jsonObject;
    JSONArray test;

    private Handler mHandler = new Handler();
    int i = 1;
    int j = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welkom, container, false);

        String test = this.getArguments().getString("jsonobject");

        try {
            jsonObject = new JSONObject(test);


            Username = jsonObject.getJSONObject("user").getString("name");




        } catch (JSONException e) {
            //some exception handler code.
        }

        Welkom = (TextView) view.findViewById(R.id.tvWelcome);
        UsernameTextView = (TextView) view.findViewById(R.id.tvUsername);

        UsernameTextView.setText(String.valueOf(Username));

        mHandler.postDelayed(new Runnable() {
                public void run() {
                i = i + j;
                Welkom.setShadowLayer(i, 0,0, Color.BLACK);

                if(i > 8)
                    j = -1;
                else if (i < 2)
                    j = 1;

                mHandler.postDelayed(this, 200);
            }
        }, 100);

        return view;

    }
}
