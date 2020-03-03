package edu.wpi.cs3733.c20.teamS.utilities;

import java.util.List;

public class SendTextDirectionsThread extends Thread {
    List<String> directions;
    String number;
    String carrier;

    public SendTextDirectionsThread(List<String> directions, String number, String carrier){
        this.directions = directions;
        this.number = number;
        this.carrier = carrier;

    }

    @Override
    public void run() {
        String allDirections = "";
        for(String d : directions){
            allDirections = allDirections + d +" \n ";
        }
        Mailer.sendTextToCarrier(allDirections,"Directions from Faulkner Hospital",number,carrier);

    }
}
