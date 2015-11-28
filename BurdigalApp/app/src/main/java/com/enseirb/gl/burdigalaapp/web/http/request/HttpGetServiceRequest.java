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

/**
 * Created by rchabot on 17/11/15.
 */
public class HttpGetServiceRequest implements HttpRequest {
    private static final String TAG = "HTTP_GET_REQUEST";

    private static final String HTTP_GET_METHOD = "GET";
    private final static String OPEN_DATA_URL = "http://odata.bordeaux.fr/v1/databordeaux/";
    private final static String FORMAT_KML = "/?format=kml";
    private static final int TIMEOUT = 2 * 1000; // Milliseconds

    private URL url;

    public HttpGetServiceRequest(TypeOfService service) {
        try {
            this.url = new URL(OPEN_DATA_URL+service.toString()+FORMAT_KML);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public WebResponse executeRequest() {
        Log.d(TAG, "[executeRequest] start - " + url);
        InputStream is = null;
        WebResponse webResponse = new WebResponse(WebResponse.NO_DATA);
        HttpURLConnection connection = null;
        try {
                Log.d(TAG, "[executeRequest] - connected ");
                connection = (HttpURLConnection) url.openConnection();
                //connection.setReadTimeout(TIMEOUT);
                //connection.setConnectTimeout(TIMEOUT);
                connection.setRequestMethod(HTTP_GET_METHOD);
                connection.setDoInput(true);
                connection.connect();
                webResponse = new WebResponse(inputStreamToString(connection.getInputStream()));
               /* webResponse = new WebResponse("<kml xmlns=\"http://www.opengis.net/kml/2.2\">"+
                            "<Document>"+
                            "<name></name>"+
                            "<Placemark xmlns=\"http://www.opengis.net/kml/2.2\">"+
                            "<name>"+
                            "<![CDATA[Nom : Square Paul Antin - Type d'espace : Parc, jardin et square - Usage :  - Type de gestion : Classe 2 : Priorité aux usages récréatifs - Labellisation : ]]>"+
                            "</name>"+
                            "<Point>"+
                            "<coordinates>-0.57024382926165,44.8194923120669</coordinates>"+
                            "</Point>"+
                            "<description>04e29634-9d92-4b03-ad2b-0840cf0be390</description>"+
                            "</Placemark>"+
                            "<Placemark xmlns=\"http://www.opengis.net/kml/2.2\">"+
                            "<name>"+
                            "<![CDATA[Nom : Square Alfred Smith - Type d'espace : Parc, jardin et square - Usage :  - Type de gestion : Classe 2 : Priorité aux usages récréatifs - Labellisation : ]]>"+
                            "</name>"+
                            "<Point>"+
                            "<coordinates>-0.61259594932301,44.8246499654276</coordinates>"+
                            "</Point>"+
                            "<description>0615f41e-2bd5-4d07-bf4d-61dfed07cb29</description>"+
                            "</Placemark>"+
                            "<Placemark xmlns=\"http://www.opengis.net/kml/2.2\">"+
                            "<name>"+
                            "<![CDATA[Nom : Parc Monséjour - Type d'espace : Parc, jardin et square - Usage :  - Type de gestion : Classe 3 : Priorité à la biodiversité - Labellisation : Ecojardin - Refuge LPO]]>"+
                            "</name>"+
                            "<Point>"+
                            "<coordinates>-0.63152446326111,44.8541666320585</coordinates>"+
                            "</Point>"+
                            "<description>062218a6-19aa-452a-8f38-884d6b973d36</description>"+
                            "</Placemark>"+
                            "</Document>"+
                            "</kml>");*/
        } catch (Exception e){
            e.printStackTrace();
            webResponse = new WebResponse(WebResponse.NO_DATA);
        } finally {
            closeInputStream(is);
            Log.d(TAG, "[connectTo] disconnect - ");
            if (connection != null)
                connection.disconnect();
        }
        Log.d(TAG,"[request] end - ");
        return webResponse;
    }

    private InputStream connectTo(URL urlToParse) throws IOException {
        Log.d(TAG, "[connectTo] start - " + urlToParse);
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestMethod(HTTP_GET_METHOD);
            connection.setDoInput(true);
            connection.connect();
            Log.d(TAG, "[connectTo] after connect - " + urlToParse);
            is = connection.getInputStream();
            Log.d(TAG, "[connectTo] after getInputStream - " + urlToParse);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            Log.d(TAG, "[connectTo] disconnect - " + urlToParse);
            if (connection != null)
                connection.disconnect();
        }
        Log.d(TAG, "[connectTo] end - ");
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
        Log.d(TAG, "[inputStreamToString] - start ");
        StringBuilder jsonResponse = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Log.d(TAG, "[inputStreamToString] - boucle while ");
            jsonResponse.append(line);
        }
        Log.d(TAG, "[inputStreamToString] - start ");
        return jsonResponse.toString();
    }
}
