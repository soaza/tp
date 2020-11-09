package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BirthdateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Birthdate(null));
    }

    @Test
    public void constructor_invalidBirthdate_throwsIllegalArgumentException() {
        String invalidBirthdate = "";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    @Test
    public void isValidBirthdate() {
        // null birthdate
        assertThrows(NullPointerException.class, () -> Birthdate.isValidBirthdate(null));

        // invalid birthdates
        assertFalse(Birthdate.isValidBirthdate("")); // empty string
        assertFalse(Birthdate.isValidBirthdate(" ")); // spaces only
        assertFalse(Birthdate.isValidBirthdate("1998/April/04 ")); // alphabet present
        assertFalse(Birthdate.isValidBirthdate("1998/03/04")); // using slash instead of hyphen
        assertFalse(Birthdate.isValidBirthdate("1998-03-04 ")); // white space at the back
        assertFalse(Birthdate.isValidBirthdate("1998-03-4")); // invalid day format
        assertFalse(Birthdate.isValidBirthdate("1998-3-04")); // invalid month format
        assertFalse(Birthdate.isValidBirthdate("98-03-04")); // invalid year format
        assertFalse(Birthdate.isValidBirthdate("1998-03-32")); // invalid day number
        assertFalse(Birthdate.isValidBirthdate("1998-03-00")); // invalid day number
        assertFalse(Birthdate.isValidBirthdate("1998-00-00")); // invalid month number
        assertFalse(Birthdate.isValidBirthdate("1998-13-00")); // invalid month number

        // valid birthdates
        assertTrue(Birthdate.isValidBirthdate("1998-03-04")); // valid format
        assertTrue(Birthdate.isValidBirthdate("1998-03-01")); // valid day number
        assertTrue(Birthdate.isValidBirthdate("1998-03-28")); // valid day number
        assertTrue(Birthdate.isValidBirthdate("1998-01-04")); // valid month number
        assertTrue(Birthdate.isValidBirthdate("1998-12-04")); // valid month number
    }

    @Test
    public void areEqualBirthdates() {
        // equal birthdates
        Birthdate firstBirthdate = new Birthdate("1998-03-04");
        Birthdate secondBirthdate = new Birthdate("1998-03-04");
        assertTrue(firstBirthdate.equals(secondBirthdate));
    }

    @Test
    public void areNotEqualBirthdatees() {
        // same year and month but different day
        assertFalse(new Birthdate("1998-03-04").equals(new Birthdate("1998-03-05")));

        // different year but same month and day
        assertFalse(new Birthdate("1997-03-04").equals(new Birthdate("1998-03-04")));

        // different month but same year and day
        assertFalse(new Birthdate("1998-04-04").equals(new Birthdate("1998-03-04")));
    }

    @Test
    public void testToString() {
        Birthdate birthdate = new Birthdate("1998-03-05");
        String expected = "1998-03-05";
        assertTrue(birthdate.toString().equals(expected));
    }

    @Test
    public void testHashcode() {
        // equal hashcodes
        Birthdate firstBirthdate = new Birthdate("1998-03-05");
        Birthdate secondBirthdate = new Birthdate("1998-03-05");
        assertTrue(firstBirthdate.hashCode() == secondBirthdate.hashCode());

        // not equal hashcodes
        assertFalse(new Birthdate("1998-03-05").hashCode() == new Birthdate("1998-03-04").hashCode());
    }
}
