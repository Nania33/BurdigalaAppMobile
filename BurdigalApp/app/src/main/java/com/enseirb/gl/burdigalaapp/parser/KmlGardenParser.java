package com.enseirb.gl.burdigalaapp.parser;


import android.util.Log;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.dto.PointS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alraffin on 24/11/15.
 */
public class KmlGardenParser {
    private static final String TAG = "KmlGardenParser";


    public static ArrayList<GardenDTO> parse(String allFile) {
        Log.d(TAG, "[GardenParser] - parse start");
        String CDATA = null;
        Document doc = CommonParser.createDocument(allFile);
        ArrayList<GardenDTO> gardens = null;

        if (doc != null) {

            NodeList nameNodeList = doc.getElementsByTagName("name");
            NodeList pointNodeList = doc.getElementsByTagName("Point");

            gardens = new ArrayList<GardenDTO>();
            Map parcDescription = new HashMap();


            for (int i = 0; i < pointNodeList.getLength(); i++) {
                Element CDATALine = (Element) nameNodeList.item(i + 1);
                CDATA = CommonParser.getCharacterDataFromElement(CDATALine);
                parcDescription = CommonParser.parseCDATA(CDATA);
                Node nNode = pointNodeList.item(i);
                Element eElement = (Element) nNode;
                String coordinates = eElement.getElementsByTagName("coordinates").item(0).getTextContent();

                String[] parts = coordinates.trim().split(",");

                String x = parts[0];
                String y = parts[1];
                String name = parcDescription.get("Nom").toString();
                String type = parcDescription.get("Type d'espace").toString();
                String use = parcDescription.get("Usage").toString();
                String gestionType = parcDescription.get("Type de gestion").toString();
                String label = parcDescription.get("Labellisation").toString();
                gardens.add(new GardenDTO(name, type, use, gestionType, label, new PointS(x, y)));
                // TODO factoriser dans une fonction parseNode + GardenDTOContainer.add plutot que gardenList.add
            }
        }
        Log.d(TAG, "[GardenParser] - parse end");
        return gardens;
    }
}
