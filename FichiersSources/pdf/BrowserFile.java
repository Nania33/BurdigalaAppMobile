package com.enseirb.pfa.bastats.pdf;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Arrays;


public class BrowserFile {

    private File[] d;
    private String[] filesName;
    public static String path = Environment.getExternalStorageDirectory().toString()+"/Bastats";

    public BrowserFile() {
        Log.v("Files", "Path: " + path);
        File f = new File(path);
        if (f.exists() && f.isDirectory())
            d=f.listFiles();
        else {
            if (f.mkdir()) {
                d = f.listFiles();
            }
            else {
                Log.e("FILE FATAL ERREURE", "ERREURE DE MKDIR");
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }

        }
        filesName=new String[d.length];
        for (int i=0;i<d.length;i++)
            filesName[i]=d[i].getName();
    }

    public String[] getFilesName() {
        return filesName;
    }

    public boolean fileExists(String a){
        return Arrays.asList(filesName).contains(a);
    }

}
