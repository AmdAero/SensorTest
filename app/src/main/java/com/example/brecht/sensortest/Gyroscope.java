package com.example.brecht.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class Gyroscope extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView GyroscopeX;
    private TextView GyroscopeY;
    private TextView GyroscopeZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyroscope);

        FileWriter.setFileName("Gyroscope.txt", getApplicationContext());

        GyroscopeX=(TextView) findViewById(R.id.GyroscopeX);
        GyroscopeY=(TextView) findViewById(R.id.GyroscopeY);
        GyroscopeZ=(TextView) findViewById(R.id.GyroscopeZ);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
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
        //TODO: Add logic to put sensor values on screen
        GyroscopeX.setText(String.valueOf(event.values[0]/Math.PI*180) + " graden");
        GyroscopeY.setText(String.valueOf(event.values[1]/Math.PI*180) + " graden");
        GyroscopeZ.setText(String.valueOf(event.values[2]/Math.PI*180) + " graden");

        FileWriter.Write(getApplicationContext(), "X: " + String.valueOf(event.values[0]/Math.PI*180) + ";");
        FileWriter.Write(getApplicationContext(), "Y: " + String.valueOf(event.values[1]/Math.PI*180) + ";");
        FileWriter.Write(getApplicationContext(), "Z: " + String.valueOf(event.values[2]/Math.PI*180) + ";");
        FileWriter.Write(getApplicationContext(), System.getProperty("line.separator"));

    }

}
