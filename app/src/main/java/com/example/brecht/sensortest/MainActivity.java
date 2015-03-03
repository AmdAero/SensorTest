package com.example.brecht.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private FileWriter f;




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
                            i = new Intent(MainActivity.this , Stopwatch.class);
                            break;
                        case 1:
                            i = new Intent(MainActivity.this , Login.class);
                            break;
                        case 2:
                            i = new Intent(MainActivity.this , Gravity_raw.class);
                            break;
                        case 3:
                            i = new Intent(MainActivity.this , Accelero.class);
                            break;
                        case 4:
                            i = new Intent(MainActivity.this , Gyroscope.class);
                            break;
                        case 5:
                            i = new Intent(MainActivity.this , Magnetometer.class);
                            break;
                        case 6:
                            i = new Intent(MainActivity.this , Rotation.class);
                            break;
                        case 7:
                            i = new Intent(MainActivity.this , FileWriter.class);
                            break;
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



