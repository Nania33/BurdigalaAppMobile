package com.enseirb.gl.burdigalaapp.parser;

import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.dto.PointS;
import com.enseirb.gl.burdigalaapp.model.CyclePark;

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
public class KmlCycleParkParser {

    public static ArrayList<CycleParkDTO> parse(String allFile) {
        String CDATA = null;
        Document doc = CommonParser.createDocument(allFile);
        ArrayList<CyclePark> parkings = null;


        ArrayList<CycleParkDTO> twoWheelVehicules = null;
        if (doc != null) {
            NodeList nameNodeList = doc.getElementsByTagName("name");
            NodeList pointNodeList = doc.getElementsByTagName("Point");

            twoWheelVehicules = new ArrayList<CycleParkDTO>();
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

                String type = parcDescription.get("Type de fixation").toString();
                String spotNumber = parcDescription.get("Nombre").toString();
                twoWheelVehicules.add(new CycleParkDTO(type, spotNumber, new PointS(x, y)));
            }
        }
        return twoWheelVehicules;
    }
}
