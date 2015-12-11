package com.enseirb.gl.burdigalaapp.web.http.request;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetServiceRequest implements HttpRequest {
    private static final String TAG = "HTTP_GET_REQUEST";

    private static final String HTTP_GET_METHOD = "GET";
    private final static String OPEN_DATA_URL = "http://odata.bordeaux.fr/v1/databordeaux/";
    private final static String FORMAT_KML = "/?format=kml";
    private static final int TIMEOUT = 2 * 1000; // Milliseconds

    private URL url;
    private HttpURLConnection connection;

    public HttpGetServiceRequest(TypeOfService service) {
        try {
            this.url = new URL(OPEN_DATA_URL+service.toString()+FORMAT_KML);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public WebResponse executeRequest() {
        Log.d(TAG, "[executeRequest] -  start" + url);
        InputStream is = null;
        WebResponse webResponse = new WebResponse(WebResponse.NO_DATA);
        try {
            is = connectTo(url);
            webResponse = new WebResponse(inputStreamToString(is));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeInputStream(is);
            if (connection != null)
                connection.disconnect();
            Log.d(TAG, "[connectTo] - disconnected ");
        }
        Log.d(TAG,"[request] - end");
        return webResponse;
    }

    private InputStream connectTo(URL urlToParse) throws IOException {
        InputStream is = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod(HTTP_GET_METHOD);
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            Log.d(TAG, "[connectTo] - connected " + urlToParse);
        } catch (Exception e){
            e.printStackTrace();
        }
        return is;
    }

    private void closeInputStream(InputStream is){
        try {
            if (is != null)
                is.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private String inputStreamToString(InputStream is) throws IOException{
        StringBuilder jsonResponse = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonResponse.append(line);
        }
        return jsonResponse.toString();
    }
}
