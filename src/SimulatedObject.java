import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class SimulatedObject {
    private static final HashMap<Long, LinkedList<Runnable>> schedule = new HashMap<>();
    protected void schedule(Long ticksToWait, Runnable function){
        System.out.println("Scheduled a task in " + ticksToWait + " ms");
        schedule.computeIfAbsent(Controller.tick + ticksToWait, k -> new LinkedList<>()).add(function);
    };
    abstract public Pane getPane();
    public static void step() {
        Controller.tick++;
        List<Runnable> tasksToRun = new ArrayList<>();
        schedule.computeIfPresent(Controller.tick, (key, function) -> {
            tasksToRun.addAll(function);
            return null;
        });
        tasksToRun.forEach(Runnable::run);
    }
}
