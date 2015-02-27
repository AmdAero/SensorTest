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


public class Gyroscope extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView GyroscopeX;
    private TextView GyroscopeY;
    private TextView GyroscopeZ;

    private double startTime;
    private double elapsedTime;

    private FileWriter f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyroscope);

        f = new FileWriter("Gyroscope.txt", getApplicationContext());
        f.Write(getApplicationContext(), "Time;");
        f.Write(getApplicationContext(), "X;");
        f.Write(getApplicationContext(), "Y;");
        f.Write(getApplicationContext(), "Z;");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000.0;

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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        //Brecht: For now nothing...
    }

    public void onSensorChanged(SensorEvent event)
    {
        GyroscopeX.setText(String.valueOf(event.values[0]/Math.PI*180) + " degrees/s");
        GyroscopeY.setText(String.valueOf(event.values[1]/Math.PI*180) + " degrees/s");
        GyroscopeZ.setText(String.valueOf(event.values[2]/Math.PI*180) + " degrees/s");

        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;

        f.Write(getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[0]/Math.PI*180).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[1]/Math.PI*180).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[2]/Math.PI*180).replace(".", ",") + ";");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));

    }

}
