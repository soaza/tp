package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentFilter;

/**
 * Lists all appointments in the address book that have not been completed or missed to the user.
 */
public class AppointmentListCommand extends Command {

    public static final String COMMAND_WORD = "a-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all upcoming appointments. "
            + "\n"
            + "Example " + COMMAND_WORD;

    public static final String MESSAGE_LIST_APPOINTMENT_SUCCESS = "%1$d upcoming appointments found!"
            + "\n"
            + "You can view your past appointments with a-missed, a-completed or a-listall!";

    private final SearchAppointmentFilter predicate;

    public AppointmentListCommand(SearchAppointmentFilter predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(MESSAGE_LIST_APPOINTMENT_SUCCESS,
                        model.getFilteredAppointmentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentListCommand // instanceof handles nulls
                && predicate.equals(((AppointmentListCommand) other).predicate)); // state check
    }
}
