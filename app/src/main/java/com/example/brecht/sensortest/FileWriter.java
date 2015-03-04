package com.example.brecht.sensortest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

/**
 * Created by Brecht on 23/02/2015.
 */
public class FileWriter extends ActionBarActivity{

    ActionBar actionBar = getSupportActionBar();
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeActionOverflowMenuShown();

    }

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
                i = new Intent(FileWriter.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.login:
                i = new Intent(FileWriter.this , Login.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(FileWriter.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.accelo:
                i = new Intent(FileWriter.this , Accelero.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(FileWriter.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.magneetometer:
                i = new Intent(FileWriter.this , Magnetometer.class);
                startActivity(i);
                return true;
            case R.id.rotation:
                i = new Intent(FileWriter.this , Rotation.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(FileWriter.this , FileWriter.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private String fileName;
    private  File file;

    public FileWriter(String fileWriter, Context context) {
        fileName = fileWriter;
        file = new File(context.getExternalFilesDir(null), fileName);

        if(file.exists())
            file.delete();
    }

    public void Write(Context c, String string)
    {
        FileOutputStream outputStream;
        OutputStreamWriter osw;

        if(isExternalStorageWritable() ==  false) {
            Toast.makeText(c, "No access to file", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            outputStream = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(outputStream);
            osw.append(string);
            osw.flush();
            osw.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Not necessarily at the moment!
    Checks if external storage is available to at least read
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    */

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
