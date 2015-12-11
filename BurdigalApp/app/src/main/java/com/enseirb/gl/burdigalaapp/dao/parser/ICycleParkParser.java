package com.enseirb.gl.burdigalaapp.dao.parser;

import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import java.util.ArrayList;

public interface ICycleParkParser extends IModelParser {
    public ArrayList<CycleParkDTO> parse(String allFile);
}
