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


public class Gyroscope extends Fragment implements SensorEventListener {

    private View v;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;

    private FileWriter f;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.gyroscope,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        f = new FileWriter("Gyroscope.txt", v.getContext());
        f.Write(getView().getContext(), "Time;");
        f.Write(getView().getContext(), "X;");
        f.Write(getView().getContext(), "Y;");
        f.Write(getView().getContext(), "Z;");
        f.Write(getView().getContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000.0;

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        }
        else {

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

        ((TextView)v.findViewById(R.id.GyroscopeX)).setText(String.valueOf(event.values[0] / Math.PI * 180) + " degrees/s");
        ((TextView)v.findViewById(R.id.GyroscopeY)).setText(String.valueOf(event.values[1] / Math.PI * 180) + " degrees/s");
        ((TextView)v.findViewById(R.id.GyroscopeZ)).setText(String.valueOf(event.values[2] / Math.PI * 180) + " degrees/s");
        ((TextView)v.findViewById(R.id.sampling)).setText(String.valueOf(sampleRate));

        f.Write(v.getContext(), String.valueOf(elapsedTime).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[0]/Math.PI*180).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[1]/Math.PI*180).replace(".", ",") + ";");
        f.Write(v.getContext(), String.valueOf(event.values[2]/Math.PI*180).replace(".", ",") + ";");
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
