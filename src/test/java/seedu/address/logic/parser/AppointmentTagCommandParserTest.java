package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentTagCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentTagCommandParser;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentTagsFilter;

public class AppointmentTagCommandParserTest {

    private AppointmentTagCommandParser parser = new AppointmentTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AppointmentTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsAppointmentTagCommand() {
        // no leading and trailing whitespaces
        AppointmentTagCommand expectedAppointmentTagCommand =
                new AppointmentTagCommand(new SearchAppointmentTagsFilter(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedAppointmentTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t",
                expectedAppointmentTagCommand);
    }

}
