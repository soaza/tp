package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_PATIENTS_LISTED_OVERVIEW = "%1$d patients listed!";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX =
            "The appointment index provided is invalid";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointment(s) listed!";
    public static final String MESSAGE_APPOINTMENT_BACKDATED =
            "Appointments can only be scheduled for future appointment time.\n"
            + "i.e. you cannot schedule an Appointment before the current time.";
    public static final String MESSAGE_APPOINTMENT_DURATION = "Appointment duration cannot exceed 24 hours.";
    public static final String MESSAGE_INVALID_APPOINTMENT_SLOT = "That time slot is already taken.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in Archangel.";


}
