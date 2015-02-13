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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultToast = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);

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
                        i = new Intent(MainActivity.this , Accelero.class);
                        break;
                    /*case 1:
                        i = new Intent(MainActivity.this , Gyroscope.class);
                        break;*/
                    /*case 2:
                        i = new Intent(MainActivity.this , Magnetometer.class);
                        break;*/
                    default:
                        if(!defaultToast.getView().isShown())
                           defaultToast.show();
                        return;
                }

                startActivity(i);
            }
        });

    }

}
