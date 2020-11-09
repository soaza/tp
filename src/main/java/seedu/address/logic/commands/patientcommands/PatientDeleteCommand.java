package seedu.address.logic.commands.patientcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.filters.appointmentfilters.SearchSpecificPatientHashcodeFilter;
import seedu.address.model.patient.Patient;

/**
 * Deletes a patient identified using it's displayed index from the address book.
 */
public class PatientDeleteCommand extends Command {

    public static final String COMMAND_WORD = "p-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by the index number used in the displayed patient list.\n"
            + "*Note: Deleting a patient also deletes any appointments that contains the deleted patient."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PATIENT_SUCCESS = "Deleted Patient: %1$s";

    private final Index targetIndex;

    public PatientDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patientToDelete = lastShownList.get(targetIndex.getZeroBased());

        // patient fields has changed, need to delete appointments that contain the deleted patient
        this.deleteModelAppointments(patientToDelete, model);

        model.deletePatient(patientToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete),
                 false, false, true);
    }

    /**
     * Deletes appointments in given model to reflect changes to edited patient.
     * @param patientToDelete Patient to be deleted from patient list
     * @param model Model with list of appointments to update
     */
    private void deleteModelAppointments(Patient patientToDelete, Model model) {
        assert patientToDelete != null;
        SearchSpecificPatientHashcodeFilter patientFilter =
                new SearchSpecificPatientHashcodeFilter(patientToDelete.hashCode());
        model.updateFilteredAppointmentList(patientFilter);

        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        while (appointmentList.size() != 0) {
            model.deleteAppointment(appointmentList.get(0));
            model.updateFilteredAppointmentList(patientFilter);
        }
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((PatientDeleteCommand) other).targetIndex)); // state check
    }
}
