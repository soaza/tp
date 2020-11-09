package seedu.address.model.appointment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import javafx.collections.ObservableList;

public class AppointmentTime {

    public static final String MESSAGE_CONSTRAINTS =
        "(1) AppointmentTime must be valid, and start must be before end.\n"
        + "(2) AppointmentTime start and end must be maximally 24 hours apart.\n"
        + "(3) AppointmentTime must also not overlap with an existing Appointment's time.\n"
        + "* Note: Date indicated must be YYYY-MM-DD"
        + "(i.e. 1th Jan 2020 must be input as 2020-01-01 instead of 2020-1-1).\n"
        + "* Note: Time indicated must be XX:XX (i.e. 9AM must be input as 09:00 instead of 9:00).\n"
        + "Eg: start/2020-12-07 08:00 end/2020-12-07 10:00";

    // Data fields
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates an AppointmentTime object that stores start and end time of an appointment.
     * @param start start time of appointment.
     * @param end end time of appointment.
     */
    public AppointmentTime(LocalDateTime start, LocalDateTime end) {
        checkArgument(isValidAppointmentTime(start, end), MESSAGE_CONSTRAINTS);
        this.start = start;
        this.end = end;
    }

    /**
     * Every field must be present and not null. Additionally, start must be before end.
     */
    public static boolean isValidAppointmentTime(LocalDateTime start, LocalDateTime end) {
        requireAllNonNull(start, end);
        if (start.isAfter(end)) {
            return false;
        }
        return true;
    }

    /**
     * Checks for any overlapping AppointmentTime with all other appointments in appointmentList.
     * @param appointmentList list of all appointments in Archangel.
     * @param appointment Appointment to schedule/edit
     * @return true if no overlapping AppointmentTime, else false.
     */
    public static Boolean isValidTimeSlot(ObservableList<Appointment> appointmentList, Appointment appointment) {
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

    // Getter methods
    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "START: " + start + "\n END: " + end;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentTime // instanceof handles nulls
                && getStart().isEqual(((AppointmentTime) other).getStart()) // check same data fields
                && getEnd().isEqual(((AppointmentTime) other).getEnd()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

}
