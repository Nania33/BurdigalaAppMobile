package com.enseirb.gl.burdigalaapp.file;

import android.content.Context;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.presenter.service.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rchabot on 10/12/15.
 */
public class FileIO {
    private static final String TAG = "FileIO";
    private Context context;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");


    public FileIO(Context context) {
        this.context = context;
    }

    public String readFromFile(String filename) {
        StringBuilder result = new StringBuilder();
        FileInputStream ins = null;
        try {
            ins = context.openFileInput(filename);
            if (ins != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                line = bufferedReader.readLine();
                Log.d(TAG, "readFromFileDate " + line);
                do {
                    line = bufferedReader.readLine();
                    Log.d(TAG, "readFromFile " + line);
                    result.append(line);
                } while (line != null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null)
                    ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "read: " + result.toString());
        return result.toString();
    }

    public void writeDataToFile(String data, String fileName) {
        File file = new File(context.getCacheDir(), fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "file does not exist, new file created");
            }
        }

        FileOutputStream outputStreamWriter = null;
        try {
            String dateString = simpleDateFormat.format(new Date());
            String toWrite = dateString + "\n" + data;
            outputStreamWriter = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter.write(toWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "data wroted successfully");
    }
}
