package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.Description;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String appointmentTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final String patient;
    private final String description;
    private final String isCompleted;
    private final String isMissed;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("appointmentTime") String appointmentTime,
                                  @JsonProperty("patient") String patient,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("isCompleted") String isCompleted,
                                  @JsonProperty("isMissed") String isMissed,
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isMissed = isMissed;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        String patient1;
        appointmentTime = source.getAppointmentTime().toString();
        patient1 = source.getPatientString();
        if (patient1 == null) {
            patient1 = source.getPatient().getName().fullName;
        }
        patient = patient1;
        description = source.getDescription().toString();
        isCompleted = source.isCompleted().toString();
        isMissed = source.isMissed().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType(ReadOnlyAddressBook addressBook) throws IllegalValueException {
        final List<Tag> appointmentTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            appointmentTags.add(tag.toModelType());
        }

        if (appointmentTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentTime.class.getSimpleName()));
        }
        final AppointmentTime modelAppointmentTime = new AppointmentTime(startTime, endTime);

        if (patient == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName()));
        }
        final String modelPatientString = patient;

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (isCompleted == null || isMissed == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName()));
        }
        final boolean modelIsCompleted = Boolean.parseBoolean(isCompleted);
        final boolean modelIsMissed = Boolean.parseBoolean(isMissed);
        final Set<Tag> modelTags = new HashSet<>(appointmentTags);
        Appointment appointment = new Appointment(modelAppointmentTime, modelPatientString,
                modelTags, modelIsCompleted, modelIsMissed, modelDescription);
        appointment.parsePatient(addressBook);
        return appointment;
    }

}
