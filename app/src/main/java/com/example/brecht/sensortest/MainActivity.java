package com.example.brecht.sensortest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.lang.reflect.Field;


public class MainActivity extends ActionBarActivity {

    private Toast defaultToast;
    private Toast notImplementedToast;
    private FileWriter f;
    ActionBar actionBar = getSupportActionBar();
    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_screens, menu);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.stopwatch:
                i = new Intent(MainActivity.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.login:
                i = new Intent(MainActivity.this , Login.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(MainActivity.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.accelo:
                i = new Intent(MainActivity.this , Accelero.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(MainActivity.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.magneetometer:
                i = new Intent(MainActivity.this , Magnetometer.class);
                startActivity(i);
                return true;
            case R.id.rotation:
                i = new Intent(MainActivity.this , Rotation.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(MainActivity.this , FileWriter.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeActionOverflowMenuShown();
    }

    private void makeActionOverflowMenuShown() {
        //devices with hardware menu button (e.g. Samsung Note) don't show action overflow menu
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
    }

}
