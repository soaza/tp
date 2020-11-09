package seedu.address.logic.parser.patientparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.patientcommands.PatientAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.BloodType;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new PatientAddCommand object
 */
public class AddCommandParser implements Parser<PatientAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PatientAddCommand
     * and returns an PatientAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PatientAddCommand parse(String args) throws ParseException {
        boolean hasMissingFields = false;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER,
                            PREFIX_BIRTHDATE, PREFIX_BLOODTYPE,
                            PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GENDER,
                    PREFIX_BIRTHDATE, PREFIX_BLOODTYPE,
                    PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            ArrayList<String> missingFieldsList = new ArrayList<>();
            if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
                missingFieldsList.add("NAME");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_GENDER)) {
                missingFieldsList.add("GENDER");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_BIRTHDATE)) {
                missingFieldsList.add("BIRTHDATE");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_BLOODTYPE)) {
                missingFieldsList.add("BLOODTYPE");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
                missingFieldsList.add("PHONE");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
                missingFieldsList.add("EMAIL");
                hasMissingFields = true;
            }
            if (!arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
                missingFieldsList.add("ADDRESS");
                hasMissingFields = true;
            }
            if (hasMissingFields) {
                String missingFieldsString = "Missing fields: ";
                for (int i = 0; i < missingFieldsList.size(); i++) {
                    String currentMissedField = missingFieldsList.get(i);
                    if (i == missingFieldsList.size() - 1) {
                        missingFieldsString = missingFieldsString + currentMissedField + ".";
                    } else {
                        missingFieldsString = missingFieldsString + currentMissedField + ", ";
                    }
                }
                String modifiedInvalidMessageCommand = MESSAGE_INVALID_COMMAND_FORMAT + '\n' + missingFieldsString;
                throw new ParseException(String.format(modifiedInvalidMessageCommand, PatientAddCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                PatientAddCommand.MESSAGE_USAGE));
            }
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());
        Birthdate birthdate = ParserUtil.parseBirthdate(argMultimap.getValue(PREFIX_BIRTHDATE).get());
        BloodType bloodtype = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_BLOODTYPE).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = new Remark(""); // add command does not allow adding remarks straight away
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Patient patient = new Patient(name, gender, birthdate, bloodtype, phone, email, address, remark, tagList);

        return new PatientAddCommand(patient);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
