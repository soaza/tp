package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_APPOINTMENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the address book.
 */
public class AppointmentScheduleCommand extends Command {

    public static final String COMMAND_WORD = "a-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an appointment to Archangel.\n"
            + "Parameters: "
            + PREFIX_APPOINTMENT_START + "START "
            + PREFIX_APPOINTMENT_END + "END "
            + PREFIX_PATIENT + "PATIENT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPOINTMENT_START + "2020-12-25 08:00 "
            + PREFIX_APPOINTMENT_END + "2020-12-25 12:00 "
            + PREFIX_PATIENT + "1 "
            + PREFIX_DESCRIPTION + "Medical checkup "
            + PREFIX_TAG + "LowPriority";

    public static final String MESSAGE_SUCCESS = "New appointment scheduled: %1$s.";
    public static final String MESSAGE_MISSING_PATIENT = "This patient is not in Archangel.";
    public static final String MESSAGE_WRONG_INDEX = "You have entered an invalid index.";

    private final Appointment toSchedule;

    /**
     * Creates an AppointmentAddCommand to add the specified {@code Appointment}
     */
    public AppointmentScheduleCommand(Appointment appointment) {
        requireNonNull(appointment);
        toSchedule = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            requireNonNull(model);
            ReadOnlyAddressBook addressBook = model.getAddressBook();
            ObservableList<Patient> patientList = model.getFilteredPatientList();
            ObservableList<Appointment> appointmentList = addressBook.getAppointmentList();

            // Target index inside the unique patient list
            int index = Integer.parseInt(toSchedule.getPatientString());

            ArrayList<Patient> patientArrayList = new ArrayList<>(patientList);
            int size = patientArrayList.size();

            // Patient list is empty
            if (size == 0) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            // index is out of bounds
            if (index > size) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            toSchedule.parsePatient(patientArrayList, index);

            // Patient does not exist
            if (!model.hasPatient(toSchedule.getPatient())) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            // Appointment slot is already taken
            if (!AppointmentTime.isValidTimeSlot(appointmentList, toSchedule)) {
                throw new CommandException(MESSAGE_INVALID_APPOINTMENT_SLOT);
            }

            // Appointment already exists
            if (model.hasAppointment(toSchedule)) {
                throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
            }

            model.addAppointment(toSchedule);
            model.getFilteredAppointmentList();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule),
                     false, false, true);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_WRONG_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentScheduleCommand // instanceof handles nulls
                && toSchedule.equals(((AppointmentScheduleCommand) other).toSchedule));
    }
}
