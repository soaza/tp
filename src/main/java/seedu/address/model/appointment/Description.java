package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid appointment description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid appointment description.
     */
    public static boolean isValidDescription(String description) {
        return description.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object otherDesc) {
        if (this == otherDesc) {
            return true;
        }
        if (otherDesc instanceof Description) {
            Description other = (Description) otherDesc;
            if (this.description.equals(other.description)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    public String toString() {
        return description;
    }

}
