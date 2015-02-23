package com.example.brecht.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class Rotation extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float orientation[] = new float[3];
    private float rotation[] = new float[16];

    private TextView azimuth;
    private TextView pitch;
    private TextView roll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotation);

        //TODO: Change ID's
        azimuth = (TextView) findViewById(R.id.x);
        pitch = (TextView) findViewById(R.id.y);
        roll = (TextView) findViewById(R.id.z);

        FileWriter.setFileName("Rotation.txt", getApplicationContext());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mSensor, 150000);
        }
        else {
            //DO SHIT
        }
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

        FileWriter.Write(getApplicationContext(), "Azimuth: " + String.valueOf(orientation[0]) + ";");
        FileWriter.Write(getApplicationContext(), "Pitch: " + String.valueOf(orientation[1]) + ";");
        FileWriter.Write(getApplicationContext(), "Roll: " + String.valueOf(orientation[2]) + ";");
        FileWriter.Write(getApplicationContext(), System.getProperty("line.separator"));


    }

}
