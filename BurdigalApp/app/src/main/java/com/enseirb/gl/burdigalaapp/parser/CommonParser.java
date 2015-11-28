package com.enseirb.gl.burdigalaapp.parser;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by alraffin on 23/11/15.
 */
public class CommonParser {

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static Map parseCDATA (String CDATA) {
        String[] parts = CDATA.split("-");
        Map toReturn = new HashMap();
        for (int i = 0; i < parts.length; i++) {
            String[] parts2 = parts[i].split(":");
            String key = parts2[0];
            String value = "";

            // format can be key : value : value
            // appending all the values if needed
            for (int j = 1; j < parts2.length; j++)
                value += parts2[j].trim() + ", ";

            key = key.trim();
            value = value.trim().replaceAll(",$", "");
            toReturn.put(key, value);
        }
        return toReturn;
    }

    public static Document createDocument(String toParse){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(toParse);
            ByteArrayInputStream input = null;

            input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
            return builder.parse(input);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
