package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAppointments;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AppointmentCompleteCommand}.
 */
public class AppointmentCompleteCommandTest {
    private Model model = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());

    /*
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToComplete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment copyOfAppointmentToComplete = new AppointmentBuilder(appointmentToComplete).build();
        AppointmentCompleteCommand appointmentCompleteCommand = new AppointmentCompleteCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(AppointmentCompleteCommand.MESSAGE_COMPLETE_APPOINTMENT_SUCCESS,
                appointmentToComplete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.completeAppointment(copyOfAppointmentToComplete);

        AppointmentCommandTestUtil.assertCommandSuccess(appointmentCompleteCommand, model,
                expectedMessage, expectedModel);
    }
    */



    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        AppointmentCompleteCommand appointmentCompleteCommand = new AppointmentCompleteCommand(outOfBoundIndex);

        AppointmentCommandTestUtil.assertCommandFailure(appointmentCompleteCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getAppointmentList().size());

        AppointmentCompleteCommand appointmentCompleteCommand = new AppointmentCompleteCommand(outOfBoundIndex);

        AppointmentCommandTestUtil.assertCommandFailure(appointmentCompleteCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AppointmentCompleteCommand completeFirstCommand = new AppointmentCompleteCommand(INDEX_FIRST_APPOINTMENT);
        AppointmentCompleteCommand completeSecondCommand = new AppointmentCompleteCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertTrue(completeFirstCommand.equals(completeFirstCommand));

        // same values -> returns true
        AppointmentCompleteCommand completeFirstCommandCopy = new AppointmentCompleteCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(completeFirstCommand.equals(completeFirstCommandCopy));

        // different types -> returns false
        assertFalse(completeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(completeFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(completeFirstCommand.equals(completeSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(p -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }
}
