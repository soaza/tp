package seedu.address.commons;

import java.util.List;
import java.util.Stack;

import javafx.util.Pair;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

public class UserHistoryManager {

    private final Stack<Pair<List<Patient>, List<Appointment>>> userHistory;
    private final Stack<Pair<List<Patient>, List<Appointment>>> redoHistory;

    /**
     * Initialises UserHistoryManager with empty stacks.
     */
    public UserHistoryManager() {
        this.userHistory = new Stack<>();
        this.redoHistory = new Stack<>();
    }

    /**
     * Add initial history to UserHistoryManager
     * @param pair current user history
     */
    public void initialiseHistory(Pair<List<Patient>, List<Appointment>> pair) {
        this.userHistory.add(pair);
    }

    /**
     * Add user history to stack
     * @param p current user history
     */
    public void addHistory(Pair<List<Patient>, List<Appointment>> p) {
        userHistory.add(p);
    }

    /**
     * Undo the user history
     */
    public void undoHistory() {
        this.redoHistory.add(userHistory.peek());
        userHistory.pop();
    }

    /**
     * Redo the user history
     */
    public void redoHistory() {
        this.userHistory.add(redoHistory.peek());
        redoHistory.pop();
    }

    /**
     * Reset the redo history whenever a new command(that is not undo/redo) is called
     */
    public void resetRedoHistory() {
        this.redoHistory.clear();
    }

    public int getUserHistorySize() {
        return userHistory.size();
    }

    public int getRedoHistorySize() {
        return redoHistory.size();
    }

    public Stack<Pair<List<Patient>, List<Appointment>>> getHistory() {
        return userHistory;
    }

    public Stack<Pair<List<Patient>, List<Appointment>>> getRedoHistory() {
        return redoHistory;
    }

    public boolean canUndo() {
        return userHistory.size() > 1;
    }

    public boolean canRedo() {
        return redoHistory.size() > 0;
    }
}
