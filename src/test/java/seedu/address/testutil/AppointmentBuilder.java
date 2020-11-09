package seedu.address.testutil;

import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_ONE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;



/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final boolean DEFAULT_COMPLETE = false;
    public static final boolean DEFAULT_MISSED = true; // as DEFAULT_TIME is backdated
    private static final AppointmentTime DEFAULT_TIME = new AppointmentTimeBuilder().build();
    private static final Patient DEFAULT_PATIENT = new PatientBuilder().build();
    private static final String DEFAULT_PATIENTSTRING = DEFAULT_PATIENT.getName().fullName;
    private static final String DEFAULT_DESCRIPTION = VALID_DESCRIPTION_ONE;

    // Identity fields
    private AppointmentTime appointmentTime;
    private Patient patient;
    private String patientString;
    private Set<Tag> tags = new HashSet<>();
    private Boolean isCompleted;
    private Boolean isMissed;
    private Description description;
    /**
     * Creates a {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        appointmentTime = DEFAULT_TIME;
        patient = DEFAULT_PATIENT;
        patientString = DEFAULT_PATIENTSTRING;
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
        isCompleted = DEFAULT_COMPLETE;
        isMissed = DEFAULT_MISSED;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        appointmentTime = appointmentToCopy.getAppointmentTime();
        patient = appointmentToCopy.getPatient();
        patientString = appointmentToCopy.getPatientString();
        description = appointmentToCopy.getDescription();
        tags = appointmentToCopy.getTags();
        isMissed = appointmentToCopy.isMissed();
        isCompleted = appointmentToCopy.isCompleted();
    }

    /**
     * Sets the {@code AppointmentTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withAppointmentTime(String start, String end) {
        this.appointmentTime = new AppointmentTimeBuilder().withStart(start).withEnd(end).build();
        return this;
    }

    /**
     * Sets the {@code Patient} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatient(Patient patient) {
        this.patient = patient;
        this.patientString = patient.getName().fullName;
        return this;
    }

    /**
     * Sets the {@code PatientString} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatientString(String patientString) {
        this.patient = null;
        this.patientString = patientString;
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Appointment build() {
        return new Appointment(appointmentTime, patient, tags, isCompleted, isMissed, description);
    }

    /**
     * Build Appointment for Edit Tests, to make sure that the new timing does not clash with any current timing.
     * @return Appointment to be edited.
     */
    public Appointment buildAppointmentForEdit() {
        AppointmentTime appointmentTime = AppointmentTimeBuilder.buildAppointTimeForEdit();
        if (appointmentTime.getEnd().plusMinutes(30).isBefore(LocalDateTime.now())) {
            isMissed = true;
        } else {
            isMissed = false;
        }
        return new Appointment(appointmentTime, patient, tags,
                isCompleted, isMissed, description);
    }

    public Appointment buildAppointmentWithPatientString() {
        return new Appointment(appointmentTime, patientString, tags, isCompleted, isMissed, description);
    }
}
