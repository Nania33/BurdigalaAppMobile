package com.enseirb.gl.burdigalaapp.Florian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by flo on 23/11/15.
 */
public class IBordeauxDAO {
    private final static String ERROR1 = "URL is not correct";
    private final static String ERROR2 = "Problem with the protocol";
    private final static String ERROR3 = "General I/O exception: ";
    private final static String URL1 = "http://odata.bordeaux.fr/v1/databordeaux/";
    private final static String URL2 = "/?format=kml";
    private final static String GET = "GET";


    /*
	 * possibilities for service :
	 * sigsanitaire
	 * parcjardin
	 * sigparkpub
	 * sigstavelo
	 */
    protected static String query(String service){
        try{
            String url = URL1 + service + URL2;
            InputStream is = connectTo(url);
            String result = convertToString(is);
            return result;
        } catch (MalformedURLException e) {
            System.out.println(ERROR1 + e.getMessage());
        } catch(ProtocolException e){
            System.out.println(ERROR2 + e.getMessage());
        } catch (IOException e){
            System.out.println(ERROR3 + e.getMessage());
        }
        return null;
    }


    public static InputStream connectTo(String urltoParse) throws IOException {
        InputStream is;
        URL url = new URL(urltoParse);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(GET);
        is = conn.getInputStream();
        return is;

    }

    public static String convertToString(InputStream input) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        String is = builder.toString();
        return is;
    }
}
