package seedu.address.logic.parser.appointmentparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.appointmentcommands.AppointmentIsCompletedCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentCompletedFilter;

/**
 * Parses input arguments and creates a new AppointmentIsCompletedCommand object
 */
public class AppointmentIsCompletedCommandParser implements Parser<AppointmentIsCompletedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentIsCompletedCommand
     * and returns a AppointmentIsCompletedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentIsCompletedCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentIsCompletedCommand.MESSAGE_USAGE));
        }

        return new AppointmentIsCompletedCommand(new SearchAppointmentCompletedFilter(true));

    }
}
