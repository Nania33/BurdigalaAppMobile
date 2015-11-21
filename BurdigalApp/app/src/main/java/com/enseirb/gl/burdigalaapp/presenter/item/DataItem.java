package com.enseirb.gl.burdigalaapp.presenter.item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rchabot on 21/11/15.
 */
public class DataItem {
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "descritpion";
    public static final String KEY_IS_SELECTED = "is_selected";

    private String name;
    private String descritpion;
    private boolean isSelected;

    public DataItem(String name, String descritpion) {
        this.name = name;
        this.descritpion = descritpion;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public CharSequence toCharSequence(){
        return name;
    }

    public Map<String, String> getMap(){
        Map<String, String> itemMap = new HashMap<>();
        itemMap.put(KEY_NAME, name);
        itemMap.put(KEY_DESCRIPTION, descritpion);
        itemMap.put(KEY_IS_SELECTED, String.valueOf(isSelected));
        return itemMap;
    }

    @Override
    public String toString(){
        return name + " " + isSelected;
    }

    public void select(){
        this.isSelected = true;
    }

    public void unselect(){
        this.isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
