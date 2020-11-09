package seedu.address.logic;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Thread used to check for newly missed appointments at regular intervals.
 */
public class TimerThread extends Thread {

    private Logic logic;
    private Boolean running;

    /**
     * Constructor for thread.
     * @param logic
     */
    public TimerThread(Logic logic) {
        this.logic = logic;
        this.running = true;
    }

    /**
     * Executes the AppointmentNesMissesCommand at one minute intervals.
     */
    public void run() {
        while (running) {
            try {
                logic.checkNewlyMissedAppointments();
            } catch (CommandException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 12; i++) {
                if (!running) {
                    break;
                } else {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void setStop() {
        this.running = false;
    }
}
