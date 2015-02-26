package com.example.brecht.sensortest;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Brecht on 24/02/2015.
 */
public class Login extends ActionBarActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        LinearLayout ll= (LinearLayout) findViewById(R.id.LoginLayout);
        final Button bt= (Button) findViewById(R.id.LogInButton);
        bt.setOnClickListener(new View.OnClickListener() {
            Intent i=null;
            @Override
            public void onClick(View v) {

                 i=new Intent(Login.this,MainActivity.class);
                startActivity(i);
            }


        });
    }

}
