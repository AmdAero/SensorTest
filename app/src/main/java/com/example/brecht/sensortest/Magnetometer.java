package com.example.brecht.sensortest;

import android.content.Context;
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
    private TextView xMagnetometer;
    private TextView yMagnetometer;
    private TextView zMagnetometer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnetometer);

        xMagnetometer = (TextView) findViewById(R.id.xMagnetometer);
        yMagnetometer = (TextView) findViewById(R.id.yMagnetometer);
        zMagnetometer = (TextView) findViewById(R.id.zMagnetometer);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            //DO SHIT
        }

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        //Brecht: For now nothing...
    }

    public void onSensorChanged(SensorEvent event)
    {
        xMagnetometer.setText(String.valueOf(event.values[0]) + " µT");
        yMagnetometer.setText(String.valueOf(event.values[1]) + " µT");
        zMagnetometer.setText(String.valueOf(event.values[2]) + " µT");
    }

}
