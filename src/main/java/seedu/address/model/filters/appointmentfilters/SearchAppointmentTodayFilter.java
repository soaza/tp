package seedu.address.model.filters.appointmentfilters;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.model.appointment.Appointment;

/**
 * Filter appointments  {@code Appointment}'s {@code Patient}'s {@code startDate} that matches today's date.
 */
public class SearchAppointmentTodayFilter implements Predicate<Appointment> {
    private static final Logger LOGGER = Logger.getLogger("SearchAppointmentWeekFilter");
    private static final String ASSERTION_ERROR = "Invalid appointment start and end date";
    private static final String LOG = "Valid appointment start and end date";

    private LocalDate today;

    public SearchAppointmentTodayFilter() {
        this.today = LocalDate.now();
    }

    @Override
    public boolean test(Appointment appointment) {
        assert appointment.getStartTime().isBefore(appointment.getEndTime()) : ASSERTION_ERROR;
        LOGGER.log(Level.WARNING, LOG);
        boolean completed = appointment.isCompleted();
        return appointment.getStartTime().toLocalDate().equals(today) && !completed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchAppointmentTodayFilter // instanceof handles nulls
                && today.equals(((SearchAppointmentTodayFilter) other).today)); // state check
    }
}
