package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    public void isValidBloodType() {
        // null blood type
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood types
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("A+ ")); // trailing whitespace
        assertFalse(BloodType.isValidBloodType("a+")); // lower-case letters used
        assertFalse(BloodType.isValidBloodType("A++")); // extra symbol
        assertFalse(BloodType.isValidBloodType("A")); // absence of symbol

        // valid blood types
        assertTrue(BloodType.isValidBloodType("A+"));
        assertTrue(BloodType.isValidBloodType("A-"));
        assertTrue(BloodType.isValidBloodType("B+"));
        assertTrue(BloodType.isValidBloodType("B-"));
        assertTrue(BloodType.isValidBloodType("O+"));
        assertTrue(BloodType.isValidBloodType("O-"));
        assertTrue(BloodType.isValidBloodType("AB+"));
        assertTrue(BloodType.isValidBloodType("AB-"));
    }

    @Test
    public void areEqualBloodTypes() {
        // equal blood types
        BloodType firstBloodType = new BloodType("A+");
        BloodType secondBloodType = new BloodType("A+");
        assertTrue(firstBloodType.equals(secondBloodType));
    }

    @Test
    public void areNotEqualBloodTypees() {
        // same letter but different polarity
        assertFalse(new BloodType("A-").equals(new BloodType("A+")));

        // same polarity but different letter
        assertFalse(new BloodType("A+").equals(new BloodType("B+")));
    }

    @Test
    public void testToString() {
        BloodType bloodtype = new BloodType("A+");
        String expected = "A+";
        assertTrue(bloodtype.toString().equals(expected));
    }

    @Test
    public void testHashcode() {
        // equal hashcodes
        BloodType firstBloodType = new BloodType("A+");
        BloodType secondBloodType = new BloodType("A+");
        assertTrue(firstBloodType.hashCode() == secondBloodType.hashCode());

        // not equal hashcodes
        assertFalse(new BloodType("A+").hashCode() == new BloodType("A-").hashCode());
    }
}
