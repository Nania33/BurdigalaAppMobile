package com.enseirb.gl.burdigalaapp.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Alex on 12/6/2015.
 */
public class FileParser {

    public String retrieveDataFromFile(String fileName){
        StringBuilder result = new StringBuilder();
        FileInputStream ins;
        try {
            ins = new FileInputStream(fileName);
            if(ins != null){
                InputStreamReader inputStreamReader = new InputStreamReader(ins);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                do{
                    line = bufferedReader.readLine();
                    if(!line.equals(fileName)){
                        result.append(line);
                    }
                }
                while(line != null);
            }
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
