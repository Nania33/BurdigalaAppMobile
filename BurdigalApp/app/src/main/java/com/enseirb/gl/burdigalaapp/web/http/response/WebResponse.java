package com.enseirb.gl.burdigalaapp.web.http.response;

/**
 * Created by rchabot on 17/11/15.
 */
public class WebResponse {
    private String data;

    public WebResponse(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
