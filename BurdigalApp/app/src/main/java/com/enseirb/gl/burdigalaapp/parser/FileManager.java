package com.enseirb.gl.burdigalaapp.parser;

import android.content.Context;
import android.util.Log;

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

/*
* Created by Alex on 12/6/2015.
*/

public class FileManager {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    private final String TAG = "DEBUG1";
    private final int daysBeforeDowloadingAgain = 7;
    private final int secondsInDay = 1000 * 60 * 60 * 24;
    private Context context;

    public FileManager(Context ctx) {
        context = ctx;
    }

    public String readFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        FileInputStream ins;
        try {
            ins = context.openFileInput(fileName);
            if (ins != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                do {
                    line = bufferedReader.readLine();
                    Log.d(TAG,line);
                    // if the line is not in a date format (like the first line)
                    if (!line.equals(simpleDateFormat.format(simpleDateFormat.parse(line)))) {
                        result.append(line);
                    }
                }
                while (line != null);
            }
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void writeToFile(String data, String fileName) {
        Log.d(TAG, fileName);

        File file = new File(context.getCacheDir(), fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "file does not exist");
                writeDataToFile(data, fileName);
            }
        } else {
            Log.d(TAG, "file exists");
            Date fileDate = getDateFromFile(fileName);
            if (fileDate==null || isTooOld(fileDate)) {
                writeDataToFile(data, fileName);
            }
        }
    }

    private void writeDataToFile(String data, String fileName) {
        try {
            String dateString = simpleDateFormat.format(new Date());
            String toWrite = dateString + "\n" + data;
            FileOutputStream outputStreamWriter = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStreamWriter.write(toWrite.getBytes());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Date getDateFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        FileInputStream ins;
        try {
            ins = context.openFileInput(fileName);
            if (ins != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dateString = bufferedReader.readLine();
                ins.close();

                return simpleDateFormat.parse(dateString);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / secondsInDay);
    }

    private boolean isTooOld(Date fileDate) {
        return daysBetween(fileDate, new Date()) > daysBeforeDowloadingAgain;
    }
}