package seedu.address.model.filters.appointmentfilters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.model.appointment.Appointment;

/**
 * Filter appointments  {@code Appointment}'s {@code Patient}'s {@code startDate} that occurs this week.
 */
public class SearchAppointmentWeekFilter implements Predicate<Appointment> {
    private static final Logger LOGGER = Logger.getLogger("SearchAppointmentWeekFilter");
    private static final String ASSERTION_ERROR = "Invalid appointment start and end date";
    private static final String LOG = "Valid appointment start and end date";
    private LocalDate today;
    private LocalDate thisWeekSunday;
    private LocalDate thisWeekSaturday;

    /**
     * Class Constructor.
     */
    public SearchAppointmentWeekFilter() {
        this.today = LocalDate.now();
        this.thisWeekSunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        this.thisWeekSaturday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
    }

    @Override
    public boolean test(Appointment appointment) {
        assert appointment.getStartTime().isBefore(appointment.getEndTime()) : ASSERTION_ERROR;
        LOGGER.log(Level.WARNING, LOG);
        LocalDate appointmentStartDate = appointment.getStartTime().toLocalDate();
        LocalDate appointmentEndDate = appointment.getEndTime().toLocalDate();
        boolean completed = appointment.isCompleted();

        return (isLocalDateInTheSameWeek(appointmentStartDate) || isLocalDateInTheSameWeek(appointmentEndDate))
                    && !completed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchAppointmentWeekFilter // instanceof handles nulls
                && today.equals(((SearchAppointmentWeekFilter) other).today)); // state check
    }

    // Code is adapted from
    // https://stackoverflow.com/questions/4535583/how-to-detect-if-a-date-is-within-this-or-next-week-in-java
    /**
     * Check if the appointment's date occurs this week.
     * @param appointmentDate
     * @return A boolean.
     */
    public boolean isLocalDateInTheSameWeek(LocalDate appointmentDate) {
        return ((appointmentDate.isEqual(thisWeekSunday) || appointmentDate.isAfter(thisWeekSunday))
                && (appointmentDate.isEqual(thisWeekSaturday) || appointmentDate.isBefore(thisWeekSaturday)));
    }
}
