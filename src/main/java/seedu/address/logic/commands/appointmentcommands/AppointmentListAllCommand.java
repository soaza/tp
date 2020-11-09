package seedu.address.logic.commands.appointmentcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class AppointmentListAllCommand extends Command {

    public static final String COMMAND_WORD = "a-listall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all appointments in addressbook. "
            + "\n"
            + "Example " + COMMAND_WORD;

    public static final String MESSAGE_LIST_APPOINTMENT_SUCCESS = "Listed all appointments";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_LIST_APPOINTMENT_SUCCESS, false, false, false);
    }
}
