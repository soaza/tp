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
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.TypicalAppointments;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AppointmentDeleteCommand}.
 */
public class AppointmentDeleteCommandTest {

    private Model model = new ModelManager(TypicalAppointments.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        AppointmentDeleteCommand appointmentDeleteCommand = new AppointmentDeleteCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(AppointmentDeleteCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToDelete);

        AppointmentCommandTestUtil.assertCommandSuccess(appointmentDeleteCommand, model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        AppointmentDeleteCommand appointmentDeleteCommand = new AppointmentDeleteCommand(outOfBoundIndex);

        AppointmentCommandTestUtil.assertCommandFailure(appointmentDeleteCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getAppointmentList().size());

        AppointmentDeleteCommand appointmentDeleteCommand = new AppointmentDeleteCommand(outOfBoundIndex);

        AppointmentCommandTestUtil.assertCommandFailure(appointmentDeleteCommand, model,
                Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        AppointmentDeleteCommand deleteFirstCommand = new AppointmentDeleteCommand(INDEX_FIRST_APPOINTMENT);
        AppointmentDeleteCommand deleteSecondCommand = new AppointmentDeleteCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        AppointmentDeleteCommand deleteFirstCommandCopy = new AppointmentDeleteCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(p -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }
}
