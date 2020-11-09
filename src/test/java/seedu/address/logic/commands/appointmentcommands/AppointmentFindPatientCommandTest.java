package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.APT2;
import static seedu.address.testutil.TypicalAppointments.NEXT_WEEK_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.THIS_WEEK_SATURDAY_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.THIS_WEEK_SUNDAY_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.TODAY_APPOINTMENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.filters.appointmentfilters.SearchPatientFilter;
import seedu.address.testutil.TypicalAppointments;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AppointmentFindPatientCommand.
 */
public class AppointmentFindPatientCommandTest {

    private Model model = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SearchPatientFilter firstPredicate =
                new SearchPatientFilter(Collections.singletonList("first"));
        SearchPatientFilter secondPredicate =
                new SearchPatientFilter(Collections.singletonList("second"));

        AppointmentFindPatientCommand findFirstCommand = new AppointmentFindPatientCommand(firstPredicate);
        AppointmentFindPatientCommand findSecondCommand = new AppointmentFindPatientCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        AppointmentFindPatientCommand findFirstCommandCopy = new AppointmentFindPatientCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0);
        SearchPatientFilter predicate = preparePredicate(" ");
        AppointmentFindPatientCommand command = new AppointmentFindPatientCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_invalidKeywords_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0);
        SearchPatientFilter predicate = preparePredicate("wazzupdizawrongkeyword");
        AppointmentFindPatientCommand command = new AppointmentFindPatientCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 5);
        SearchPatientFilter predicate = preparePredicate("carl bob");
        AppointmentFindPatientCommand command = new AppointmentFindPatientCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        List<Appointment> appointmentArrayList =
                Arrays.asList(APT2, TODAY_APPOINTMENT, THIS_WEEK_SATURDAY_APPOINTMENT,
                        THIS_WEEK_SUNDAY_APPOINTMENT, NEXT_WEEK_APPOINTMENT);
        appointmentArrayList.sort((a1, a2) -> {
                if (a1.getStartTime().isBefore(a2.getStartTime())) {
                    return -1;
                } else if (a1.getStartTime().isEqual(a2.getStartTime())
                        && a1.getEndTime().isBefore(a2.getEndTime())) {
                    return 0;
                }
                return 1;
            }
        );
        assertEquals(appointmentArrayList, model.getFilteredAppointmentList());
    }

    /**
     * Parses {@code userInput} into a {@code SearchPatientFilter}.
     */
    private SearchPatientFilter preparePredicate(String userInput) {
        return new SearchPatientFilter(Arrays.asList(userInput.split("\\s+")));
    }

}
