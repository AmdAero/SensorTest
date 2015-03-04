package com.example.brecht.sensortest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


public class Magnetometer extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView xMagnetometer;
    private TextView yMagnetometer;
    private TextView zMagnetometer;
    private TextView sampleRateText;

    private double startTime;
    private double elapsedTime;

    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    public ActionBar actionBar = getSupportActionBar();

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_screens, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.stopwatch:
                i = new Intent(Magnetometer.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.login:
                i = new Intent(Magnetometer.this , LoginFragment.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(Magnetometer.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(Magnetometer.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.rotation:
                i = new Intent(Magnetometer.this , RotationFragment.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(Magnetometer.this , FileWriter.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnetometer);


        xMagnetometer = (TextView) findViewById(R.id.xMagnetometer);
        yMagnetometer = (TextView) findViewById(R.id.yMagnetometer);
        zMagnetometer = (TextView) findViewById(R.id.zMagnetometer);
        sampleRateText = (TextView) findViewById(R.id.sampling);

        startTime = System.currentTimeMillis() / 1000.0;
        f = new FileWriter("Magnetometer.txt", getApplicationContext());

        f.Write(getApplicationContext(), "Time;");
        f.Write(getApplicationContext(), "X;");
        f.Write(getApplicationContext(), "Y;");
        f.Write(getApplicationContext(), "Z;");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));

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
        xMagnetometer.setText(String.valueOf(event.values[0]) + " µT");
        yMagnetometer.setText(String.valueOf(event.values[1]) + " µT");
        zMagnetometer.setText(String.valueOf(event.values[2]) + " µT");

        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);
        sampleRateText.setText(String.valueOf(sampleRate));

        f.Write(getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[0]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[1]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(event.values[2]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));
        oldElapsedTime = elapsedTime;
    }

}
