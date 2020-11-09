package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class BloodType {


    public static final String MESSAGE_CONSTRAINTS = "BloodType must be valid. Eg: A+";

    private static final String[] Types = { "A+", "A-", "B+", "B-", "O", "O+", "O-", "AB+", "AB-"};

    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodtype A valid bloodtype number.
     */
    public BloodType(String bloodtype) {
        requireNonNull(bloodtype);
        checkArgument(isValidBloodType(bloodtype), MESSAGE_CONSTRAINTS);
        value = bloodtype;
    }

    /**
     * Returns true if a given string is a valid blood type.
     */
    public static boolean isValidBloodType(String test) {
        for (String validType: Types) {
            if (test.equals(validType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && value.equals(((BloodType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
