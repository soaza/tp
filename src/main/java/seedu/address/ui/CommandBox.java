package seedu.address.ui;

import static seedu.address.commons.util.SuggestionBoxUtil.createSuggestions;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private ContextMenu suggestionPopup;
    private final SortedSet<String> suggestions;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */

    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        /*Source code with some modifications
         * author: https://gist.github.com/floralvikings
         * https://gist.github.com/floralvikings/10290131
         */
        suggestionPopup = new ContextMenu();
        suggestions = new TreeSet<>();
        suggestions.addAll(createSuggestions());
        commandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (commandTextField.getText().length() == 0) {
                    suggestionPopup.hide();
                } else {
                    LinkedList<String> searchResult = new LinkedList<>();
                    if (commandTextField.getText().contains(" ")) {
                        String[] parts = commandTextField.getText().split(" ", 2);
                        searchResult.addAll(suggestions.subSet(
                                parts[0].toLowerCase(),
                                 parts[0].toLowerCase() + Character.MAX_VALUE));

                    } else {
                        searchResult.addAll(suggestions.subSet(
                                commandTextField.getText(),
                                commandTextField.getText() + Character.MAX_VALUE));
                    }
                    if (suggestions.size() > 0) {
                        populatePopup(searchResult);
                        if (!suggestionPopup.isShowing()) {
                            suggestionPopup.show(commandTextField, Side.BOTTOM, 0, 0);
                        } else {
                            suggestionPopup.hide();
                        }
                    }
                }
            }
        });
        commandTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue,
                                Boolean aBoolean, Boolean aBoolean2) {
                suggestionPopup.hide();
            }
        });
    }

    /**
     *Getter method
     */
    public SortedSet<String> getSuggestions() {
        return suggestions;
    }

    /**
     * Populate the suggestion box
     * @param searchResult current search
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 5;
        int count = Math.min(searchResult.size(), maxEntries);
        if (count == 1) {
            final String result = searchResult.get(0);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);
        } else {
            for (int i = 0; i < count; i++) {
                final String result = searchResult.get(i);
                Label entryLabel = new Label(result);
                CustomMenuItem item = new CustomMenuItem(entryLabel, true);
                menuItems.add(item);
            }
        }
        suggestionPopup.getItems().clear();
        suggestionPopup.getItems().addAll(menuItems);

    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
