package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.database.EmployeeData;

import javax.mail.MessagingException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class Mailer {

    public Mailer(){}

    public static void sendTextToCarrier(String messageToSend, String subject, String phoneNumber, String carrier){
        HashMap<String, String> carrierMap = buildCarrierMap();
        String address = phoneNumber + "@" +carrierMap.get(carrier);
        sendMail(messageToSend,subject,address);

    }

    private static HashMap<String,String> buildCarrierMap(){
        HashMap<String,String> carrierEmailList = new HashMap<>();
        carrierEmailList.put("AT&T","txt.att.net");
        carrierEmailList.put("Verizon","vtext.com");
        carrierEmailList.put("T-Mobile","tmomail.net");
        carrierEmailList.put("Sprint","messaging.sprintpcs.com");
        return carrierEmailList;

    }

    public static void sendMail(String messageToSend, String subject, String emailAddress){

        // Recipient's email ID needs to be mentioned.
        String to = emailAddress;

        // Sender's email ID needs to be mentioned
        String from = "FaulknerHospitalC2020@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("FaulknerHospitalC2020@gmail.com", "SquishySquidwardKiosk2020");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(false);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageToSend);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
