package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentFindPatientCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentFindPatientCommandParser;
import seedu.address.model.filters.appointmentfilters.SearchPatientFilter;

public class AppointmentFindPatientCommandParserTest {

    private AppointmentFindPatientCommandParser parser = new AppointmentFindPatientCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentFindPatientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentFindPatientCommand() {
        // no leading and trailing whitespaces
        AppointmentFindPatientCommand expectedAppointmentFindPatientCommand =
                new AppointmentFindPatientCommand(new SearchPatientFilter(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedAppointmentFindPatientCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t",
                expectedAppointmentFindPatientCommand);
    }

}
