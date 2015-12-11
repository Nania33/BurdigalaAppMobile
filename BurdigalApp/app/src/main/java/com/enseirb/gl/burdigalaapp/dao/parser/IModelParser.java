package com.enseirb.gl.burdigalaapp.dao.parser;
import com.enseirb.gl.burdigalaapp.dto.DataDTO;

import java.util.ArrayList;

public interface IModelParser <T extends DataDTO> {
    public ArrayList<T> parse(String allFile);
}
