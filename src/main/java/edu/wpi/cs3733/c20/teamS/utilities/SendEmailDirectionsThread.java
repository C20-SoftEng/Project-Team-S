package edu.wpi.cs3733.c20.teamS.utilities;

import java.util.List;
import java.util.Set;

public class SendEmailDirectionsThread extends Thread {
    List<String> directions;
    String email;
    //String carrier;

    public SendEmailDirectionsThread(List<String> directions, String email){
        this.directions = directions;
        this.email = email;

    }

    @Override
    public void run() {
        String allDirections = "";
        for(String d : directions){
            allDirections = allDirections + d +" - ";
        }
        Mailer.sendMail(allDirections,"Directions from Faulkner Hospital",email);

    }
}
