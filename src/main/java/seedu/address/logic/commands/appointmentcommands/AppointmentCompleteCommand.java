package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Sets an existing appointment in the address book as completed.
 */
public class AppointmentCompleteCommand extends Command {
    public static final String COMMAND_WORD = "a-complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets an appointment as completed "
            + "by the index number used in the displayed appointment list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_COMPLETE_APPOINTMENT_SUCCESS = "Completed Appointment: %1$s";
    public static final String MESSAGE_ALREADY_COMPLETE = "This appointment has already been completed.";
    // public static final String MESSAGE_ALREADY_MISSED = "This appointment has already been missed.";
    public static final String ASSERT_ALREADY_COMPLETE = "AppointmentCompleteCommand should not run on appointments"
            + " which already been completed.";
    // public static final String ASSERT_ALREADY_MISSED = "AppointmentCompleteCommand should not run on appointments"
    //       + "which already been missed.";

    private final Index targetIndex;

    /**
     * @param targetIndex of the appointment in the filtered appointment list to set as completed
     */
    public AppointmentCompleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (appointmentToComplete.isCompleted()) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETE);
        } //else if (appointmentToComplete.isMissed()) {
        //      throw new CommandException(MESSAGE_ALREADY_MISSED);
        // }
        // assert appointmentToComplete.isCompleted() : ASSERT_ALREADY_COMPLETE;
        // assert appointmentToComplete.isMissed() : ASSERT_ALREADY_MISSED;

        // Create duplicate appointment with only difference being isCompleted set to true
        Appointment completedAppointment = createCompletedAppointment(appointmentToComplete);

        model.setAppointment(appointmentToComplete, completedAppointment);
        return new CommandResult(String.format(MESSAGE_COMPLETE_APPOINTMENT_SUCCESS, appointmentToComplete),
                  false, false, true);
    }

    private static Appointment createCompletedAppointment(Appointment appointmentToComplete) {

        assert appointmentToComplete != null;

        LocalDateTime startTime = appointmentToComplete.getStartTime();
        LocalDateTime endTime = appointmentToComplete.getEndTime();
        AppointmentTime appointmentTime = new AppointmentTime(startTime, endTime);

        Patient patient = appointmentToComplete.getPatient();

        Description description = appointmentToComplete.getDescription();

        Set<Tag> tags = appointmentToComplete.getTags();

        Boolean isCompleted = true;
        Boolean isMissed = false;

        return new Appointment(appointmentTime, patient, tags, isCompleted, isMissed,
                description);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentCompleteCommand // instanceof handles nulls
                && targetIndex.equals(((AppointmentCompleteCommand) other).targetIndex)); // state check
    }
}
