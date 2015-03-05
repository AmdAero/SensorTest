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

/**
 * Created by Arne on 24/02/2015.
 */
public class Gravity_raw extends Fragment implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.gravity_raw,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        f = new FileWriter("Gravity_raw.txt", getView().getContext());
        f.Write(getView().getContext(), "Time;");
        f.Write(getView().getContext(), "X;");
        f.Write(getView().getContext(), "Y;");
        f.Write(getView().getContext(), "Z;");
        f.Write(getView().getContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000.0;

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        } else {
            //DO SHIT
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        elapsedTime = (System.currentTimeMillis() / 1000.0) - startTime;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);

        oldElapsedTime = elapsedTime;

        ((TextView)getView().findViewById(R.id.x)).setText(String.valueOf(event.values[0]));
        ((TextView)getView().findViewById(R.id.y)).setText(String.valueOf(event.values[1]));
        ((TextView)getView().findViewById(R.id.z)).setText(String.valueOf(event.values[2]));
        ((TextView)getView().findViewById(R.id.sampling)).setText(String.valueOf(sampleRate));


        f.Write(getView().getContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(getView().getContext(), String.valueOf(event.values[0]).replace(".", ",") + ";");
        f.Write(getView().getContext(), String.valueOf(event.values[1]).replace(".", ",") + ";");
        f.Write(getView().getContext(), String.valueOf(event.values[2]).replace(".", ",") + ";");
        f.Write(getView().getContext(), System.getProperty("line.separator"));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onPause()
    {
        mSensorManager.unregisterListener(this,mSensor);
        super.onPause();
    }

    public static void OnKeyDown(int keyCode)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            android.os.Process.killProcess(android.os.Process.myPid());}
    }

}