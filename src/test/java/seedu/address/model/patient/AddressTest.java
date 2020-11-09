package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void areEqualAddresses() {
        // equal addresses
        Address firstAddress = new Address("12 Kent Ridge Dr");
        Address secondAddress = new Address("12 Kent Ridge Dr");
        assertTrue(firstAddress.equals(secondAddress));

        // equal addresses even with trailing whitespace
        Address thirdAddress = new Address("12 Kent Ridge Dr ");
        assertTrue(firstAddress.equals(thirdAddress));
    }

    @Test
    public void areNotEqualAddresses() {
        Address firstAddress = new Address("12 Kent Ridge Dr");
        Address secondAddress = new Address("Bedok South Avenue 2");
        assertFalse(firstAddress.equals(secondAddress));
    }

    @Test
    public void testToString() {
        Address address = new Address("12 Kent Ridge Dr");
        String expected = "12 Kent Ridge Dr";
        assertTrue(address.toString().equals(expected));
    }

    @Test
    public void testHashcode() {
        // equal hashcodes
        Address firstAddress = new Address("12 Kent Ridge Dr");
        Address secondAddress = new Address("12 Kent Ridge Dr");
        assertTrue(firstAddress.hashCode() == secondAddress.hashCode());

        // not equal hashcodes
        Address thirdAddress = new Address("12 Bedok South Avenue");
        assertFalse(firstAddress.hashCode() == thirdAddress.hashCode());
    }
}
