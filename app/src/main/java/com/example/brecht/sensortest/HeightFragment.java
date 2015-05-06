package com.example.brecht.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class HeightFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    private Sensor mSensor;
    private SensorManager mSensorManager;

    private AcceleroFilter xFilter = new AcceleroFilter();
    private AcceleroFilter yFilter = new AcceleroFilter();
    private AcceleroFilter zFilter = new AcceleroFilter();

    private double xFiltered;
    private double yFiltered;
    private double zFiltered;

    private float[] orientationValues = new float[3];
    private float rotation[] = new float[16];

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    double[] arrayVelo = new double[50];
    double[] arrayDist = new double[50];

    private double velocity = 0;
    private double oldVelocity = 0;

    private double height;

    int pointer = 0;

    private TextView tvaY;
    private TextView tvVelocity;
    private TextView tvHeight;

    private TextView sampleRateText;

    private Button startButton;
    private Button stopButton;

    private FileWriter f;
    private List<String> list = new ArrayList<String>();

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_height,container,false);

        tvaY = (TextView) v.findViewById(R.id.tvaY);
        tvVelocity = (TextView) v.findViewById(R.id.tvVelocity);
        tvHeight = (TextView) v.findViewById(R.id.tvHeight);

        sampleRateText = (TextView) v.findViewById(R.id.tvSampleRate);

        startButton = (Button) v.findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);
        stopButton = (Button) v.findViewById(R.id.btnStop);
        stopButton.setOnClickListener(this);

        return v;
    }
    private void Start()
    {
        f = new FileWriter("Height.txt", getActivity().getApplicationContext());
        f.Write(v.getContext(), "Time;");
        f.Write(v.getContext(), "xFiltered;");
        f.Write(v.getContext(), "yFiltered;");
        f.Write(v.getContext(), "zFiltered;");
        f.Write(v.getContext(), "Azimuth;");
        f.Write(v.getContext(), "Pitch;");
        f.Write(v.getContext(), "Roll;");
        f.Write(v.getContext(), "aX;");
        f.Write(v.getContext(), "aY;");
        f.Write(v.getContext(), "Velocity;");
        f.Write(v.getContext(), System.getProperty("line.separator"));

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),  SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),  SensorManager.SENSOR_DELAY_GAME);
        }
        else {
            //DO SHIT
        }

        startTime = System.currentTimeMillis() / 1000;
    }

    private void Stop()
    {
        tvaY.setText("?");
        tvVelocity.setText("?");
        tvHeight.setText("?");
        sampleRateText.setText("?");

        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION));
            mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR));
        }

        int i = 0;
        for(String s : list)
        {
            i++;
            f.Write(getActivity().getApplicationContext(), s);

            if (i % 10 == 0)
                f.Write(getActivity().getApplicationContext(), System.getProperty("line.separator"));
        }
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

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    public void onSensorChanged(SensorEvent event)
    {
        mSensor = event.sensor;


        if (mSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            xFiltered = xFilter.Filter(event.values[0]);
            yFiltered = yFilter.Filter(event.values[1]);
            zFiltered = zFilter.Filter(event.values[2]);
        }

        else if(mSensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            SensorManager.getRotationMatrixFromVector(rotation, event.values);
            SensorManager.getOrientation(rotation, orientationValues);
            double azimuth = Math.toDegrees(orientationValues[0]);
            double pitch = Math.toDegrees(orientationValues[1]);
            double roll = Math.toDegrees(orientationValues[2]);

            double ax = xFiltered * Math.cos(Math.toRadians(roll)) + yFiltered * Math.cos(Math.toRadians(90) - Math.toRadians(roll));
            double ay = yFiltered * Math.cos(Math.toRadians(90) + Math.toRadians(pitch)) + xFiltered * Math.cos(Math.toRadians(90) + Math.toRadians(roll)) + zFiltered * Math.cos(Math.toRadians(pitch)) * Math.cos(Math.toRadians(roll));

            tvaY.setText(String.valueOf(ay));

            elapsedTime = (System.currentTimeMillis() / 1000.0) - startTime;
            sampleRate = 1 / (elapsedTime - oldElapsedTime);
            sampleRateText.setText(String.valueOf(sampleRate));

            velocity = oldVelocity + (ay * (elapsedTime - oldElapsedTime));

            oldElapsedTime = elapsedTime;
            oldVelocity = velocity;

            tvVelocity.setText(String.valueOf(velocity));

            list.add(String.valueOf(elapsedTime).replace(".", ",") + ";");
            list.add(String.valueOf(xFiltered).replace(".", ",") + ";");
            list.add(String.valueOf(yFiltered).replace(".", ",") + ";");
            list.add(String.valueOf(zFiltered).replace(".", ",") + ";");
            list.add(String.valueOf(azimuth).replace(".", ",") + ";");
            list.add(String.valueOf(pitch).replace(".", ",") + ";");
            list.add(String.valueOf(roll).replace(".", ",") + ";");
            list.add(String.valueOf(ax).replace(".", ",") + ";");
            list.add(String.valueOf(ay).replace(".", ",") + ";");
            list.add(String.valueOf(velocity).replace(".", ",") + ";");

            pointer ++;
        }
    }
}
