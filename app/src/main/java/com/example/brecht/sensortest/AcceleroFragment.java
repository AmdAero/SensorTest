package com.example.brecht.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AcceleroFragment extends Fragment implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float gravity[] = new float[3];
    private float linear_acceleration[] = new float[3];


    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_accelerometer,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        f = new FileWriter("Accelerometer.txt", v.getContext());

        f.Write(v.getContext(), "Time;");
        f.Write(v.getContext(), "X;");
        f.Write(v.getContext(), "Y;");
        f.Write(v.getContext(), "Z;");
        f.Write(v.getContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000;

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            //DO SHIT
        }

        super.onActivityCreated(savedInstanceState);
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

        ((TextView)v.findViewById(R.id.tvX)).setText(String.valueOf(gravity[0]));
        ((TextView)v.findViewById(R.id.tvY)).setText(String.valueOf(gravity[1]));
        ((TextView)v.findViewById(R.id.tvZ)).setText(String.valueOf(gravity[2]));

        ((TextView)v.findViewById(R.id.x2)).setText(String.valueOf(linear_acceleration[0]));
        ((TextView)v.findViewById(R.id.y2)).setText(String.valueOf(linear_acceleration[1]));
        ((TextView)v.findViewById(R.id.z2)).setText(String.valueOf(linear_acceleration[2]));

        ((TextView)v.findViewById(R.id.tvSampleRate)).setText(String.valueOf(sampleRate));

        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);

        f.Write(v.getContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(gravity[0]).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(gravity[1]).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(gravity[2]).replace(".", ",") + ";");
        f.Write(v.getContext(), System.getProperty("line.separator"));
        oldElapsedTime = elapsedTime;

    }
    @Override
    public void onPause()
    {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onResume()
    {
        mSensorManager.registerListener(this,mSensor,mSensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    public static void OnKeyDown(int keyCode)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            android.os.Process.killProcess(android.os.Process.myPid());}
    }


}