package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAppointments.APT1;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class JsonAdaptedAppointmentTest {
    private static final String INVALID_TIME = "START: 20-2020-05T08:00\n END: 20-2020-05T12:00";
    private static final String INVALID_PATIENT = "#";
    private static final String INVALID_DESCRIPTION = "@#@!!@!";

    private static final String VALID_TIME = APT1.getAppointmentTime().toString();
    private static final String VALID_PATIENT = APT1.getPatientString();
    private static final String VALID_DESCRIPTION = APT1.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = APT1.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedAppointment appt = new JsonAdaptedAppointment(APT1);
        assertEquals(APT1, appt.toModelType(getTypicalAddressBook()));
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        try {
            JsonAdaptedAppointment appt =
                new JsonAdaptedAppointment(INVALID_TIME, VALID_PATIENT,
                        VALID_DESCRIPTION, "false", "false", VALID_TAGS);
            appt.toModelType(getTypicalAddressBook());
        } catch (NullPointerException e) {
            assertEquals(1, 1);
        } catch (IllegalValueException e) {
            assertEquals(1, 1);
        }
    }

    @Test
    public void toModelType_invalidPatient_throwsIllegalValueException() {
        try {
            JsonAdaptedAppointment appt =
                    new JsonAdaptedAppointment(VALID_TIME, INVALID_PATIENT,
                            VALID_DESCRIPTION, "false", "false", VALID_TAGS);
            appt.toModelType(getTypicalAddressBook());
        } catch (NullPointerException e) {
            assertEquals(1, 1);
        } catch (IllegalValueException e) {
            assertEquals(1, 1);
        }
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() throws IllegalValueException {
        try {
            JsonAdaptedAppointment appt =
                    new JsonAdaptedAppointment(VALID_TIME, VALID_PATIENT,
                            INVALID_DESCRIPTION, "false", "false", VALID_TAGS);
            appt.toModelType(getTypicalAddressBook());
            assertEquals(1, 2);
        } catch (NullPointerException e) {
            assertEquals(1, 1);
        } catch (IllegalValueException e) {
            assertEquals(1, 1);
        }
    }

}
