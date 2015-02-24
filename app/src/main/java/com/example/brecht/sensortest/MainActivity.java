package com.example.brecht.sensortest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private Toast defaultToast;
    private Toast notImplementedToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        defaultToast = Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT);
        notImplementedToast = Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT);

        String[] choices = getResources().getStringArray(R.array.SensorList);
        ArrayAdapter<String> ListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choices);

        ListView lv = (ListView) findViewById(R.id.TestList);
        lv.setAdapter(ListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = null;
                switch(position)
                {
                    case 0:
                        //i = new Intent(MainActivity.this , Stopwatch.class);
                        if(!defaultToast.getView().isShown() && !notImplementedToast.getView().isShown())
                            notImplementedToast.show();
                        return;
                    // break;
                    case 1:
                        //i = new Intent(MainActivity.this , Login.class);
                        if(!defaultToast.getView().isShown() && !notImplementedToast.getView().isShown())
                            notImplementedToast.show();
                        return;
                    // break;
                    case 2:
                        i = new Intent(MainActivity.this , Accelero.class);
                        break;
                    case 3:
                        i = new Intent(MainActivity.this , Gyroscope.class);
                       /* if(!defaultToast.getView().isShown() && !notImplementedToast.getView().isShown())
                            notImplementedToast.show();
                        return;*/
                         break;
                    case 4:
                        i = new Intent(MainActivity.this , Magnetometer.class);
                        /*if(!defaultToast.getView().isShown() && !notImplementedToast.getView().isShown())
                            notImplementedToast.show();*/

                        break;
                    case 5:
                        i = new Intent(MainActivity.this , Rotation.class);
                        break;
                    case 6:
                        i = new Intent(MainActivity.this, Gravity_raw.class);
                        break;
                    case 7:
                        FileWriter.setFileName("Test2.txt", getApplicationContext());
                        FileWriter.Write(getApplicationContext(), "Testing the filewrite functionality!");
                        return;
                    default:
                        if(!defaultToast.getView().isShown() && !notImplementedToast.getView().isShown())
                           defaultToast.show();
                        return;
                }

                startActivity(i);
            }
        });

    }

}
