package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#isSameAppointment(Appointment)}.
 * As such, adding and updating of appointments uses Appointment#isSameAppointment(Appointment)
 * for equality so as to ensure that the appointment being added or updated is
 * unique in terms of identity in the UniqueAppointmentList.
 * However, the removal of a appointment uses Appointment#equals(Object) so
 * as to ensure that the appointment with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isSameAppointment(Appointment)
 */
public class UniqueAppointmentList {

    private ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAppointment);
    }

    /**
     * Adds a appointment to the list, then sorts the appointments in list by chronological order.
     * The appointment must not already exist in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a appointment to the list when getting from JSON,
     * then sorts the appointments in list by chronological order..
     */
    public void add(Appointment toAdd, boolean fromJson) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Sorts internalList of appointments chronologically, by earlier appointment startTime.
     * If appointment startTime is equal, then sort by earlier appointment endTime.
     */
    public void sortAppointmentList() {
        // Define comparator to sort internalList.
        Comparator<Appointment> appointmentComparator = new Comparator<Appointment>() {
            @Override
            public int compare(Appointment a1, Appointment a2) {
                if (a1.getStartTime().isBefore(a2.getStartTime())) {
                    return -1;
                } else if (a1.getStartTime().isEqual(a2.getStartTime())
                        && a1.getEndTime().isBefore(a2.getEndTime())) {
                    return 0;
                }
                return 1;
            }
        };
        internalList.sort(appointmentComparator);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the list.
     * The appointment identity of {@code editedAppointment}
     * must not be the same as another existing appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.isSameAppointment(editedAppointment) && contains(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
        internalList.remove(toRemove);
    }

    /**
     * Sets the equivalent appointment from the list as completed.
     * The appointment must exist in the list.
     */
    public void setComplete(Appointment toComplete) {
        requireNonNull(toComplete);
        int index = internalList.indexOf(toComplete);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }
        internalList.get(index).setIsCompleted();
    }

    /**
     * Set appointments from the list as missed.
     * LocalDateTime now is used to determine which appointments have been missed.
     */
    public void setMissedAppointments(LocalDateTime now) {
        requireNonNull(now);
        for (Appointment appointment: internalList) {
            if (appointment.hasBeenMissed(now) && !appointment.isCompleted()) {
                appointment.setIsMissed();
            }
        }
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
