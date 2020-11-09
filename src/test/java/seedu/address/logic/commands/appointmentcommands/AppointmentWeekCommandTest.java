package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.filters.appointmentfilters.SearchAppointmentWeekFilter;
import seedu.address.testutil.TypicalAppointments;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AppointmentWeekCommand.
 */
public class AppointmentWeekCommandTest {

    private Model model = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());
    private Model model2 = new ModelManager(TypicalAppointments.getTypicalAddressBookForEdit(), new UserPrefs());
    private Model expectedModel2 = new ModelManager(TypicalAppointments.getTypicalAddressBookForEdit(),
            new UserPrefs());


    @Test
    public void equals() {
        SearchAppointmentWeekFilter firstPredicate =
                new SearchAppointmentWeekFilter();
        SearchAppointmentWeekFilter secondPredicate =
                new SearchAppointmentWeekFilter();

        AppointmentWeekCommand findFirstCommand = new AppointmentWeekCommand(firstPredicate);
        AppointmentWeekCommand findSecondCommand = new AppointmentWeekCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        AppointmentWeekCommand findFirstCommandCopy = new AppointmentWeekCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different patient -> returns false
        assertTrue(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0);
        SearchAppointmentWeekFilter predicate = new SearchAppointmentWeekFilter();
        AppointmentWeekCommand command = new AppointmentWeekCommand(predicate);
        expectedModel2.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model2, expectedMessage, expectedModel2);
        assertEquals(Collections.emptyList(), model2.getFilteredAppointmentList());
    }

    @Test
    public void execute_oneAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 3);
        SearchAppointmentWeekFilter predicate = new SearchAppointmentWeekFilter();
        AppointmentWeekCommand command = new AppointmentWeekCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        List<Appointment> appointmentArrayList =
                Arrays.asList(TODAY_APPOINTMENT, THIS_WEEK_SATURDAY_APPOINTMENT, THIS_WEEK_SUNDAY_APPOINTMENT);
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
}
