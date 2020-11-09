package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentIsMissedCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentMissedCommandParser;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentMissedFilter;

public class AppointmentIsMissedCommandParserTest {

    private AppointmentMissedCommandParser parser = new AppointmentMissedCommandParser();

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "some random word",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentIsMissedCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentIsMissedCommand() {
        // no leading and trailing whitespaces
        AppointmentIsMissedCommand expectedAppointmentIsMissedCommand =
                new AppointmentIsMissedCommand(new SearchAppointmentMissedFilter());
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "",
                expectedAppointmentIsMissedCommand);
    }

}
