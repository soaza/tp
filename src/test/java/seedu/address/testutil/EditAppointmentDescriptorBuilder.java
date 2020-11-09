package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.appointmentcommands.AppointmentEditCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Description;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private AppointmentEditCommand.EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new AppointmentEditCommand.EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(AppointmentEditCommand.EditAppointmentDescriptor descriptor) {
        this.descriptor = new AppointmentEditCommand.EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new AppointmentEditCommand.EditAppointmentDescriptor();
        descriptor.setAppointmentTime(appointment.getStartTime(), appointment.getEndTime());
        descriptor.setPatient(appointment.getPatient());
        descriptor.setPatientString(appointment.getPatientString());
        descriptor.setDescription(appointment.getDescription());
        descriptor.setIsMissed(appointment.isMissed());
        descriptor.setIsCompleted(appointment.isCompleted());
        descriptor.setTags(appointment.getTags());
    }

    /**
     * Sets the {@code startTime, endTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withAppointmentTime(String startTime, String endTime) {
        try {
            descriptor.setAppointmentTime(ParserUtil.parseDateTime(startTime), ParserUtil.parseDateTime(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withStartTime(String startTime) {
        try {
            descriptor.setAppointmentTime(ParserUtil.parseDateTime(startTime), null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withEndTime(String endTime) {
        try {
            descriptor.setAppointmentTime(ParserUtil.parseDateTime(endTime), null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code PatientIndex} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withPatient(String patientString) {
        descriptor.setNeedsParsePatient(true);
        descriptor.setPatientString(patientString);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code isMissed} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withIsMissed(boolean isMissed) {
        descriptor.setIsMissed(isMissed);
        return this;
    }

    /**
     * Sets the {@code isCompleted} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withIsCompleted(boolean isCompleted) {
        descriptor.setIsCompleted(isCompleted);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditAppointmentDescriptor}
     * that we are building.
     */
    public EditAppointmentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public AppointmentEditCommand.EditAppointmentDescriptor build() {
        return descriptor;
    }

    /**
     * Build Descriptor for Edit Tests, to make sure that the index is provided, and the new patient is parsed using
     * the index.
     * @return Descriptor for editting.
     */
    public AppointmentEditCommand.EditAppointmentDescriptor buildDescriptorForEdit(String index) {
        descriptor.setNeedsParsePatient(true);
        descriptor.setPatientString(index);
        return descriptor;
    }

}
