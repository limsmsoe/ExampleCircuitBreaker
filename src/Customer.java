/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class Customer
 * Name: Sam Lim
 * Created 11/12/2024
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Course SWE2410-121
 * Fall 2024-2025
 * Class Customer Purpose: An instance simulating a group of guests in a restaurant that have specific needs
 *
 * @author Sam Lim
 * @version created on 10/11/2024 9:00am
 */
public class Customer extends SimulatedObject{

    private Pane pane = new Pane();
    private final int seatsNeeded;
    private final boolean barAcceptable;

    public Customer(int seatsNeeded, boolean barAcceptable) {
        this.seatsNeeded = seatsNeeded;
        this.barAcceptable = barAcceptable;
        pane.getChildren().add(new Circle(12.5/2,12.5/2,12.5, Color.GRAY));
    }

    public int getSeatsNeeded(){
        return seatsNeeded;
    }
    public boolean getBarPreference(){
        return barAcceptable;
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void removeObject(){
        ((Pane) pane.getParent()).getChildren().remove(pane);
    }
}
