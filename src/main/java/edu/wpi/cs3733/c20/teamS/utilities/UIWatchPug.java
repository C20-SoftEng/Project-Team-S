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

public class UIWatchPug{
    private final Timeline idleTimeline;
    private final EventHandler userEventHandler;

    public UIWatchPug(Duration timeout, Runnable notifier){
        idleTimeline = new Timeline(new KeyFrame(timeout,e->notifier.run()));
        idleTimeline.setCycleCount(Animation.INDEFINITE);

        userEventHandler = e->notIdle();
        idleTimeline.playFromStart();

    }


    public void notIdle() {
        if (idleTimeline.getStatus() == Animation.Status.RUNNING) {
            idleTimeline.playFromStart();
        }
    }

    public void register(Scene scene, EventType<? extends Event> eventType) {
        scene.addEventFilter(eventType, userEventHandler);
    }

    public void register(Node node, EventType<? extends Event> eventType) {
        node.addEventFilter(eventType, userEventHandler);
    }

    public void unregister(Scene scene, EventType<? extends Event> eventType) {
        scene.removeEventFilter(eventType, userEventHandler);
    }

    public void unregister(Node node, EventType<? extends Event> eventType) {
        node.removeEventFilter(eventType, userEventHandler);
    }
    public void pause(){
        idleTimeline.pause();
    }
    public void play(){
        idleTimeline.playFromStart();
    }




}
