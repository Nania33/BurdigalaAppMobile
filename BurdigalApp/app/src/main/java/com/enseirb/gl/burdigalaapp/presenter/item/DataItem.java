package com.enseirb.gl.burdigalaapp.presenter.item;

import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.presenter.choices.Choice;
import com.enseirb.gl.burdigalaapp.presenter.choices.ChoiceEnum;
import com.enseirb.gl.burdigalaapp.presenter.choices.CycleParkChoice;
import com.enseirb.gl.burdigalaapp.presenter.choices.GardenChoice;
import com.enseirb.gl.burdigalaapp.presenter.choices.ParkingChoice;
import com.enseirb.gl.burdigalaapp.presenter.choices.ToiletChoice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rchabot on 21/11/15.
 */
public class DataItem {
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IS_SELECTED = "is_selected";

    private String name;
    private String description;
    private boolean isSelected;
    private int color;

    public DataItem(ChoiceEnum choice) throws UnknownDataException {
        Choice choiceItem;

        switch(choice){
            case TOILET:
                choiceItem = new ToiletChoice();
                break;
            case CYCLEPARK:
                choiceItem = new CycleParkChoice();
                break;
            case GARDEN:
                choiceItem = new GardenChoice();
                break;
            case PARKING:
                choiceItem = new ParkingChoice();
                break;
            default:
                throw new UnknownDataException("Le type " + choice + " n'existe pas");
        }

        this.name = choiceItem.getName();
        this.description = choiceItem.getDescription();
        this.isSelected = false;
        this.color = choiceItem.getBackgroundColor();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBackgroundColor() {
        return color;
    }

    public CharSequence toCharSequence(){
        return name;
    }

    public Map<String, String> getMap(){
        Map<String, String> itemMap = new HashMap<>();
        itemMap.put(KEY_NAME, name);
        itemMap.put(KEY_DESCRIPTION, description);
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
