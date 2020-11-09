package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testToString() {
        Name name = new Name("peter jack");
        String expected = "peter jack";
        assertTrue(name.toString().equals(expected));
    }

    @Test
    public void areEqualNames() {
        // equal names
        assertTrue(new Name("peter jack").equals(new Name("peter jack")));

        // not equal names
        assertFalse(new Name("Peter jack").equals(new Name("peter jack"))); // different letter casing
    }

    @Test
    public void testHashcode() {
        // equal hashcodes
        Name firstName = new Name("peter jack");
        Name secondName = new Name("peter jack");
        assertTrue(firstName.hashCode() == secondName.hashCode());

        // not equal hashcodes
        Name thirdName = new Name("David Roger Jackson Ray Jr 2nd");
        assertFalse(firstName.hashCode() == thirdName.hashCode());
    }
}
