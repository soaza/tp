package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.appointmentcommands.AppointmentCompleteCommand;
import seedu.address.logic.parser.appointmentparser.AppointmentCompleteCommandParser;

public class AppointmentCompleteCommandParserTest {

    private AppointmentCompleteCommandParser parser = new AppointmentCompleteCommandParser();

    @Test
    public void parse_validArgs_returnsCompleteCommand() {
        assertParseSuccess(parser, "1", new AppointmentCompleteCommand(INDEX_FIRST_PATIENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCompleteCommand.MESSAGE_USAGE));
    }
}
