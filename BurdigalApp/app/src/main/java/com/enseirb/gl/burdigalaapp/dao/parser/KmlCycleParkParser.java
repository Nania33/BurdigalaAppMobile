package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.dto.PointS;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KmlCycleParkParser implements ICycleParkParser {

    public ArrayList<CycleParkDTO> parse(String allFile) {
        String CDATA = null;
        Document doc = CommonParser.createDocument(allFile);
        ArrayList<CycleParkDTO> cycleParks = null;

        if (doc != null) {
            NodeList nameNodeList = doc.getElementsByTagName("name");
            NodeList pointNodeList = doc.getElementsByTagName("Point");

            cycleParks = new ArrayList<>();
            Map parcDescription = new HashMap();


            for (int i = 0; i < pointNodeList.getLength(); i++) {
                Element CDATALine = (Element) nameNodeList.item(i + 1);
                CDATA = CommonParser.getCharacterDataFromElement(CDATALine);
                parcDescription = CommonParser.parseCDATA(CDATA);
                Node nNode = pointNodeList.item(i);
                Element eElement = (Element) nNode;
                String coordinates = eElement.getElementsByTagName("coordinates").item(0).getTextContent();

                String[] parts = coordinates.trim().split(",");

                String x = parts[1];
                String y = parts[0];

                String type = parcDescription.get("Type de fixation").toString();
                String spotNumber = parcDescription.get("Nombre").toString();
                cycleParks.add(new CycleParkDTO(type, spotNumber, new PointS(x, y)));
            }
        }
        return cycleParks;
    }
}
