package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RedoCommand;

public class RedoCommandParserTest {

    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_invalid_throwsParseException() {
        assertParseFailure(parser, "some random word",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            RedoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        // no leading and trailing whitespaces
        RedoCommand expectedRedoCommand =
                new RedoCommand();
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "",
                expectedRedoCommand);
    }

}
