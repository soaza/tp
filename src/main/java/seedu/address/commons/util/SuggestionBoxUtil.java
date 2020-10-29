package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Arrays;

public class SuggestionBoxUtil {
    /**
     * Creates a list a default suggestions.
     */
    public static ArrayList<String> createSuggestions() {
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.addAll(
                Arrays.asList(
                        //for patient commands
                        "p-add n/NAME g/GENDER bd/BIRTHDATE bt/BLOOD_TYPE p/PHONE e/EMAIL a/ADDRESS t/TAG",
                        "p-edit INDEX n/NAME g/GENDER bd/BIRTHDATE bt/BLOODTYPE n/NAME p/PHONE e/EMAIL a/ADDRESS t/TAG",
                        "p-delete INDEX",
                        "p-find KEYWORD",
                        "p-remark INDEX r/REMARK",
                        "p-remark INDEX",
                        //for appointment commands
                        "a-schedule start/YYYY-MM-DD HH:MM start/YYYY-MM-DD HH:MM pt/INDEX d/DESCRIPTION t/TAGS",
                        "a-delete INDEX",
                        "a-edit INDEX start/YYYY-MM-DD HH:MM start/YYYY-MM-DD HH:MM pt/INDEX d/DESCRIPTION t/TAGS",
                        "a-complete INDEX",
                        "a-list",
                        "a-listall",
                        "a-find NAME [MORE_NAME]...",
                        "a-tag KEYWORD [MORE_KEYWORDS]...",
                        "a-today",
                        "a-upcoming",
                        "a-completed",
                        "a-missed",
                        //for general commands
                        "undo",
                        "redo"
                )
        );
        return suggestions;
    }

}
