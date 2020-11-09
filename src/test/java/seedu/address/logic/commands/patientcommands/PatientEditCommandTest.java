package seedu.address.logic.commands.patientcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.patientcommands.PatientEditCommand.EditPatientDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for PatientEditCommand.
 */
public class PatientEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        PatientEditCommand patientEditCommand = new PatientEditCommand(INDEX_FIRST_PATIENT, descriptor);

        String expectedMessage = String.format(PatientEditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(patientEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPatient = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient lastPatient = model.getFilteredPatientList().get(indexLastPatient.getZeroBased());

        PatientBuilder personInList = new PatientBuilder(lastPatient);
        Patient editedPatient = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        PatientEditCommand patientEditCommand = new PatientEditCommand(indexLastPatient, descriptor);

        String expectedMessage = String.format(PatientEditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(lastPatient, editedPatient);

        assertCommandSuccess(patientEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        PatientEditCommand patientEditCommand =
                                        new PatientEditCommand(INDEX_FIRST_PATIENT, new EditPatientDescriptor());
        Patient editedPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());

        String expectedMessage = String.format(PatientEditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(patientEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient patientInFilteredList = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(patientInFilteredList).withName(VALID_NAME_BOB).build();
        PatientEditCommand patientEditCommand = new PatientEditCommand(INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(PatientEditCommand.MESSAGE_EDIT_PATIENT_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(patientEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePatientUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(firstPatient).build();
        PatientEditCommand patientEditCommand = new PatientEditCommand(INDEX_SECOND_PATIENT, descriptor);

        assertCommandFailure(patientEditCommand, model, PatientEditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        // edit patient in filtered list into a duplicate in address book
        Patient patientInList = model.getAddressBook().getPatientList().get(INDEX_SECOND_PATIENT.getZeroBased());
        PatientEditCommand patientEditCommand = new PatientEditCommand(INDEX_FIRST_PATIENT,
                new EditPatientDescriptorBuilder(patientInList).build());

        assertCommandFailure(patientEditCommand, model, PatientEditCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        PatientEditCommand patientEditCommand = new PatientEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(patientEditCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPatientIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPatientList().size());

        PatientEditCommand patientEditCommand = new PatientEditCommand(outOfBoundIndex,
                new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(patientEditCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PatientEditCommand standardCommand = new PatientEditCommand(INDEX_FIRST_PATIENT, DESC_AMY);

        // same values -> returns true
        EditPatientDescriptor copyDescriptor = new EditPatientDescriptor(DESC_AMY);
        PatientEditCommand commandWithSameValues = new PatientEditCommand(INDEX_FIRST_PATIENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new PatientEditCommand(INDEX_SECOND_PATIENT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new PatientEditCommand(INDEX_FIRST_PATIENT, DESC_BOB)));
    }

}
