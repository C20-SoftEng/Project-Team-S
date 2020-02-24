package edu.wpi.cs3733.c20.teamS.utilities;


import twitter4j.*;

import java.util.LinkedList;
import java.util.List;

public class Tweetbox {

    public static List<String> getTweets(String usernameWithAt){
        List<String> stringStatus = new LinkedList<String>();
        try{
            Twitter twitter = new TwitterFactory().getInstance();
            //User user = twitter.verifyCredentials();
            //String handle = "@FaulknerHosp";
            String handle = usernameWithAt;
            List<Status> statuses = twitter.getUserTimeline(handle);

            System.out.println("Showing @" + handle+ "'s home timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                stringStatus.add("@" + status.getUser().getScreenName() + " - " + status.getText());
            }

        }catch(TwitterException e){
            System.out.println(e.getMessage());
        }
        return stringStatus;
    }


}
