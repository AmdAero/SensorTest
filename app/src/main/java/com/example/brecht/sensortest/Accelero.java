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


public class Accelero extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];

    //TODO: Better names!
    private TextView x;
    private TextView y;
    private TextView z;
    private TextView x2;
    private TextView y2;
    private TextView z2;

    private double startTime;
    private double elapsedTime;

    private FileWriter f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer);

        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);
        x2 = (TextView) findViewById(R.id.x2);
        y2 = (TextView) findViewById(R.id.y2);
        z2 = (TextView) findViewById(R.id.z2);

        f = new FileWriter("Accelerometer.txt", getApplicationContext());
        f.Write(getApplicationContext(), "Time;");
        f.Write(getApplicationContext(), "X;");
        f.Write(getApplicationContext(), "Y;");
        f.Write(getApplicationContext(), "Z;");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
    }

    public void onSensorChanged(SensorEvent event)
    {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = 0.8f;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        x.setText(String.valueOf(gravity[0]));
        y.setText(String.valueOf(gravity[1]));
        z.setText(String.valueOf(gravity[2]));

        x2.setText(String.valueOf(linear_acceleration[0]));
        y2.setText(String.valueOf(linear_acceleration[1]));
        z2.setText(String.valueOf(linear_acceleration[2]));

        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;

        f.Write(getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[0]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[1]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[2]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));



    }

}
