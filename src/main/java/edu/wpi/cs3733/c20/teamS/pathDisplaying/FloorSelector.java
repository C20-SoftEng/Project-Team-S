package edu.wpi.cs3733.c20.teamS.pathDisplaying;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.c20.teamS.ThrowHelper;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

class FloorSelector {
    private final JFXButton upButton;
    private final JFXButton downButton;
    private final Floor[] floors;
    private final PublishSubject<Integer> currentChanged = PublishSubject.create();
    private int current;
    private static final String UNSELECTED_BUTTON_STYLE = "-fx-background-color: #ffffff; -fx-font: 22 System;";
    private static final String SELECTED_BUTTON_STYLE = "-fx-background-color: #f6bd38; -fx-font: 32 System;";
    private final int lowestFloorNumber = 1;
    private final int highestFloorNumber;

    public FloorSelector(JFXButton upButton, JFXButton downButton, Floor... floors) {
        this.upButton = upButton;
        this.downButton = downButton;
        this.floors = floors;
        this.highestFloorNumber = floors.length;
    }

    /**
     * Gets the currently-selected floor number.
     * @return
     */
    public int current() {
        return this.current;
    }
    /**
     * Sets the currently-selected floor number. Floor numbers start at 1.
     * @param floorNumber The floor number to select.
     */
    public void setCurrent(int floorNumber) {
        if (floorNumber < lowestFloorNumber || floorNumber > highestFloorNumber)
            ThrowHelper.outOfRange("floorNumber", lowestFloorNumber, highestFloorNumber);
        int previous = this.current;
        this.current = floorNumber;
        updateFloorButtons(floorNumber);
//            updateMapPanPosition(floorNumber);
        if (previous != this.current)
            currentChanged.onNext(this.current);
    }
    public Observable<Integer> currentChanged() {
        return currentChanged;
    }

    private void updateFloorButtons(int floorNumber) {
        for (Floor floor : this.floors)
            floor.button.setStyle(UNSELECTED_BUTTON_STYLE);

        floor(floorNumber).button.setStyle(SELECTED_BUTTON_STYLE);
        this.upButton.setDisable(floorNumber == highestFloorNumber);
        this.downButton.setDisable(floorNumber == lowestFloorNumber);
    }
    public Floor floor(int floorNumber) {
        return floors[floorNumber - 1];
    }
}
