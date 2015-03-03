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
                i = new Intent(Rotation.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.login:
                i = new Intent(Rotation.this , Login.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(Rotation.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(Rotation.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.magneetometer:
                i = new Intent(Rotation.this , Magnetometer.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(Rotation.this , FileWriter.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

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
