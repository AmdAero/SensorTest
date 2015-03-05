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


public class Magnetometer extends Fragment implements SensorEventListener {

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
        v=inflater.inflate(R.layout.magnetometer,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        startTime = System.currentTimeMillis() / 1000.0;

        f = new FileWriter("Magnetometer.txt", getView().getContext());

        f.Write(getView().getContext(), "Time;");
        f.Write(getView().getContext(), "X;");
        f.Write(getView().getContext(), "Y;");
        f.Write(getView().getContext(), "Z;");
        f.Write(getView().getContext(), System.getProperty("line.separator"));

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
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
        //Brecht: For now nothing...
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        elapsedTime = (System.currentTimeMillis() /1000.0) - startTime ;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);

        ((TextView)v.findViewById(R.id.xMagnetometer)).setText(String.valueOf(event.values[0]) + " µT");
        ((TextView)v.findViewById(R.id.yMagnetometer)).setText(String.valueOf(event.values[1]) + " µT");
        ((TextView)v.findViewById(R.id.zMagnetometer)).setText(String.valueOf(event.values[2]) + " µT");
        ((TextView)v.findViewById(R.id.sampling)).setText(String.valueOf(sampleRate));

        f.Write(v.getContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[0]).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[1]).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[2]).replace(".", ",") + ";");
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
