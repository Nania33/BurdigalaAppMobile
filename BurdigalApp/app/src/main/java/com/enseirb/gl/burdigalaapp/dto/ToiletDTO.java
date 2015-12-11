package com.enseirb.gl.burdigalaapp.dto;

public class ToiletDTO implements DataDTO {
    private PointS point;
    private String address;
    private String neighbourhood;
    private String toiletType;

    public ToiletDTO(String address, String neighbourhood, String toiletType, PointS point){
        this.point = point;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.toiletType = toiletType;
    }

    @Override
    public String toString(){
        return  point.toString() +
                "address: " + address + "\n" +
                "Neighbourhood: " + neighbourhood + "\n" +
                "Toilet Type: " + toiletType + "\n\n";

    }

    public PointS getPoint() {
        return point;
    }

    public String getAddress() {
        return address;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getToiletType() {
        return toiletType;
    }
}
