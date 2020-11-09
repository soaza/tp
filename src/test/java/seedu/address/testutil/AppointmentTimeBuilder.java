package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_DATE_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_END_ONE;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_DATE_TODAY;
import static seedu.address.logic.commands.appointmentcommands.AppointmentCommandTestUtil.VALID_START_ONE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.appointment.AppointmentTime;

/**
 * A utility class to help with building AppointmentTime objects.
 */
public class AppointmentTimeBuilder {

    /**
     * Formats date and time inputs as: yyyy-MM-dd hh:mm (eg. 2020-02-20 08:00).
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private static final String DEFAULT_START = VALID_START_ONE;
    private static final String DEFAULT_END = VALID_END_ONE;
    private static final String DEFAULT_START_TWO = VALID_START_DATE_TODAY;
    private static final String DEFAULT_END_TWO = VALID_END_DATE_TODAY;

    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates a {@code AppointmentTimeBuilder} with the default details.
     */
    public AppointmentTimeBuilder() {
        start = parseDateTime(DEFAULT_START);
        end = parseDateTime(DEFAULT_END);
    }

    /**
     * Initializes the AppointmentTimeBuilder with the data of {@code appointmentTimeToCopy}.
     */
    public AppointmentTimeBuilder(AppointmentTime appointmentTimeToCopy) {
        start = appointmentTimeToCopy.getStart();
        end = appointmentTimeToCopy.getEnd();
    }

    /**
     * Sets the start time of the {@code AppointmentTime} that we are building.
     */
    public AppointmentTimeBuilder withStart(String stringStart) {
        this.start = parseDateTime(stringStart);
        return this;
    }

    /**
     * Sets the end time of the {@code AppointmentTime} that we are building.
     */
    public AppointmentTimeBuilder withEnd(String stringEnd) {
        this.end = parseDateTime(stringEnd);
        return this;
    }

    public AppointmentTime build() {
        return new AppointmentTime(start, end);
    }
    /**
     * Parses a {@code String dateTime} into a {@code LocalDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        requireNonNull(dateTime);
        LocalDateTime localDateTime = null;
        String[] dateTimeSplit = dateTime.split(" ", 2);
        String date = dateTimeSplit[0];
        String time = dateTimeSplit[1];
        String[] dateSplit = date.split("-", 3);
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);
        String[] timeSplit = time.split(":", 2);
        int hour = Integer.parseInt(timeSplit[0]);
        int min = Integer.parseInt(timeSplit[1]);
        //String dt = date + "T" + time;
        localDateTime = LocalDateTime.of(year, month, day, hour, min);
        return localDateTime;
    }

    /**
     * Build AppointmentTime for Edit Tests, to make sure that the new timing does not clash with any current timing.
     * @return AppointmentTime to be edited.
     */
    public static AppointmentTime buildAppointTimeForEdit() {
        return new AppointmentTime(parseDateTime(DEFAULT_START_TWO),
                parseDateTime(DEFAULT_END_TWO));
    }
}
