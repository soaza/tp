package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid genders
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("Male"));
        assertFalse(Gender.isValidGender("Female"));
        assertFalse(Gender.isValidGender("Others"));
        assertFalse(Gender.isValidGender("male"));
        assertFalse(Gender.isValidGender("female"));
        assertFalse(Gender.isValidGender("others"));


        // valid genders
        assertTrue(Gender.isValidGender("MALE"));
        assertTrue(Gender.isValidGender("FEMALE"));
        assertTrue(Gender.isValidGender("OTHERS"));
    }

    @Test
    public void areEqualGenders() {
        // equal genders
        Gender firstGender = new Gender("FEMALE");
        Gender secondGender = new Gender("FEMALE");
        assertTrue(firstGender.equals(secondGender));
    }

    @Test
    public void areNotEqualGenderes() {
        assertFalse(new Gender("MALE").equals(new Gender("FEMALE")));
        assertFalse(new Gender("MALE").equals(new Gender("OTHERS")));
        assertFalse(new Gender("FEMALE").equals(new Gender("OTHERS")));
    }

    @Test
    public void testToString() {
        Gender gender = new Gender("MALE");
        String expected = "MALE";
        assertTrue(gender.toString().equals(expected));
    }

    @Test
    public void testHashcode() {
        // equal hashcodes
        Gender firstGender = new Gender("MALE");
        Gender secondGender = new Gender("MALE");
        assertTrue(firstGender.hashCode() == secondGender.hashCode());

        // not equal hashcodes
        assertFalse(new Gender("MALE").hashCode() == new Gender("FEMALE").hashCode());
    }
}
