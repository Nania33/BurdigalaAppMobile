package com.enseirb.gl.burdigalapp.web.http.format;

/**
 * Created by rchabot on 17/11/15.
 */
public enum Format {
    JSON, KML;

    public String toString() {
        switch (this) {
            case JSON:
                return "json";
            case KML:
                return "kml";
            default:
                return "json";
        }
    }
}
