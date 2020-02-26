package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.SendTextDirectionsScreen;

import java.util.Set;

public class SendTextDirectionsThread extends Thread {
    Set<String> directions;
    String number;
    String carrier;

    public SendTextDirectionsThread(Set<String> directions, String number, String carrier){
        this.directions = directions;
        this.number = number;
        this.carrier = carrier;

    }

    @Override
    public void run() {
        String allDirections = "";
        for(String d : directions){
            allDirections = allDirections + d +" - ";
        }
        Mailer.sendTextToCarrier(allDirections,"Directions from Faulkner Hospital",number,carrier);

    }
}
