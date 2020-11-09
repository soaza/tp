package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentTodayFilter;
import seedu.address.testutil.TypicalAppointments;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AppointmentTodayCommand.
 */
public class AppointmentTodayCommandTest {

    private Model model = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());
    private Model model2 = new ModelManager(TypicalAppointments.getTypicalAddressBookForEdit(), new UserPrefs());
    private Model expectedModel2 = new ModelManager(TypicalAppointments.getTypicalAddressBookForEdit(),
            new UserPrefs());

    @Test
    public void equals() {
        SearchAppointmentTodayFilter firstPredicate =
                new SearchAppointmentTodayFilter();
        SearchAppointmentTodayFilter secondPredicate =
                new SearchAppointmentTodayFilter();

        AppointmentTodayCommand findFirstCommand = new AppointmentTodayCommand(firstPredicate);
        AppointmentTodayCommand findSecondCommand = new AppointmentTodayCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        AppointmentTodayCommand findFirstCommandCopy = new AppointmentTodayCommand(firstPredicate);
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
        SearchAppointmentTodayFilter predicate = new SearchAppointmentTodayFilter();
        AppointmentTodayCommand command = new AppointmentTodayCommand(predicate);
        expectedModel2.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model2, expectedMessage, expectedModel2);
        assertEquals(Collections.emptyList(), model2.getFilteredAppointmentList());
    }


    //    @Test
    //    public void execute_oneAppointmentFound() {
    //        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 1);
    //        SearchAppointmentTodayFilter predicate = new SearchAppointmentTodayFilter();
    //        AppointmentTodayCommand command = new AppointmentTodayCommand(predicate);
    //        expectedModel.updateFilteredAppointmentList(predicate);
    //        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    //        assertEquals(Arrays.asList(TODAY_APPOINTMENT), model.getFilteredAppointmentList());
    //    }
}
