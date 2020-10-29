package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the address book.
 */
public class AppointmentScheduleCommand extends Command {

    public static final String COMMAND_WORD = "a-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an appointment to Archangel."
            + "Parameters: "
            + PREFIX_APPOINTMENT_START + "START "
            + PREFIX_APPOINTMENT_END + "END "
            + PREFIX_PATIENT + "PATIENT "
            + PREFIX_DESCRIPTION + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPOINTMENT_START + "2020-01-25 08:00 "
            + PREFIX_APPOINTMENT_END + "2020-01-25 12:00 "
            + PREFIX_PATIENT + "1 "
            + PREFIX_DESCRIPTION + "Medical checkup "
            + PREFIX_TAG + "LowPriority";

    public static final String MESSAGE_SUCCESS = "New appointment scheduled: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in Archangel";
    public static final String MESSAGE_MISSING_PATIENT = "This patient is not in Archangel";
    public static final String MESSAGE_WRONG_INDEX = "We have some problem with your index";
    public static final String MESSAGE_INVALID_APPOINTMENT_SLOT = "That time slot is already taken";


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
            ObservableList<Patient> patientList = addressBook.getPatientList();
            ObservableList<Appointment> appointmentList = addressBook.getAppointmentList();

            // Target index inside the unique patient list
            int index = Integer.parseInt(toSchedule.getPatientString());

            ArrayList<Patient> arr = new ArrayList<>(patientList);
            int size = arr.size();

            // Patient list is empty
            if (size == 0) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            // index is out of bounds
            if (index > size) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            toSchedule.parsePatient(arr, index);

            // Patient does not exist
            if (!model.hasPatient(toSchedule.getPatient())) {
                throw new CommandException((MESSAGE_MISSING_PATIENT));
            }

            // Appointment slot is already taken
            if (!isValidTimeSlot(appointmentList, toSchedule)) {
                throw new CommandException(MESSAGE_INVALID_APPOINTMENT_SLOT);
            }

            // Appointment already exists
            if (model.hasAppointment(toSchedule)) {
                throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            model.addAppointment(toSchedule);
            model.getFilteredAppointmentList();

            return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
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

    private Boolean isValidTimeSlot(ObservableList<Appointment> appointmentList, Appointment appointment) {
        LocalDateTime startDate = appointment.getStartTime();
        LocalDateTime endDate = appointment.getEndTime();

        for (Appointment currentAppointment : appointmentList) {
            LocalDateTime currentStartDate = currentAppointment.getStartTime();
            LocalDateTime currentEndDate = currentAppointment.getEndTime();
            // startDate or endDate is in between currentAppointment slot
            // current Appointment slot is within new appointment slot
            if (startDate.isAfter(currentStartDate) && startDate.isBefore(currentEndDate)
                    || endDate.isAfter(currentStartDate) && endDate.isBefore(currentEndDate)
                    || startDate.isBefore(currentStartDate) && endDate.isAfter(currentEndDate)
                    || startDate.isEqual(currentStartDate) || endDate.isEqual(currentEndDate)) {
                return false;
            }
        }
        return true;
    }
}
