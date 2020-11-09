package seedu.address.logic.parser.appointmentparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.appointmentcommands.AppointmentIsMissedCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentMissedFilter;



/**
 * Parses input arguments and creates a new AppointmentIsMissedCommand object
 */
public class AppointmentMissedCommandParser implements Parser<AppointmentIsMissedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PatientFindCommand
     * and returns a PatientFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentIsMissedCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentIsMissedCommand.MESSAGE_USAGE));
        }

        return new AppointmentIsMissedCommand(new SearchAppointmentMissedFilter());

    }

}
