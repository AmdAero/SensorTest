package com.example.brecht.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import java.io.File;


public class Rotation extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float orientation[] = new float[3];
    private float rotation[] = new float[16];

    private TextView azimuth;
    private TextView pitch;
    private TextView roll;
    private TextView sampleRateText;

    private double startTime;
    private double elapsedTime;

    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotation);

        //TODO: Change ID's
        azimuth = (TextView) findViewById(R.id.x);
        pitch = (TextView) findViewById(R.id.y);
        roll = (TextView) findViewById(R.id.z);
        sampleRateText = (TextView) findViewById(R.id.sampling);

        f = new FileWriter("Rotation.txt", getApplicationContext());
        startTime = (System.currentTimeMillis() / 1000.0);

        f.Write(getApplicationContext(), "Time;");
        f.Write(getApplicationContext(), "Azimuth;");
        f.Write(getApplicationContext(), "Pitch;");
        f.Write(getApplicationContext(), "Roll;");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mSensor,  mSensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            //DO SHIT
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) || (keyCode == KeyEvent.KEYCODE_HOME) || (keyCode == KeyEvent.KEYCODE_APP_SWITCH ))
        {
            //finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    public void onSensorChanged(SensorEvent event)
    {
        SensorManager.getRotationMatrixFromVector(rotation, event.values);
        SensorManager.getOrientation(rotation, orientation);
        orientation[0]=(float)Math.toDegrees(orientation[0]);
        orientation[1]=(float)Math.toDegrees(orientation[1]);
        orientation[2]=(float)Math.toDegrees(orientation[2]);

        azimuth.setText(String.valueOf(orientation[0]));
        pitch.setText(String.valueOf(orientation[1]));
        roll.setText(String.valueOf(orientation[2]));

        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);
        sampleRateText.setText(String.valueOf(sampleRate));

        f.Write(getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(orientation[0]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(orientation[1]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(orientation[2]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));
        oldElapsedTime = elapsedTime;


    }

}
