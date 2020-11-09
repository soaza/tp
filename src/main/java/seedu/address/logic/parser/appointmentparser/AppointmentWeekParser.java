package seedu.address.logic.parser.appointmentparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.appointmentcommands.AppointmentWeekCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentWeekFilter;

/**
 * Parses input arguments and creates a new AppointmentTodayCommand object
 */
public class AppointmentWeekParser implements Parser<AppointmentWeekCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PatientFindCommand
     * and returns a PatientFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentWeekCommand parse(String args) throws ParseException {
        if (!args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentWeekCommand.MESSAGE_USAGE));
        }

        return new AppointmentWeekCommand(new SearchAppointmentWeekFilter());

    }

}
