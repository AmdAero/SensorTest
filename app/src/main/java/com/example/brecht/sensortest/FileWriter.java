package com.example.brecht.sensortest;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Brecht on 23/02/2015.
 */
public class FileWriter {

    private static String fileName;
    private static File file;

    public static void setFileName(String fileWriter, Context context) {
        fileName = fileWriter;
        file = new File(context.getExternalFilesDir(null), fileName);
    }

    public static void Write(Context c)
    {
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }





}
