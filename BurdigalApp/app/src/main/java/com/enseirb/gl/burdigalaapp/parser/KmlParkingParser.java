package com.enseirb.gl.burdigalaapp.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.dto.PointS;

/**
 * Created by alraffin on 23/11/15.
 */
public class KmlParkingParser implements IParkingParser{
    public static ArrayList<ParkingDTO> parse(String allFile) {
        String CDATA = null;
        Document doc = CommonParser.createDocument(allFile);
        ArrayList<ParkingDTO> parkings = null;

        if (doc != null) {

            NodeList nameNodeList = doc.getElementsByTagName("name");
            NodeList pointNodeList = doc.getElementsByTagName("Point");

            parkings = new ArrayList<ParkingDTO>();
            Map parkingDescription = new HashMap();

            for (int i = 0; i < pointNodeList.getLength(); i++) {
                Element CDATALine = (Element) nameNodeList.item(i + 1);
                CDATA = CommonParser.getCharacterDataFromElement(CDATALine);
                parkingDescription = CommonParser.parseCDATA(CDATA);
                Node nNode = pointNodeList.item(i);
                Element eElement = (Element) nNode;
                String coordinates = eElement.getElementsByTagName("coordinates").item(0).getTextContent();
                String[] parts = coordinates.trim().split(",");

                String x = parts[1];
                String y = parts[0];
                String name = parkingDescription.get("nom").toString();
                String numberOfSpots = parkingDescription.get("nombre de places").toString();
                String type = parkingDescription.get("type de parking").toString();

                parkings.add(new ParkingDTO(name, numberOfSpots, type, new PointS(x, y)));
                //return parkings;
            }
        }
        return parkings;
    }
}
