package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;

import java.util.ArrayList;

public interface IParkingParser extends IModelParser{
    public ArrayList<ParkingDTO> parse(String allFile);
}
