package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.filters.appointmentfilters.SearchAppointmentTodayFilter;

/**
 * Finds and lists all the appointments occuring at the current date in the addressbook.
 */
public class AppointmentTodayCommand extends Command {

    public static final String COMMAND_WORD = "a-today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all the appointments today. "
            + "\n"
            + "Example: " + COMMAND_WORD;

    private final SearchAppointmentTodayFilter predicate;

    public AppointmentTodayCommand(SearchAppointmentTodayFilter predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW,
                        model.getFilteredAppointmentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentTodayCommand // instanceof handles nulls
                && predicate.equals(((AppointmentTodayCommand) other).predicate)); // state check
    }
}
