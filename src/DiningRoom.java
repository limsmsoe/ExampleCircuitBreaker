/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class DiningRoom
 * Name: Sam Lim
 * Created 11/12/2024
 */
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

/**
 * Course SWE2410-121
 * Fall 2024-2025
 * Class DiningRoom Purpose: A simulation of a dining room in a restaurant that has a variety of tables
 *
 * @author Sam Lim
 * @version created on 10/11/2024 9:20am
 */
public class DiningRoom extends SimulatedObject{
    FlowPane pane = new FlowPane();
    private final LinkedList<Table> tables = new LinkedList<>();

    public DiningRoom(int tables, int bars) {
        for (int i = 0; i < tables; i++) {
            this.tables.add(new Table());
        }
        for (int i = 0; i < bars; i++) {
            this.tables.add(new Table(config.barSeats, true));
        }

        this.pane.setPrefWidth(750);
        this.pane.setHgap(5);
        this.pane.setVgap(5);
        this.pane.setPrefWrapLength(750);


        this.tables.forEach(table -> pane.getChildren().add(table.getPane()));
    }

    public DiningRoom(){
        this(config.tables,config.bars);
    }

    public LinkedList<Table> getTables(){
        return tables;
    }

    /**
     * Seats a certain number of guests at a table index
     * NOTE THAT THIS METHOD DOES NOT CHECK AVAILABLE SEATS
     * @param table The index of the table to sit the guests at
     * @param seats The number of guests that will be sitting at the table
     */
    public void seatCustomerAtTable(int table, int seats){
        for (int i = 0; i < seats; i++) {
            tables.get(table).seatCustomer(config.seatingDuration);
        }
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
