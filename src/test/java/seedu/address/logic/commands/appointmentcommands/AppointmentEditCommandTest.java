package seedu.address.logic.commands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.DESC_FOLLOWUP;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.DESC_REVIEW;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalAppointments.getTypicalAddressBookForEdit;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand.EditAppointmentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AppointmentEditCommand.
 */
public class AppointmentEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookForEdit(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient patientReplacingCurrentPatient = new PatientBuilder().build();
        model.addPatient(patientReplacingCurrentPatient);

        int index = model.getFilteredPatientList().indexOf(patientReplacingCurrentPatient) + 1;
        String indexString = Integer.toString(index);
        Appointment editedAppointment = new AppointmentBuilder().buildAppointmentForEdit();
        AppointmentEditCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentDescriptorBuilder(editedAppointment).buildDescriptorForEdit(indexString);
        AppointmentEditCommand appointmentEditCommand =
                new AppointmentEditCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        String expectedMessage = String.format(AppointmentEditCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());
        expectedModel.setAppointment(expectedModel.getAddressBook().getAppointmentList().get(0),
                editedAppointment);

        assertCommandSuccess(appointmentEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());
        int patientIndex = model.getFilteredPatientList().indexOf(lastAppointment.getPatient()) + 1;
        String patientIndexString = Integer.toString(patientIndex);
        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);

        Appointment editedAppointment = appointmentInList
                .withDescription(VALID_DESCRIPTION_ONE)
                .withTags(VALID_TAG_TWO)
                .withAppointmentTime(VALID_START_ONE, VALID_END_ONE)
                .build();

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment)
                .buildDescriptorForEdit(patientIndexString);

        AppointmentEditCommand appointmentEditCommand =
                new AppointmentEditCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(AppointmentEditCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                editedAppointment);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAppointment(lastAppointment, editedAppointment);

        assertCommandSuccess(appointmentEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        AppointmentEditCommand.EditAppointmentDescriptor descriptor =
                new EditAppointmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_ONE).build();
        AppointmentEditCommand appointmentEditCommand = new AppointmentEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(appointmentEditCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getAppointmentList().size());

        AppointmentEditCommand appointmentEditCommand = new AppointmentEditCommand(outOfBoundIndex,
                new EditAppointmentDescriptorBuilder().withDescription(VALID_DESCRIPTION_ONE).build());

        assertCommandFailure(appointmentEditCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AppointmentEditCommand standardCommand = new AppointmentEditCommand(INDEX_FIRST_APPOINTMENT, DESC_REVIEW);

        // same values -> returns true
        AppointmentEditCommand.EditAppointmentDescriptor copyDescriptor =
                new AppointmentEditCommand.EditAppointmentDescriptor(DESC_REVIEW);
        AppointmentEditCommand commandWithSameValues =
                new AppointmentEditCommand(INDEX_FIRST_APPOINTMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AppointmentEditCommand(INDEX_SECOND_APPOINTMENT, DESC_REVIEW)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AppointmentEditCommand(INDEX_FIRST_APPOINTMENT, DESC_FOLLOWUP)));
    }

}
