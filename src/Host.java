/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class Host
 * Name: Sam Lim
 * Created 11/12/2024
 */
import java.util.LinkedList;

/**
 * Course SWE2410-121
 * Fall 2024-2025
 * Class Host Purpose: A simulation of a host in a restaurant that will seat customers
 *
 * @author Sam Lim
 * @version created on 10/11/2024 9:30am
 */
public class Host {
    private final DiningRoom diningRoom = new DiningRoom();
    private Boolean isBusy = false;
    private Boolean diningRoomWasFull = false;

    /**
     * Tries to seat a group of guests
     * @param seatsNeeded How many guests are being seated
     * @param barAcceptable Whether they will sit at a bar
     * @throws InterruptedException Thrown when a threading error occurs
     */
    private void checkSeat(int seatsNeeded, boolean barAcceptable) throws InterruptedException {
        LinkedList<Table> tables = diningRoom.getTables();
        boolean wasEmptyTable = false;
        for (int i = 0; i < tables.size(); i++) {
            Thread.sleep(config.seatCheckTime);
            Table table = tables.get(i);
            if(config.useCircuitBreakerPattern && table.getAvailableSeats() == table.getTotalSeats() && !table.isBar()){
                wasEmptyTable = true;
            }
            if( // Checks if the table is a bar and a bar is acceptable and there are enough seats
                (table.isBar() && barAcceptable && table.getAvailableSeats() >= seatsNeeded) ||
                // Checks if the table is table and has enough seats and no one is sitting at that table
                (table.getAvailableSeats() >= seatsNeeded && table.getAvailableSeats() == table.getTotalSeats() && !table.isBar())
            ){
                diningRoom.seatCustomerAtTable(i, seatsNeeded);
                seatFound = true;
                diningRoomWasFull = false;
                break;
            }
        }
        if(config.useCircuitBreakerPattern && !wasEmptyTable){
            diningRoomWasFull = true;
            System.out.println("Dining room was full");
            new Thread(() -> {
                try {
                    Thread.sleep(config.circuitBreakerOpenTime);
                    diningRoomWasFull = false;
                    System.out.println("Able to check dining room again");
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }).start();
        }

    }
    private boolean seatFound = false;

    /**
     * Attempts to seat a group of guests based on their needs
     * @param customer The customer trying to be seated
     * @return Whether the customer was seated
     * @throws InterruptedException Thrown when a threading error occurs
     */
    public boolean seatCustomer(Customer customer) throws InterruptedException {
        if(!isBusy && !diningRoomWasFull) {
            isBusy = true;
            seatFound = false;

            Thread thread = new Thread(() -> {
                try {
                    this.checkSeat(customer.getSeatsNeeded(), customer.getBarPreference());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();

            thread.join();
            isBusy = false;
            return seatFound;
        } else {
            return false;
        }
    }


}
