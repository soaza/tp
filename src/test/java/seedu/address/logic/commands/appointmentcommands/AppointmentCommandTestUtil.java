package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.filters.appointmentfilters.SearchPatientFilter;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

/**
 * Contains helper methods for testing appointment commands.
 */
public class AppointmentCommandTestUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    // Current day of the week
    public static final DayOfWeek DAY_OF_WEEK = LocalDateTime.now().getDayOfWeek();

    // Next week start and end date
    public static final String VALID_START_DATE_NEXT_WEEK = FORMATTER.format(LocalDateTime.now()
                .with(TemporalAdjusters.next(DAY_OF_WEEK))
                .with(LocalTime.of(8, 0)));
    public static final String VALID_END_DATE_NEXT_WEEK = FORMATTER.format(LocalDateTime.now()
            .with(TemporalAdjusters.next(DAY_OF_WEEK))
            .with(LocalTime.of(12, 0)));

    // This week Sunday start and end date
    public static final String VALID_START_DATE_THIS_WEEK_SUNDAY = FORMATTER.format(LocalDateTime.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            .with(LocalTime.of(8, 0)));
    public static final String VALID_END_DATE_THIS_WEEK_SUNDAY = FORMATTER.format(LocalDateTime.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
            .with(LocalTime.of(12, 0)));
    // This week Saturday start and end date
    public static final String VALID_START_DATE_THIS_WEEK_SATURDAY = FORMATTER.format(LocalDateTime.now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
            .with(LocalTime.of(8, 0)));
    public static final String VALID_END_DATE_THIS_WEEK_SATURDAY = FORMATTER.format(LocalDateTime.now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
            .with(LocalTime.of(12, 0)));

    // Today start and end date
    public static final String VALID_START_DATE_TODAY = FORMATTER.format(LocalDateTime.now()
            .with(LocalTime.of(8, 0)));
    public static final String VALID_END_DATE_TODAY = FORMATTER.format(LocalDateTime.now()
            .with(LocalTime.of(12, 0)));


    public static final String VALID_START_ONE = "2020-10-07 08:00";
    public static final String VALID_START_TWO = "2020-08-05 14:00";
    public static final String VALID_END_ONE = "2020-10-07 10:00";
    public static final String VALID_END_TWO = "2020-08-05 16:00";
    public static final String VALID_PATIENT_ONE = "0";
    public static final String VALID_PATIENT_TWO = "1";
    public static final String VALID_DESCRIPTION_ONE = "Review Appointment";
    public static final String VALID_DESCRIPTION_TWO = "Followup Appointment";
    public static final String VALID_DESCRIPTION_TODAY = "today";
    public static final String VALID_DESCRIPTION_SUNDAY = "sunday";
    public static final String VALID_DESCRIPTION_SATURDAY = "saturday";
    public static final String VALID_DESCRIPTION_NEXT_WEEK = "nextweek";
    public static final String VALID_TAG_ONE = "Review";
    public static final String VALID_TAG_TWO = "Followup";
    public static final String VALID_TAG_TODAY = "today";
    public static final String VALID_TAG_SUNDAY = "sunday";
    public static final String VALID_TAG_SATURDAY = "saturday";
    public static final String VALID_TAG_NEXT_WEEK = "nextweek";

    public static final String START_DESC_ONE = " " + PREFIX_APPOINTMENT_START + VALID_START_ONE;
    public static final String START_DESC_TWO = " " + PREFIX_APPOINTMENT_START + VALID_START_TWO;
    public static final String END_DESC_ONE = " " + PREFIX_APPOINTMENT_END + VALID_END_ONE;
    public static final String END_DESC_TWO = " " + PREFIX_APPOINTMENT_END + VALID_END_TWO;
    public static final String PATIENT_DESC_ONE = " " + PREFIX_PATIENT + VALID_PATIENT_ONE;
    public static final String PATIENT_DESC_TWO = " " + PREFIX_PATIENT + VALID_PATIENT_TWO;
    public static final String DESCRIPTION_DESC_ONE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ONE;
    public static final String DESCRIPTION_DESC_TWO = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TWO;
    public static final String TAG_DESC_ONE = " " + PREFIX_TAG + VALID_TAG_ONE;
    public static final String TAG_DESC_TWO = " " + PREFIX_TAG + VALID_TAG_TWO;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // Must follow correct date & time format
    public static final String INVALID_START_DESC = " " + PREFIX_APPOINTMENT_START + "08:00 2020-10-07";
    public static final String INVALID_END_DESC = " " + PREFIX_APPOINTMENT_END + "07-10-2020 08:00";
    // Patient id must be a number
    public static final String INVALID_PATIENT_DESC = " " + PREFIX_PATIENT + "A";
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Review*";

    public static final AppointmentEditCommand.EditAppointmentDescriptor DESC_REVIEW;
    public static final AppointmentEditCommand.EditAppointmentDescriptor DESC_FOLLOWUP;

    static {
        DESC_REVIEW = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE)
                .withDescription(VALID_DESCRIPTION_ONE)
                .withPatient(VALID_PATIENT_ONE)
                .withTags(VALID_TAG_ONE).build();
        DESC_FOLLOWUP = new EditAppointmentDescriptorBuilder()
                .withAppointmentTime(VALID_START_TWO, VALID_END_TWO)
                .withDescription(VALID_DESCRIPTION_TWO)
                .withPatient(VALID_PATIENT_TWO)
                .withTags(VALID_TAG_TWO).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered patient list and selected patient in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Appointment> expectedFilteredList = new ArrayList<>(actualModel.getFilteredAppointmentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredAppointmentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the appointment at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAppointmentList().size());

        Appointment appointment = model.getFilteredAppointmentList().get(targetIndex.getZeroBased());
        final String[] splitName = appointment.getPatient().getName().fullName.split("\\s+");
        model.updateFilteredAppointmentList(new SearchPatientFilter(Arrays.asList(splitName)));

        assertEquals(1, model.getFilteredAppointmentList().size());
    }

    //    public static void main(String[] args) {
    //        System.out.println(VALID_START_DATE_NEXT_WEEK + " " + VALID_END_DATE_NEXT_WEEK + "\n"
    //                + VALID_START_DATE_THIS_WEEK_SATURDAY + " " + VALID_END_DATE_THIS_WEEK_SATURDAY + "\n"
    //                + VALID_START_DATE_THIS_WEEK_SUNDAY + " " + VALID_END_DATE_THIS_WEEK_SUNDAY + "\n"
    //                + VALID_START_DATE_TODAY + " " + VALID_END_DATE_TODAY);
    //    }
}
