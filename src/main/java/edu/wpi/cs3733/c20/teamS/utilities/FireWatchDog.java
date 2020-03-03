package edu.wpi.cs3733.c20.teamS.utilities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.Observable;

public class FireWatchDog{
    private final Timeline idleTimeline;
    //private final EventHandler userEventHandler;
    private final Runnable notifier;

    public FireWatchDog(Duration timeout, Runnable notifier){
        this.notifier = notifier;
        idleTimeline = new Timeline(new KeyFrame(timeout,e->notifier.run()));
        idleTimeline.setCycleCount(Animation.INDEFINITE);
        //idleTimeline.getKeyFrames().
        //userEventHandler = e->notIdle();
        idleTimeline.playFromStart();

    }

    public void changeTimeout(int duration){
        this.idleTimeline.getKeyFrames().clear();
        this.idleTimeline.getKeyFrames().add(new KeyFrame(new Duration(duration),e->notifier.run()));
    }


    public void pause(){
        idleTimeline.pause();
    }
    public void play(){
        idleTimeline.playFromStart();
    }




}

