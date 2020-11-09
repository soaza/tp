package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.DESCRIPTION_DESC_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.DESCRIPTION_DESC_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.END_DESC_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.END_DESC_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.INVALID_END_DESC;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.INVALID_PATIENT_DESC;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.INVALID_START_DESC;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.PATIENT_DESC_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.PATIENT_DESC_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.START_DESC_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.START_DESC_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.TAG_DESC_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.TAG_DESC_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_PATIENT_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_PATIENT_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_TWO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand;
import seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.appointmentparser.AppointmentEditCommandParser;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class AppointmentEditCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentEditCommand.MESSAGE_USAGE);

    private AppointmentEditCommandParser parser = new AppointmentEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, START_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AppointmentEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + START_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + START_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid start time
        assertParseFailure(parser, "1" + INVALID_START_DESC, AppointmentTime.MESSAGE_CONSTRAINTS);
        // invalid end time
        assertParseFailure(parser, "1" + INVALID_END_DESC, AppointmentTime.MESSAGE_CONSTRAINTS);
        // invalid patient
        assertParseFailure(parser, "1" + INVALID_PATIENT_DESC, MESSAGE_INVALID_INDEX);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        // invalid start followed by valid end
        assertParseFailure(parser, "1" + INVALID_START_DESC + END_DESC_ONE,
                AppointmentTime.MESSAGE_CONSTRAINTS);

        // valid start followed by invalid start. The test case for invalid start followed by valid start
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + START_DESC_ONE + INVALID_START_DESC,
                AppointmentTime.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Appointment} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_ONE + TAG_DESC_TWO + TAG_EMPTY,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_ONE + TAG_EMPTY + TAG_DESC_TWO,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_ONE + TAG_DESC_TWO,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_START_DESC + INVALID_TAG_DESC + INVALID_PATIENT_DESC,
                AppointmentTime.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + START_DESC_ONE + END_DESC_ONE + DESCRIPTION_DESC_ONE
                + PATIENT_DESC_ONE + TAG_DESC_ONE;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE)
                .withDescription(VALID_DESCRIPTION_ONE)
                .withPatient(VALID_PATIENT_ONE)
                .withTags(VALID_TAG_ONE).build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + START_DESC_ONE + DESCRIPTION_DESC_ONE + END_DESC_ONE;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE)
                .withDescription(VALID_DESCRIPTION_ONE).build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // start time & end time
        Index targetIndex = INDEX_THIRD_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + START_DESC_ONE + END_DESC_ONE;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE).build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_ONE;
        descriptor = new EditAppointmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_ONE).build();
        expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // patient
        userInput = targetIndex.getOneBased() + PATIENT_DESC_ONE;
        descriptor = new EditAppointmentDescriptorBuilder().withPatient(VALID_PATIENT_ONE).build();
        expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_ONE;
        descriptor = new EditAppointmentDescriptorBuilder().withTags(VALID_TAG_ONE).build();
        expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + PATIENT_DESC_ONE + DESCRIPTION_DESC_ONE + END_DESC_ONE
                + TAG_DESC_ONE + DESCRIPTION_DESC_TWO + START_DESC_ONE + PATIENT_DESC_ONE + TAG_DESC_ONE
                + START_DESC_TWO + END_DESC_TWO + PATIENT_DESC_TWO + TAG_DESC_TWO;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_TWO, VALID_END_TWO)
                .withDescription(VALID_DESCRIPTION_TWO)
                .withPatient(VALID_PATIENT_TWO)
                .withTags(VALID_TAG_TWO, VALID_TAG_ONE).build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + INVALID_START_DESC + START_DESC_ONE;
        EditAppointmentDescriptor descriptor =
                new EditAppointmentDescriptorBuilder().withStartTime(VALID_START_ONE).build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_START_DESC + START_DESC_ONE + END_DESC_ONE
                + DESCRIPTION_DESC_ONE;
        descriptor = new EditAppointmentDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_ONE)
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE).build();
        expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().withTags().build();
        AppointmentEditCommand expectedCommand = new AppointmentEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
