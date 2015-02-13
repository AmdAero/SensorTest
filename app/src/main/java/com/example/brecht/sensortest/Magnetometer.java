package com.example.brecht.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class Magnetometer extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;

    //TODO: Better names!
    private TextView x;
    private TextView y;
    private TextView z;
    private TextView x2;
    private TextView y2;
    private TextView z2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Add setContentView
        //TODO: Add Sensorlogic
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        //Brecht: For now nothing...
    }

    public void onSensorChanged(SensorEvent event)
    {
        //TODO: Add logic to put sensor values on screen!


    }

}
