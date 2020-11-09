package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patientcommands.PatientAddCommand;
import seedu.address.logic.parser.patientparser.AddCommandParser;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PatientBuilder;

public class PatientAddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatient));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatient));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatient));

        // multiple tags - all accepted
        Patient expectedPatientMultipleTags = new PatientBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new PatientAddCommand(expectedPatientMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Patient expectedPatient = new PatientBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + GENDER_DESC_AMY
                        + BIRTHDATE_DESC_AMY + BLOODTYPE_DESC_AMY
                        + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new PatientAddCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PatientAddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage + "\n" + "Missing fields: NAME.");

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage + "\n" + "Missing fields: PHONE.");

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage + "\n" + "Missing fields: EMAIL.");

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage + "\n" + "Missing fields: ADDRESS.");

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage + "\n" + "Missing fields: NAME, PHONE, EMAIL, ADDRESS.");
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + GENDER_DESC_BOB
                + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + GENDER_DESC_BOB
                        + BIRTHDATE_DESC_BOB + BLOODTYPE_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PatientAddCommand.MESSAGE_USAGE));
    }
}
