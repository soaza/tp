package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Command undone.";
    public static final String MESSAGE_NO_UNDO_HISTORY_ERROR = "Nothing to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restore the state before the previous command. "
            + "\n"
            + "Example: " + COMMAND_WORD;

    public UndoCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.getUserHistoryManager().canUndo()) {
            throw new CommandException(MESSAGE_NO_UNDO_HISTORY_ERROR);
        } else {
            model.undoHistory();
            return new CommandResult("Command undone.", false, false, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand); // instanceof handles nulls
    }
}
