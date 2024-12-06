/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class Table
 * Name: Sam Lim
 * Created 11/12/2024
 */
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Course SWE2410-121
 * Fall 2024-2025
 * Class Table Purpose: A simulation of a table which keeps track of how many guests can sit at a given table
 *
 * @author Sam Lim
 * @version created on 10/11/2024 9:10am
 */
public class Table extends SimulatedObject{
    private Pane pane;
    private final int totalSeats;
    private int availableSeats;
    private final boolean isBar;

    public Table(int totalSeats, boolean isBar) {
        this.totalSeats = totalSeats;
        availableSeats = this.totalSeats;
        this.isBar = isBar;
        this.pane = new Pane();
        if(isBar){
            Rectangle bar = new Rectangle();
            bar.setWidth(50);
            bar.setHeight(30);
            bar.setFill(Color.CYAN);
            this.pane.getChildren().add(bar);
        } else {
            Circle table = new Circle(15,15,30,Color.CYAN);
            this.pane.getChildren().add(table);

        }
    }

    public Table(int totalSeats) {
        this(totalSeats, false);
    }

    public Table() {
        this((new Random()).nextInt(config.tableSeatMax - config.tableSeatMin + 1) + config.tableSeatMin, false);
    }

    /**
     * Seats a single guest for a certain duration of time
     * @param duration how long the guest will occupy the seat at the table
     */
    public void seatCustomer(long duration) {
        if(availableSeats > 0) {
            availableSeats--;
            schedule(duration, () -> {
                availableSeats++;
                System.out.println("Seat opened at table");
            });
        }
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
    public int getTotalSeats() {
        return totalSeats;
    }

    public boolean isBar() {
        return isBar;
    }


    @Override
    public Pane getPane() {
        return pane;
    }

}
