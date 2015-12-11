package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.PointS;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alraffin on 23/11/15.
 */
public class KmlToiletParser {
    public static ArrayList<ToiletDTO> parse(String allFile) {
        String CDATA = null;
        Document doc = CommonParser.createDocument(allFile);
        ArrayList<ToiletDTO> toilets = null;

        if (doc != null) {
            NodeList nameNodeList = doc.getElementsByTagName("name");
            NodeList pointNodeList = doc.getElementsByTagName("Point");

            toilets = new ArrayList<ToiletDTO>();
            Map toiletDescription = new HashMap();

            for (int i = 0; i < pointNodeList.getLength(); i++) {
                Element CDATALine = (Element) nameNodeList.item(i + 1);
                CDATA = CommonParser.getCharacterDataFromElement(CDATALine);
                toiletDescription = CommonParser.parseCDATA(CDATA);
                Node nNode = pointNodeList.item(i);
                Element eElement = (Element) nNode;
                String coordinates = eElement.getElementsByTagName("coordinates").item(0).getTextContent();
                String[] parts = coordinates.trim().split(",");

                String x = parts[1];
                String y = parts[0];

                String type = toiletDescription.get("type de toilettes").toString();
                String address = toiletDescription.get("adresse").toString();
                String neighbourhood = toiletDescription.get("quartier").toString();

                toilets.add(new ToiletDTO(address, neighbourhood, type, new PointS(x, y)));
            }
        }
        return toilets;
    }
}

