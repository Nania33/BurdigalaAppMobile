package com.enseirb.gl.burdigalaapp.dto;

public class ParkingDTO implements DataDTO {
    private String name;
    private PointS point;
    private String parkingSpotNumber;
    private String parkingType;

    public ParkingDTO(String name, String parkingSpotNumber, String parkingType, PointS point){
        this.point = point;
        this.name = name;
        this.parkingSpotNumber = parkingSpotNumber;
        this.parkingType = parkingType;
    }

    @Override
    public String toString(){
        return  "name: " + name + "\n" +
                point.toString() +
                "Parking Spot Number: " + parkingSpotNumber + "\n" +
                "Parking Type: " + parkingType + "\n\n";
    }

    public String getName() {
        return name;
    }

    public PointS getPoint() {
        return point;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public String getParkingType() {
        return parkingType;
    }
}
