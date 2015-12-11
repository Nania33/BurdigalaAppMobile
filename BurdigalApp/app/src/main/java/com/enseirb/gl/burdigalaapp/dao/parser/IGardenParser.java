package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;

import java.util.ArrayList;

public interface IGardenParser extends IModelParser {
    public ArrayList<GardenDTO> parse(String allFile);
}
