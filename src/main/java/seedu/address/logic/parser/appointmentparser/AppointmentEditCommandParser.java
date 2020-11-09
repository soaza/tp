package seedu.address.logic.parser.appointmentparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand.EditAppointmentDescriptor;
import static seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AppointmenttEditCommand object
 */
public class AppointmentEditCommandParser implements Parser<AppointmentEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppointmentEditCommand
     * and returns an AppointmentEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPOINTMENT_START, PREFIX_APPOINTMENT_END, PREFIX_PATIENT,
                        PREFIX_DESCRIPTION, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        if (argMultimap.getValue(PREFIX_APPOINTMENT_START).isPresent()) {
            startTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_START).get());
        }
        if (argMultimap.getValue(PREFIX_APPOINTMENT_END).isPresent()) {
            endTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_END).get());
        }
        editAppointmentDescriptor.setAppointmentTime(startTime, endTime);

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editAppointmentDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        String patientString = null;
        if (argMultimap.getValue(PREFIX_PATIENT).isPresent()) {
            try {
                patientString = ParserUtil.parsePatientString(argMultimap.getValue(PREFIX_PATIENT).get());
            } catch (ParseException e) {
                throw new ParseException(e.getMessage());
            }
            editAppointmentDescriptor.setNeedsParsePatient(true);
            editAppointmentDescriptor.setPatientString(patientString);
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAppointmentDescriptor::setTags);

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new AppointmentEditCommand(index, editAppointmentDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
