package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentTodayCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentTodayParser;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentTodayFilter;

public class AppointmentTodayParserTest {

    private AppointmentTodayParser parser = new AppointmentTodayParser();

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "some random word",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentTodayCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentTodayCommand() {
        // no leading and trailing whitespaces
        AppointmentTodayCommand expectedAppointmentTodayCommand =
                new AppointmentTodayCommand(new SearchAppointmentTodayFilter());
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "",
                expectedAppointmentTodayCommand);
    }

}
