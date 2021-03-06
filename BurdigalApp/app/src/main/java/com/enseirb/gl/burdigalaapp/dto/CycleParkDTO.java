package com.enseirb.gl.burdigalaapp.dto;

public class CycleParkDTO implements DataDTO {
    private PointS point;
    private String fixationType;
    private String parkingSpotNumber;

    public CycleParkDTO(String fixationType, String parkingSpotNumber, PointS point){
        this.point = point;
        this.fixationType = fixationType;
        this.parkingSpotNumber = parkingSpotNumber;
    }

    @Override
    public String toString(){
        return  point.toString() +
                "Fixation Type: " + fixationType + "\n" +
                "Parking Spot Number: " + parkingSpotNumber + "\n\n";
    }

    public PointS getPoint() {
        return point;
    }

    public String getFixationType() {
        return fixationType;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }
}