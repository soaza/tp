package seedu.address.testutil;

import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_NEXT_WEEK;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_SATURDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_SUNDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_DESCRIPTION_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_DATE_NEXT_WEEK;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_DATE_THIS_WEEK_SATURDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_DATE_THIS_WEEK_SUNDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_DATE_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_DATE_NEXT_WEEK;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_DATE_THIS_WEEK_SATURDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_DATE_THIS_WEEK_SUNDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_DATE_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_TWO;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_NEXT_WEEK;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_SATURDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_SUNDAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_TAG_TWO;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.getTypicalPatients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APT1 = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_ONE, VALID_END_ONE)
            .withPatient(ALICE)
            .withDescription(VALID_DESCRIPTION_ONE)
            .withTags(VALID_TAG_ONE)
            .build();
    public static final Appointment APT2 = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_TWO, VALID_END_TWO)
            .withPatient(BOB)
            .withDescription(VALID_DESCRIPTION_TWO)
            .withTags(VALID_TAG_TWO)
            .build();
    public static final Appointment TODAY_APPOINTMENT = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_DATE_TODAY, VALID_END_DATE_TODAY)
            .withPatient(CARL)
            .withDescription(VALID_DESCRIPTION_TODAY)
            .withTags(VALID_TAG_TODAY)
            .build();
    public static final Appointment THIS_WEEK_SATURDAY_APPOINTMENT = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_DATE_THIS_WEEK_SATURDAY, VALID_END_DATE_THIS_WEEK_SATURDAY)
            .withPatient(CARL)
            .withDescription(VALID_DESCRIPTION_SATURDAY)
            .withTags(VALID_TAG_SATURDAY)
            .build();
    public static final Appointment THIS_WEEK_SUNDAY_APPOINTMENT = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_DATE_THIS_WEEK_SUNDAY, VALID_END_DATE_THIS_WEEK_SUNDAY)
            .withPatient(CARL)
            .withDescription(VALID_DESCRIPTION_SUNDAY)
            .withTags(VALID_TAG_SUNDAY)
            .build();
    public static final Appointment NEXT_WEEK_APPOINTMENT = new AppointmentBuilder()
            .withAppointmentTime(VALID_START_DATE_NEXT_WEEK, VALID_END_DATE_NEXT_WEEK)
            .withPatient(CARL)
            .withDescription(VALID_DESCRIPTION_NEXT_WEEK)
            .withTags(VALID_TAG_NEXT_WEEK)
            .build();

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical appointments.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with APT1 and APT2 only.
     */
    public static AddressBook getTypicalAddressBookForEdit() {
        AddressBook ab = new AddressBook();
        for (Appointment appointment : getTypicalAppointments2()) {
            ab.addAppointment(appointment);
        }

        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        ab.addPatient(BOB);
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(APT1, APT2,
                TODAY_APPOINTMENT, THIS_WEEK_SATURDAY_APPOINTMENT,
                THIS_WEEK_SUNDAY_APPOINTMENT, NEXT_WEEK_APPOINTMENT));
    }

    public static List<Appointment> getTypicalAppointments2() {
        return new ArrayList<>(Arrays.asList(APT1, APT2));
    }

    public static void main(String[] args) {
        System.out.println(getTypicalAppointments());
    }
}
