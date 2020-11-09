package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.UniquePatientList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePatient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePatientList patients;
    private final UniqueAppointmentList appointments;


    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        patients = new UniquePatientList();
        appointments = new UniqueAppointmentList();
    }


    public AddressBook() {}

    /**
     * Creates an AddressBook using the Patients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setPatients(patients);
    }


    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }


    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPatients(newData.getPatientList());
        setAppointments(newData.getAppointmentList());
    }

    //// patient-level operations

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the address book.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Adds a patient to the address book.
     * The patient must not already exist in the address book.
     */
    public void addPatient(Patient p) {
        patients.add(p);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the address book.
     * The patient identity of {@code editedPatient} must not be
     * the same as another existing patient in the address book.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        patients.setPatient(target, editedPatient);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
    }

    //// appointment-level operations

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the address book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Adds an appointment to the address book.
     * The appointment must not already exist in the address book.
     */
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }
    public void addAppointment(Appointment a , boolean fromJson) {
        appointments.add(a, fromJson);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the address book.
     * The appointment identity of {@code editedAppointment} must not be
     * the same as another existing appointment in the address book.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);
        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    /**
     * Set {@code key} from this {@code AddressBook} as completed.
     * {@code key} must exist in the address book.
     */
    public void setComplete(Appointment key) {
        appointments.setComplete(key);
    }

    /**
     * Set missed appointments from this {@code AddressBook} as missed.
     * {@code now} is used to determine which appointments have been missed.
     */
    public void setMissedAppointments(LocalDateTime now) {
        appointments.setMissedAppointments(now);
    }

    //// util methods

    @Override
    public String toString() {
        return patients.asUnmodifiableObservableList().size() + " patients"
                + appointments.asUnmodifiableObservableList().size() + " appointments";
        // TODO: refine later
    }

    @Override
    public ObservableList<Patient> getPatientList() {
        return patients.asUnmodifiableObservableList();
    }

    /**
     * Sorts the appointment list then returns as an unmodifiableObservableList.
     * @return sorted, unmodifiable appointment list
     */
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        appointments.sortAppointmentList();
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && patients.equals(((AddressBook) other).patients)
                && appointments.equals(((AddressBook) other).appointments));
    }

    @Override
    public int hashCode() {
        // custom hash code
        return Objects.hash(patients, appointments);
    }
}
