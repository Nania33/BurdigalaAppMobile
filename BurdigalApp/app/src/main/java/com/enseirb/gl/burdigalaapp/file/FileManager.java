package com.enseirb.gl.burdigalaapp.file;

import android.content.Context;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.model.service.Service;

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
    private final String TAG = "FileManager";
    private final int daysBeforeDowloadingAgain = 7;
    private final int secondsInDay = 1000 * 60 * 60 * 24;
    private Context context;

    public FileManager(Context ctx) {
        context = ctx;
    }

    public boolean fileUpdateNeeded(Service service) {
        String fileName = service.getFilename();
        File file = new File(context.getCacheDir(), fileName);

        if (!file.exists()) {
            Log.d(TAG, "file does not exists");
            return true;
        } else {
            Date fileDate = getDateFromFile(fileName);
            Log.d(TAG, "file exists with date " + simpleDateFormat.format(fileDate) + " and name " + fileName);
            if (fileDate == null || isTooOld(fileDate)) {
                return true;
            }
        }
        return false;
    }

    public String readFromFile(String filename) {
        String data = "";
        FileInputStream ins = null;
        try {
            ins = context.openFileInput(filename);
            if (ins != null) {
                StringBuilder stringBuilder = new StringBuilder();
                String receiveString = "";
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                bufferedReader.readLine(); // read the date

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                data = stringBuilder.toString();
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
        return data;
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
