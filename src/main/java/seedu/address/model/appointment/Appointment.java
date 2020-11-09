package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Represents an Appointment in the Archangel
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    // Identity fields
    private AppointmentTime appointmentTime;
    private Patient patient;
    private String patientString;
    private Set<Tag> tags = new HashSet<>();
    private Boolean isCompleted;
    private Boolean isMissed;
    private Description description;

    /**
     * Every field must be present and not null. Proper Appointment object.
     */
    public Appointment(AppointmentTime appointmentTime, Patient patient, Set<Tag> tags,
                       Boolean isCompleted, Boolean isMissed, Description description) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.patientString = patient.getName().fullName;
        this.isCompleted = isCompleted;
        this.isMissed = isMissed;
        this.description = description;
        this.tags.addAll(tags);
    }

    /**
     * No Patient Object yet. This is used before executing Command.
     */
    public Appointment(AppointmentTime appointmentTime, String patientString, Set<Tag> tags,
                       Boolean isCompleted, Boolean isMissed, Description description) {
        this.appointmentTime = appointmentTime;
        this.patient = null;
        this.patientString = patientString;
        this.isCompleted = isCompleted;
        this.isMissed = isMissed;
        this.description = description;
        this.tags.addAll(tags);
    }

    public AppointmentTime getAppointmentTime() {
        return this.appointmentTime;
    }

    public LocalDateTime getStartTime() {
        return this.appointmentTime.getStart();
    }

    public LocalDateTime getEndTime() {
        return this.appointmentTime.getEnd();
    }

    public Patient getPatient() {
        return this.patient;
    }

    public String getPatientString() {
        return this.patientString;
    }

    public Boolean isMissed() {
        return this.isMissed;
    }

    public Boolean isCompleted() {
        return this.isCompleted;
    }

    public Description getDescription() {
        return this.description;
    }

    /**
     * Parses patientString to change patient field in appointment from Json format.
     * @param addressBook
     */
    public void parsePatient(ReadOnlyAddressBook addressBook) {
        // This method applies for reading the Json file
        ArrayList<Patient> arr = new ArrayList<>(addressBook.getPatientList());
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getName().fullName.equals(patientString)) {
                patient = arr.get(i);
            }
        }
    }

    /**
     * Retrieves patient using index from user input.
     * @param arr
     * @param index
     */
    // This method only applies for the ScheduleAppointmentCommand
    public void parsePatient(ArrayList<Patient> arr, int index) {
        patient = arr.get(index - 1);
        patientString = patient.getName().fullName;
    }

    /**
     * Updates patient in appointment if Patient has fields that are edited.
     * @param currentPatient Patient to verify this is the correct appointment to update patient.
     * @param updatedPatient Patient to update current patient with.
     */
    public void updatePatient(Patient currentPatient, Patient updatedPatient) {
            assert currentPatient.isSamePatient(this.patient);

        this.patient = updatedPatient;
        this.patientString = updatedPatient.getName().fullName;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both appointments have the same appointment time, patient, description.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }
        return otherAppointment != null
                // to allow editing of appointmentTime
                && otherAppointment.getAppointmentTime().equals(getAppointmentTime())
                // to allow editing of patient
                && otherAppointment.getPatient().equals(getPatient())
                // to allow editing of description
                && otherAppointment.getDescription().equals(getDescription())
                // to allow editing of tags
                && otherAppointment.getTags().equals(getTags());
    }

    /**
     * Sets appointment as completed.
     */
    public void setIsCompleted() {
        this.isCompleted = true;
    }

    /**
     * Sets appointment as missed.
     */
    public void setIsMissed() {
        this.isMissed = true;
    }

    /**
     * Checks if the appointment has been missed.
     * An appointment has been missed if it ends before LocalDateTime {@code now} and is not completed.
     */
    public boolean hasBeenMissed(LocalDateTime now) {
        boolean isBefore = this.appointmentTime.getEnd().plusMinutes(30).isBefore(now);
        boolean isUncompleted = !this.isCompleted;
        return isBefore && isUncompleted;
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        if (this.patient != null && otherAppointment.patient != null) {
            return otherAppointment != null
                    && otherAppointment.getAppointmentTime().equals(getAppointmentTime())
                    && otherAppointment.getPatient().equals(getPatient())
                    && otherAppointment.getPatientString().equals(getPatientString())
                    && otherAppointment.isMissed().equals(isMissed())
                    && otherAppointment.isCompleted().equals(isCompleted())
                    && otherAppointment.getDescription().equals(getDescription())
                    && otherAppointment.getTags().equals(getTags());
        } else {
            return otherAppointment != null
                    && otherAppointment.getAppointmentTime().equals(getAppointmentTime())
                    && otherAppointment.getPatientString().equals(getPatientString())
                    && otherAppointment.isMissed().equals(isMissed())
                    && otherAppointment.isCompleted().equals(isCompleted())
                    && otherAppointment.getDescription().equals(getDescription())
                    && otherAppointment.getTags().equals(getTags());
        }
    }

    @Override
    public int hashCode() {
        // custom fields hashing
        return Objects.hash(appointmentTime, patient, isMissed, isCompleted, description, tags);
    }

    @Override
    public String toString() {
        if (this.patient != null) {
            final StringBuilder builder = new StringBuilder();
            builder.append(getDescription())
                    .append("\nStart: ")
                    .append(getStartTime())
                    .append("\nEnd: ")
                    .append(getEndTime())
                    .append("\nPatient: ")
                    .append(getPatient().getName().fullName)
                    .append("\nisMissed: ")
                    .append(isMissed())
                    .append("\nisCompleted: ")
                    .append(isCompleted())
                    .append("\nTags: ");
            getTags().forEach(builder::append);
            return builder.toString();
        } else {
            final StringBuilder builder = new StringBuilder();
            builder.append(getDescription())
                    .append("\nStart: ")
                    .append(getStartTime())
                    .append("\nEnd: ")
                    .append(getEndTime())
                    .append("\nPatientString: ")
                    .append(getPatientString())
                    .append("\nisMissed: ")
                    .append(isMissed())
                    .append("\nisCompleted: ")
                    .append(isCompleted())
                    .append("\nTags: ");
            getTags().forEach(builder::append);
            return builder.toString();
        }
    }

}
