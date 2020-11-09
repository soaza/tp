package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentWeekCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentWeekParser;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentWeekFilter;

public class AppointmentWeekParserTest {

    private AppointmentWeekParser parser = new AppointmentWeekParser();

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "some random word",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentWeekCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentWeekCommand() {
        // no leading and trailing whitespaces
        AppointmentWeekCommand expectedAppointmentWeekCommand =
                new AppointmentWeekCommand(new SearchAppointmentWeekFilter());
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "",
                expectedAppointmentWeekCommand);
    }

}
