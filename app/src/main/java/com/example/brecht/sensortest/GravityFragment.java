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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arne on 24/02/2015.
 */
public class GravityFragment extends Fragment implements SensorEventListener , View.OnClickListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private double startTime;
    private double elapsedTime;
    private double oldElapsedTime;
    private double sampleRate;


    private TextView X;
    private TextView Y;
    private TextView Z;
    private TextView sampleRateText;

    private Button startButton;
    private Button stopButton;

    private FileWriter f;
    private List<String> list = new ArrayList<String>();

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v=inflater.inflate(R.layout.fragment_gravity,container,false);

        X = (TextView) v.findViewById(R.id.tvX);
        Y = (TextView) v.findViewById(R.id.tvY);
        Z = (TextView) v.findViewById(R.id.tvZ);

        sampleRateText = (TextView) v.findViewById(R.id.tvSampleRate);

        startButton = (Button) v.findViewById(R.id.btnStartG);
        startButton.setOnClickListener(this);
        stopButton = (Button) v.findViewById(R.id.btnStopG);
        stopButton.setOnClickListener(this);

        return v;
    }


    private void Start()
    {
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, mSensor,  mSensorManager.SENSOR_DELAY_GAME);
        }
        else {
            //DO SHIT
        }

        f = new FileWriter("Gravity_raw.txt", getView().getContext());
        f.Write(getView().getContext(), "Time;");
        f.Write(getView().getContext(), "X;");
        f.Write(getView().getContext(), "Y;");
        f.Write(getView().getContext(), "Z;");
        f.Write(getView().getContext(), System.getProperty("line.separator"));

        startTime = System.currentTimeMillis() / 1000.0;
    }
    private void Stop()
    {
        X.setText("?");
        Y.setText("?");
        Z.setText("?");

        sampleRateText.setText("?");

        if (mSensorManager != null)
            mSensorManager.unregisterListener(this, mSensor);

        int i = 0;
        for(String s : list)
        {
            i++;
            f.Write(getActivity().getApplicationContext(), s);

            if (i % 4 == 0)
                f.Write(getActivity().getApplicationContext(), System.getProperty("line.separator"));
        }
    }

    public void onClick(View v)
    {

        switch (v.getId()) {
            case R.id.btnStartG:
                Start();
                break;
            case R.id.btnStopG:
                Stop();
                break;
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        elapsedTime = (System.currentTimeMillis() / 1000.0) - startTime;
        sampleRate = 1 / (elapsedTime - oldElapsedTime);

        oldElapsedTime = elapsedTime;

        X.setText(String.valueOf(event.values[0]));
        Y.setText(String.valueOf(event.values[1]));
        Z.setText(String.valueOf(event.values[2]));
        sampleRateText.setText(String.valueOf(sampleRate));

        list.add(String.valueOf(elapsedTime).replace(".", ",") + ";");
        list.add(String.valueOf(event.values[0]).replace(".", ",") + ";");
        list.add(String.valueOf(event.values[1]).replace(".", ",") + ";");
        list.add(String.valueOf(event.values[2]).replace(".", ",") + ";");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }


    public static void OnKeyDown(int keyCode)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            android.os.Process.killProcess(android.os.Process.myPid());}
    }

}