/*
 * Course: SWE2410-121
 * Fall 2024-2025
 * File header contains class DiningRoom
 * Name: Sam Lim
 * Created 11/12/2024
 */
import javafx.scene.layout.AnchorPane;
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
    AnchorPane pane = new AnchorPane();
    private final LinkedList<Table> tables = new LinkedList<>();

    public DiningRoom(int tables, int bars) {
        for (int i = 0; i < tables; i++) {
            addTable(new Table());
        }
        for (int i = 0; i < bars; i++) {
            addTable(new Table(config.barSeats, true));
        }

        this.pane.setPrefWidth(750);
    }

    public DiningRoom(){
        this(config.tables,config.bars);
    }

    public LinkedList<Table> getTables(){
        return tables;
    }

    private static final double MIN_CELL_WIDTH = 60;
    private static final double MIN_CELL_HEIGHT = 65;
    private static final double MAX_ROW_LENGTH = 750;
    private static final double ROW_HGAP = 5;
    private double rowLength = 0;
    private double currentRowHeight = 0;

    /**
     * Adds a table to the dining room and displays it in a FlowPane like way
     * Its necessary to use an AnchorPane so that the Host can move to location of the Table; When a FlowPane was used,
     * all Tables had the same position making it impossible to move the Host to the Table
     * @param table the table to be displayed
     */
    private void addTable(Table table){
        tables.add(table);
        double cellWidth = Math.max(table.getPane().getWidth(), MIN_CELL_WIDTH);
        if(cellWidth + rowLength+ROW_HGAP > MAX_ROW_LENGTH){
            currentRowHeight += MIN_CELL_HEIGHT;
            rowLength = 0;
        }
        table.getPane().setLayoutX(rowLength + ROW_HGAP);
        rowLength+=cellWidth + ROW_HGAP;
        System.out.println(rowLength);

        table.getPane().setLayoutY(currentRowHeight);

        pane.getChildren().add(table.getPane());
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
