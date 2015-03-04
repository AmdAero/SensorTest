package com.example.brecht.sensortest;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WelcomeFragment extends Fragment{

    View v;

    Intent intent;
    String Username;
    TextView Welkom;
    TextView UsernameTextView;

    private Handler mHandler = new Handler();
    int i = 1;
    int j = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_welkom, container, false);

        Username = "Test";
        //Username = intent.getStringExtra("Username");
        Welkom = (TextView) v.findViewById(R.id.Welcome_textview);
        UsernameTextView = (TextView) v.findViewById(R.id.Username);

        UsernameTextView.setText(String.valueOf(Username));

        mHandler.postDelayed(new Runnable() {
                public void run() {
                i = i + j;
                Welkom.setShadowLayer(i, 0,0, Color.BLACK);

                if(i > 11)
                    j = -1;
                else if (i < 2)
                    j = 1;

                mHandler.postDelayed(this, 150);
            }
        }, 100);

        return v;

    }
}