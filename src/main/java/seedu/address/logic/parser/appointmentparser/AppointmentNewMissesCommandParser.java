package seedu.address.logic.parser.appointmentparser;

import seedu.address.logic.commands.appointmentcommands.AppointmentNewMissesCommand;
import seedu.address.logic.parser.Parser;

/**
 * Parses input arguments and creates a new AppointmentNewMissesCommand object
 */
public class AppointmentNewMissesCommandParser implements Parser<AppointmentNewMissesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentNewMissesCommand
     * and returns a AppointmentNewMissesCommand object for execution.
     */
    public AppointmentNewMissesCommand parse(String args) {
        return new AppointmentNewMissesCommand();
    }
}
