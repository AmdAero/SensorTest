package com.example.brecht.sensortest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class welkom extends ActionBarActivity {

    Intent intent;
    String Username;
    TextView Welkom;
    TextView UsernameTextView;

    private Handler mHandler = new Handler();
    int i = 1;
    int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welkom);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        Username = intent.getStringExtra("Username");
        Welkom = (TextView) findViewById(R.id.Welcome_textview);
        UsernameTextView = (TextView) findViewById(R.id.Username);

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

    }
}
