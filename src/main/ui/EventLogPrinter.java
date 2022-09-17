package ui;

import model.Event;
import model.EventLog;

public class EventLogPrinter {

    // MODIFIES: this
    // EFFECTS: prints out user log of their inputs
    public static void printLog(EventLog eventLog) {
        for (Event event : eventLog) {
            System.out.println(event.toString() + "\n");
        }
    }
}
