package com.example.brecht.sensortest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class RotationFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    View view;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float orientation[] = new float[3];
    private float rotation[] = new float[16];

    private TextView azimuth;
    private TextView pitch;
    private TextView roll;
    private TextView sampleRateText;

    private Button startButton;
    private Button stopButton;

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rotation, container, false);

        //TODO: Change ID's
        azimuth = (TextView) view.findViewById(R.id.x);
        pitch = (TextView) view.findViewById(R.id.y);
        roll = (TextView) view.findViewById(R.id.z);
        sampleRateText = (TextView) view.findViewById(R.id.sampling);
        startButton = (Button) view.findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);
        stopButton = (Button) view.findViewById(R.id.btnStop);
        stopButton.setOnClickListener(this);

        return view;
    }

    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.btnStart:
                Start();
                break;
            case R.id.btnStop:
                Stop();
                break;
        }

    }

    private void Start()
    {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mSensor,  mSensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            //DO SHIT
        }

        f = new FileWriter("Rotation.txt", getActivity().getApplicationContext());
        startTime = (System.currentTimeMillis() / 1000.0);

        f.Write(getActivity().getApplicationContext(), "Time;");
        f.Write(getActivity().getApplicationContext(), "Azimuth;");
        f.Write(getActivity().getApplicationContext(), "Pitch;");
        f.Write(getActivity().getApplicationContext(), "Roll;");
        f.Write(getActivity().getApplicationContext(), System.getProperty("line.separator"));
    }

    private void Stop()
    {
        azimuth.setText("?");
        pitch.setText("?");
        roll.setText("?");
        sampleRateText.setText("?");
        mSensorManager.unregisterListener(this, mSensor);
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

        f.Write(getActivity().getApplicationContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getActivity().getApplicationContext(), String.valueOf(orientation[0]).replace(".", ",") + ";");
        f.Write(getActivity().getApplicationContext(), String.valueOf(orientation[1]).replace(".", ",") + ";");
        f.Write(getActivity().getApplicationContext(), String.valueOf(orientation[2]).replace(".", ",") + ";");
        f.Write(getActivity().getApplicationContext(), System.getProperty("line.separator"));
        oldElapsedTime = elapsedTime;
    }
}
