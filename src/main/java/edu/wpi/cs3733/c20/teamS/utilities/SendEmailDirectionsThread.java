package edu.wpi.cs3733.c20.teamS.utilities;

import java.util.Set;

public class SendEmailDirectionsThread extends Thread {
    Set<String> directions;
    String email;
    //String carrier;

    public SendEmailDirectionsThread(Set<String> directions, String email){
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
