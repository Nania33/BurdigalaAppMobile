package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;

import java.util.ArrayList;

public interface IToiletParser extends IModelParser{
    public ArrayList<ToiletDTO> parse(String allFile);
}
