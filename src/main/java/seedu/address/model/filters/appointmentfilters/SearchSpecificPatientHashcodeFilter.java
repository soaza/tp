package seedu.address.model.filters.appointmentfilters;

import java.util.function.Predicate;

import seedu.address.model.appointment.Appointment;

public class SearchSpecificPatientHashcodeFilter implements Predicate<Appointment> {
    private final int hashcode;

    public SearchSpecificPatientHashcodeFilter(int hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public boolean test(Appointment appointment) {
        return hashcode == (appointment.getPatient().hashCode());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.filters.appointmentfilters
                .SearchSpecificPatientHashcodeFilter // instanceof handles nulls
                && hashcode == (((SearchSpecificPatientHashcodeFilter) other).hashcode)); // state check
    }
}
