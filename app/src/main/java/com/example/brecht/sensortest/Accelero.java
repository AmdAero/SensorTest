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


public class Accelero extends ActionBarActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];

    //TODO: Better names!
    private TextView x = (TextView) findViewById(R.id.x);
    private TextView y;
    private TextView z;
    private TextView x2;
    private TextView y2;
    private TextView z2;
    private TextView sampleRateText;

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    ActionBar actionBar = getSupportActionBar();

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
                i = new Intent(Accelero.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.login:
                i = new Intent(Accelero.this , Login.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(Accelero.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(Accelero.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.magneetometer:
                i = new Intent(Accelero.this , Magnetometer.class);
                startActivity(i);
                return true;
            case R.id.rotation:
                i = new Intent(Accelero.this , Rotation.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(Accelero.this , FileWriter.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer);

        y = (TextView) findViewById(R.id.y);
        z = (TextView) findViewById(R.id.z);
        x2 = (TextView) findViewById(R.id.x2);
        y2 = (TextView) findViewById(R.id.y2);
        z2 = (TextView) findViewById(R.id.z2);
        sampleRateText = (TextView) findViewById(R.id.sampling);

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
        sampleRate = 1 / (elapsedTime - oldElapsedTime);
        sampleRateText.setText(String.valueOf(sampleRate));

        f.Write(getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[0]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[1]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), String.valueOf(gravity[2]).replace(".", ",") + ";");
        f.Write(getApplicationContext(), System.getProperty("line.separator"));
        oldElapsedTime = elapsedTime;


    }


}
