package seedu.address.logic.commands.patientcommands;

import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.patientcommands.PatientCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for PatientEditCommand.
 */
public class PatientRemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Patient patientToRemark = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());

    private Remark newRemark = new Remark(VALID_REMARK);

    private Remark noRemark = new Remark("");

    private Patient remarkedPatient = new Patient(patientToRemark.getName(), patientToRemark.getGender(),
            patientToRemark.getBirthdate(), patientToRemark.getBloodType(),
            patientToRemark.getPhone(), patientToRemark.getEmail(),
            patientToRemark.getAddress(), newRemark, patientToRemark.getTags());

    @Test
    public void execute_addRemarkSpecifiedUnfilteredList_success() {

        PatientRemarkCommand patientRemarkCommand = new PatientRemarkCommand(INDEX_FIRST_PATIENT, newRemark);

        String expectedMessage = String.format(PatientRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, remarkedPatient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), remarkedPatient);

        assertCommandSuccess(patientRemarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeRemarkSpecifiedUnfilteredList_success() {
        Patient patientToRemoveRemark = remarkedPatient;

        PatientRemarkCommand patientRemarkCommand = new PatientRemarkCommand(INDEX_FIRST_PATIENT, noRemark);

        String expectedMessage = String.format(PatientRemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, patientToRemark);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(0), patientToRemark);

        assertCommandSuccess(patientRemarkCommand, model, expectedMessage, expectedModel);
    }

}
