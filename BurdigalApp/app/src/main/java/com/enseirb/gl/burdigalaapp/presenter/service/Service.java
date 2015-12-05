package com.enseirb.gl.burdigalaapp.presenter.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.enseirb.gl.burdigalaapp.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rchabot on 21/11/15.
 */
public class Service implements Parcelable {
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IS_SELECTED = "is_selected";

    private String name;
    private String description;
    private boolean isSelected;
    private int color;
    private ServiceType type;

    public Service(ServiceType type, String name, String description, int color) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isSelected = false;
        this.color = color;
    }

    protected Service(Parcel in) {
        name = in.readString();
        description = in.readString();
        isSelected = in.readByte() != 0;
        color = in.readInt();
        type = ServiceType.toServiceType(in.readString());
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

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

    public ServiceType getType() {
        return type;
    }

    public BitmapDescriptor getMarkerIcon(){
        switch (this.getType()){
            case TOILET:
                return BitmapDescriptorFactory.fromResource(R.drawable.ic_toilet_marker);
            case GARDEN:
                return BitmapDescriptorFactory.fromResource(R.drawable.marker_garden);
            case CYCLEPARK:
                return BitmapDescriptorFactory.fromResource(R.drawable.marker_cyclepark);
            case PARKING:
                return BitmapDescriptorFactory.fromResource(R.drawable.marker_parking);
            default:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(color);
        dest.writeString(type.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;

        Service service = (Service) o;

        if (isSelected != service.isSelected) return false;
        if (color != service.color) return false;
        if (!name.equals(service.name)) return false;
        if (description != null ? !description.equals(service.description) : service.description != null)
            return false;
        return type == service.type;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
