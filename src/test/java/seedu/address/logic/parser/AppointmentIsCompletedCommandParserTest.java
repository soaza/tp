package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentIsCompletedCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentIsCompletedCommandParser;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentCompletedFilter;


public class AppointmentIsCompletedCommandParserTest {

    private AppointmentIsCompletedCommandParser parser = new AppointmentIsCompletedCommandParser();

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "some random word",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentIsCompletedCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentIsCompletedCommand() {
        // no leading and trailing whitespaces
        AppointmentIsCompletedCommand expectedAppointmentIsCompletedCommand =
                new AppointmentIsCompletedCommand(new SearchAppointmentCompletedFilter(true));
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "",
                expectedAppointmentIsCompletedCommand);
    }

}
