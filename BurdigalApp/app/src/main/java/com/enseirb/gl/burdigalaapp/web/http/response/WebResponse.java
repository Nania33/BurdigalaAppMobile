package com.enseirb.gl.burdigalaapp.web.http.response;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.web.http.format.Format;

/**
 * Created by rchabot on 17/11/15.
 */
public class WebResponse {
    public static final String NO_DATA = "NO_DATA";
    private static final String TAG = "WebResponse";

    private String data;
    private Format format;

    public WebResponse(String data){
        Log.d(TAG, "[WebResponse] - new : "+data);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Format getFormat() {
        return format;
    }

    @Override
    public String toString(){
        return data;
    }
}
