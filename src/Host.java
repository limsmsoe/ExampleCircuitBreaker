/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class Host
 * Name: Sam Lim
 * Created 11/12/2024
 */
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;

/**
 * Course SWE2410-121
 * Fall 2024-2025
 * Class Host Purpose: A simulation of a host in a restaurant that will seat customers
 *
 * @author Sam Lim
 * @version created on 10/11/2024 9:30am
 */
public class Host extends SimulatedObject{
    private Pane pane;
    private final DiningRoom diningRoom = new DiningRoom();
    private Boolean isBusy = false;
    private Boolean diningRoomWasFull = false;
    private Boolean wasEmptyTable = true;

    private void scheduledCheckSeat(int tableNum, Customer customer){
        LinkedList<Table> tables = diningRoom.getTables();
        Table table = tables.get(tableNum);
        if(config.useCircuitBreakerPattern && table.getAvailableSeats() == table.getTotalSeats() && !table.isBar()){
            wasEmptyTable = true;
        }

        if( // Checks if the table is a bar and a bar is acceptable and there are enough seats
                (table.isBar() && customer.getBarPreference() && table.getAvailableSeats() >= customer.getSeatsNeeded()) ||
                        // Checks if the table is table and has enough seats and no one is sitting at that table
                        (table.getAvailableSeats() >= customer.getSeatsNeeded() && table.getAvailableSeats() == table.getTotalSeats() && !table.isBar())
        ){
            diningRoom.seatCustomerAtTable(tableNum, customer.getSeatsNeeded());
            diningRoomWasFull = false;
            isBusy = false;
            wasEmptyTable = true;
            System.out.println("Seated customer");
        } else{
            if(tableNum < tables.size() -1){
                schedule(config.seatCheckTime,() -> {
                    scheduledCheckSeat(tableNum+1, customer);
                });
            } else if(config.useCircuitBreakerPattern && !wasEmptyTable){
                    diningRoomWasFull = true;
                    System.out.println("Dining room was full");
                    schedule(config.circuitBreakerOpenTime, () -> {
                        diningRoomWasFull = false;
                        System.out.println("Able to check dining room again");
                    });
                isBusy = false;
            }
        }
    }

    /**
     * Attempts to seat a group of guests based on their needs
     * @param customer The customer trying to be seated
     */
    public void seatCustomer(Customer customer) {
        if(!isBusy && !diningRoomWasFull) {
            isBusy = true;

            schedule(config.seatCheckTime,() -> {
                scheduledCheckSeat(0, customer);
            });
        }
    }

    Host(){
        pane = new Pane();
        pane.getChildren().add(new Circle(12.5/2,12.5/2,12.5, Color.RED));
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
