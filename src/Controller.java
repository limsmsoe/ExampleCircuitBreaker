import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller{
    public static Long tick = 0L;
    public AnchorPane root;
    private Host host;
    private DiningRoom diningRoom;
    private final ArrayList<Customer> customers = new ArrayList<>();

    @FXML
    public void initialize(){
        this.host = new Host();
        this.diningRoom = new DiningRoom();
        root.getChildren().addAll(host.getPane(), diningRoom.getPane());
        host.getPane().setLayoutX(150);
        host.getPane().setLayoutY(150);

        diningRoom.getPane().setLayoutX(300);
        diningRoom.getPane().setLayoutY(150);

        System.out.println("Hello world");

        int seatsNeeded = (new Random()).nextInt(10 - 1 + 1) + 1;
        boolean barAcceptable = (new Random()).nextBoolean();
        System.out.printf("Attempting to seat customer: Needs %d seats, barAcceptable: %b%n", seatsNeeded, barAcceptable);
        Customer customer = new Customer(seatsNeeded, barAcceptable);
        host.seatCustomer(customer);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(config.millisecondsPerStep),(e)->{
            SimulatedObject.step();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
