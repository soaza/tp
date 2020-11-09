package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENT_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_APPOINTMENT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_APPOINTMENT_SLOT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
 * Edits the details of an existing appointment in the address book.
 */
public class AppointmentEditCommand extends Command {
    public static final String COMMAND_WORD = "a-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_APPOINTMENT_START + "START] "
            + "[" + PREFIX_APPOINTMENT_END + "END] "
            + "[" + PREFIX_PATIENT + "PATIENT] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_START + "2020-02-05 09:00 "
            + PREFIX_APPOINTMENT_END + "2020-02-05 10:00 "
            + PREFIX_DESCRIPTION + "Therapy session";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the appointment with
     */
    public AppointmentEditCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = null;
        try {
            editedAppointment = createEditedAppointment(appointmentToEdit,
                    editAppointmentDescriptor, model);
        } catch (CommandException e) {
            throw new CommandException(e.getMessage());
        }

        if (appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        // Create a copy of appointment list without appointmentToEdit to check for overlapping appointmentTime.
        ObservableList<Appointment> appointmentListWithoutOriginal = FXCollections.observableArrayList();
        appointmentListWithoutOriginal.addAll(model.getFilteredAppointmentList());
        appointmentListWithoutOriginal.remove(appointmentToEdit);
        // Appointment slot is already taken
        if (!AppointmentTime.isValidTimeSlot(appointmentListWithoutOriginal, editedAppointment)) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_SLOT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        // model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment),
                false, false, true);
    }

    /**
     * Creates and returns a {@code Appointment} with the details of {@code AppointmentToEdit}
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor,
                                                       Model model) throws CommandException {
        assert appointmentToEdit != null;

        LocalDateTime startTime = editAppointmentDescriptor.getStartTime().orElse(appointmentToEdit.getStartTime());
        LocalDateTime endTime = editAppointmentDescriptor.getEndTime().orElse(appointmentToEdit.getEndTime());

        // cannot schedule an Appointment for more than 24 hours.
        if (startTime.plusHours(24).isBefore(endTime)) {
            throw new CommandException(MESSAGE_APPOINTMENT_DURATION);
        }

        AppointmentTime updatedAppointmentTime = null;
        try {
            updatedAppointmentTime = new AppointmentTime(startTime, endTime);
        } catch (Exception e) {
            throw new CommandException(AppointmentTime.MESSAGE_CONSTRAINTS);
        }

        Patient updatedPatient = appointmentToEdit.getPatient();
        if (editAppointmentDescriptor.needsParsePatient) {
            Index patientIndex = Index.fromOneBased(Integer.parseInt(editAppointmentDescriptor
                    .getPatientString().get()));
            if (patientIndex.getZeroBased() >= model.getFilteredPatientList().size()) {
                throw new CommandException(MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
            }
            updatedPatient = model.getFilteredPatientList().get(patientIndex.getZeroBased());
        }

        Description updatedDescription = editAppointmentDescriptor.getDescription()
                .orElse(appointmentToEdit.getDescription());
        Set<Tag> updatedTags = editAppointmentDescriptor.getTags().orElse(appointmentToEdit.getTags());

        // Edit command does not allow editing of isCompleted status
        Boolean isCompleted = appointmentToEdit.isCompleted();

        // Check if appointment should still be considered missed
        Boolean isMissed;
        if (isCompleted) {
            isMissed = false;
        } else {
            isMissed = getUpdatedMissedStatus(endTime);
        }

        return new Appointment(updatedAppointmentTime, updatedPatient, updatedTags, isCompleted, isMissed,
                updatedDescription);
    }

    private static Boolean getUpdatedMissedStatus(LocalDateTime endTime) {
        if (endTime.plusMinutes(30).isBefore(LocalDateTime.now())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentEditCommand)) {
            return false;
        }

        // state check
        AppointmentEditCommand e = (AppointmentEditCommand) other;
        return index.equals(e.index) && editAppointmentDescriptor.equals(e.editAppointmentDescriptor);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private LocalDateTime endTime;
        private LocalDateTime startTime;
        private Patient patient;
        private boolean needsParsePatient;
        private String patientString; // used for EditCommandParser to retrieve patient from Model
        private Boolean isCompleted;
        private Boolean isMissed;
        private Description description;
        private Set<Tag> tags;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setAppointmentTime(toCopy.startTime, toCopy.endTime);
            setPatient(toCopy.patient);
            setNeedsParsePatient(toCopy.needsParsePatient);
            setPatientString(toCopy.patientString);
            setIsCompleted(toCopy.isCompleted);
            setIsMissed(toCopy.isMissed);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(startTime, endTime, patient, patientString, description, tags);
        }

        public void setAppointmentTime(LocalDateTime startTime, LocalDateTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Optional<LocalDateTime> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public Optional<LocalDateTime> getEndTime() {
            return Optional.ofNullable(endTime);
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }

        public Optional<Patient> getPatient() {
            // reset to false, already updated to parsed patient
            setNeedsParsePatient(false);

            return Optional.ofNullable(patient);
        }

        public void setNeedsParsePatient(boolean needsParsePatient) {
            this.needsParsePatient = needsParsePatient;
        }

        public Optional<Boolean> getNeedsParsePatient() {
            return Optional.ofNullable(needsParsePatient);
        }

        public void setPatientString(String patientString) {
            this.patientString = patientString;
        }

        public Optional<String> getPatientString() {
            return Optional.ofNullable(patientString);
        }

        public void setIsCompleted(Boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Optional<Boolean> getIsCompleted() {
            return Optional.ofNullable(isCompleted);
        }

        public void setIsMissed(Boolean isMissed) {
            this.isMissed = isMissed;
        }

        public Optional<Boolean> getIsMissed() {
            return Optional.ofNullable(isMissed);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

            return getStartTime().equals(e.getStartTime())
                    && getEndTime().equals(e.getEndTime())
                    && getPatient().equals(e.getPatient())
                    && getPatientString().equals(e.getPatientString())
                    && getNeedsParsePatient().equals(e.getNeedsParsePatient())
                    && getIsCompleted().equals(e.getIsCompleted())
                    && getIsMissed().equals(e.getIsMissed())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());
        }

    }
}
