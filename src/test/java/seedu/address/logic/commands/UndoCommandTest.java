package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.UserHistoryManager;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PatientBuilder;


public class UndoCommandTest {

    //Static commands used for testing

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PATIENT COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_ADD_PATIENT_COMMAND_1 =
            "p-add n/John Doe g/MALE bd/2018-12-27 bt/A+ p/98765432 e/johnd@example.com a/311,"
                    + " Clementi Ave 2, #02-25 t/friends t/owesMoney";
    private static final String SAMPLE_ADD_PATIENT_COMMAND_2 =
            "p-add n/Kim Guan g/MALE bd/2018-12-27 bt/A+ p/98765432 e/johnd@example.com a/311,"
                    + " Clementi Ave 2, #02-25 t/friends t/owesMoney";
    private static final String SAMPLE_LIST_PATIENTS_COMMAND = "p-list";
    private static final String SAMPLE_EDIT_PATIENT_COMMAND = "p-edit 1 n/Kane";
    private static final String SAMPLE_FIND_PATIENT_COMMAND = "p-find James";
    private static final String SAMPLE_DELETE_PATIENT_COMMAND = "p-delete 1";
    private static final String SAMPLE_ADD_PATIENT_REMARK_COMMAND = "p-remark 1 r/Late";
    private static final String SAMPLE_REMOVE_PATIENT_REMARK_COMMAND = "p-remark 1";
    private static final String SAMPLE_SCHEDULE_APPT_COMMAND =
            "a-schedule pt/1 start/2020-11-21 08:00 end/2020-11-21 10:00 d/Review Appointment";

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~APPOINTMENT COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_DELETE_APPT_COMMAND = "a-delete 1";
    private static final String SAMPLE_EDIT_APPT_COMMAND = "a-edit 1 start/2020-09-15 12:00 end/2020-09-15 14:00";
    private static final String SAMPLE_COMPLETE_APPT_COMMAND = "a-complete 1";
    private static final String SAMPLE_LIST_UPCOMING_APPT_COMMAND = "a-list";
    private static final String SAMPLE_LIST_ALL_APPT_COMMAND = "a-listall";
    private static final String SAMPLE_FIND_APPT_COMMAND = "a-find John";
    private static final String SAMPLE_FILTER_TODAY_APPT_COMMAND = "a-today";
    private static final String SAMPLE_FILTER_UPCOMING_APPT_COMMAND = "a-upcoming";
    private static final String SAMPLE_FILTER_COMPLETED_APPT_COMMAND = "a-completed";
    private static final String SAMPLE_FILTER_MISSED_APPT_COMMAND = "a-missed";

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~MISC COMMANDS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static final String SAMPLE_HELP_COMMAND = "help";
    private static final String SAMPLE_UNDO_COMMAND = "undo";
    private static final String SAMPLE_REDO_COMMAND = "redo";

    @TempDir
    public Path testFolder;
    private StorageManager storageManager;
    private Model model = new ModelManager();
    private Logic logic;
    private UserHistoryManager historyManager;

    /**
     * Initialises a dummy ModelManager to aid with testing
     */
    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storageManager);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~General Testing of UndoCommand~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Test basic undo functionality
     */
    @Test
    public void testUndo() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        int originalSize = model.getAddressBook().getPatientList().size();
        model.undoHistory();
        int newSize = model.getAddressBook().getPatientList().size();

        //asserts that the size of patient list before undo is not equal to after undo is executed.
        assertNotEquals(originalSize, newSize);
    }

    /**
     * Test undo after running multiple commands
     */
    @Test
    public void testConsecutiveCommandFollowedByUndo() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_2);
        int originalSize = model.getAddressBook().getPatientList().size();
        CommandResult undoCommand = new UndoCommand().execute(model);
        int newSize = model.getAddressBook().getPatientList().size();

        assertEquals(originalSize, newSize + 1);
    }

    /**
     * Test multiple undo after running multiple commands
     */
    @Test
    public void testConsecutiveCommandFollowedByConsecutiveUndo() throws CommandException, ParseException {
        Patient validPatientOne = new PatientBuilder().build();
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_2);
        int originalSize = model.getAddressBook().getPatientList().size();
        CommandResult undoCommand = new UndoCommand().execute(model);
        CommandResult undoCommandTwo = new UndoCommand().execute(model);
        int newSize = model.getAddressBook().getPatientList().size();

        assertEquals(originalSize, newSize + 2);
    }

    /**
     * Test multiple undo after a single command
     */
    @Test
    public void testMultipleUndoAfterSingleCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
            CommandResult undoCommandTwo = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Integration Testing of UndoCommand with patient commands~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Test undo functionality with add patient command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterAddPatientCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        model.undoHistory();

        boolean containsSamplePatient = false;

        for (Patient patient : model.getAddressBook().getPatientList()) {
            if (patient.getName().toString().equals("John Doe")) {
                containsSamplePatient = true;
            }
        }

        assertFalse(containsSamplePatient);
    }

    /**
     * Test undo functionality with edit patient command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterEditPatientCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_2);
        CommandResult commandResultThree = logic.execute(SAMPLE_EDIT_PATIENT_COMMAND);

        String nameBeforeUndo = model.getFilteredPatientList().get(0).getName().fullName;
        model.undoHistory();
        String nameAfterUndo = model.getFilteredPatientList().get(0).getName().fullName;

        //Note:The 2nd add patient command is undone instead of patient edit command.
        assertNotEquals(nameBeforeUndo, nameAfterUndo);
    }

    /**
     * Test undo functionality with list patient command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterListPatientCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_LIST_PATIENTS_COMMAND);

        int sizeBeforeUndo = model.getFilteredPatientList().size();
        model.undoHistory();
        int sizeAfterUndo = model.getFilteredPatientList().size();

        //Note:The add patient command is undone instead of list patient command.
        assertNotEquals(sizeBeforeUndo, sizeAfterUndo);
    }

    /**
     * Test undo functionality with find patient command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFindPatientCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_FIND_PATIENT_COMMAND);

        int sizeBeforeUndo = model.getAddressBook().getPatientList().size();
        model.undoHistory();
        int sizeAfterUndo = model.getAddressBook().getPatientList().size();

        //Note:The add patient command is undone instead of find patient command.
        assertNotEquals(sizeBeforeUndo, sizeAfterUndo);
    }

    /**
     * Test undo functionality with delete patient command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterDeletePatientCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_DELETE_PATIENT_COMMAND);

        int sizeBeforeUndo = model.getAddressBook().getPatientList().size();
        model.undoHistory();
        int sizeAfterUndo = model.getAddressBook().getPatientList().size();

        assertEquals(sizeAfterUndo, sizeBeforeUndo + 1);
    }

    /**
     * Test undo functionality with add patient remark command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterAddPatientRemarkCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_ADD_PATIENT_REMARK_COMMAND);

        String remarkBeforeUndo = model.getAddressBook().getPatientList().get(0).getRemark().toString();
        model.undoHistory();
        String remarkAfterUndo = model.getAddressBook().getPatientList().get(0).getRemark().toString();

        assertNotEquals(remarkBeforeUndo, remarkAfterUndo);
    }

    /**
     * Test undo functionality with delete patient remark command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterRemovePatientRemarkCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_ADD_PATIENT_REMARK_COMMAND);
        String remarkBeforeUndo = model.getAddressBook().getPatientList().get(0).getRemark().value;

        CommandResult commandResultThree = logic.execute(SAMPLE_REMOVE_PATIENT_REMARK_COMMAND);
        model.undoHistory();

        String remarkAfterUndo = model.getAddressBook().getPatientList().get(0).getRemark().value;

        assertEquals(remarkBeforeUndo, remarkAfterUndo);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Integration Testing of UndoCommand with appointment commands~~~~~~~~~~~~~~~~~~~~~

    /**
     * Test undo functionality with schedule appointment command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterScheduleApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_SCHEDULE_APPT_COMMAND);

        model.undoHistory();

        assertEquals(0, model.getAddressBook().getAppointmentList().size());
    }

    /**
     * Test undo functionality with delete appointment command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterDeleteApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_SCHEDULE_APPT_COMMAND);
        CommandResult commandResultThree = logic.execute(SAMPLE_DELETE_APPT_COMMAND);

        model.undoHistory();

        assertEquals(1, model.getAddressBook().getAppointmentList().size());
    }

    /**
     * Test undo functionality with edit appointment command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterEditApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_SCHEDULE_APPT_COMMAND);
        Appointment initialAppt = model.getAddressBook().getAppointmentList().get(0);
        CommandResult commandResultThree = logic.execute(SAMPLE_EDIT_APPT_COMMAND);

        model.undoHistory();

        Appointment afterUndo = model.getAddressBook().getAppointmentList().get(0);

        assertEquals(afterUndo, initialAppt);
    }

    /**
     * Test undo functionality with complete appointment command
     * Compatible with undo: YES
     */
    @Test
    public void testUndoAfterCompleteApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_ADD_PATIENT_COMMAND_1);
        CommandResult commandResultTwo = logic.execute(SAMPLE_SCHEDULE_APPT_COMMAND);
        boolean initialApptBool = model.getAddressBook().getAppointmentList().get(0).isCompleted();
        CommandResult commandResultThree = logic.execute(SAMPLE_COMPLETE_APPT_COMMAND);

        model.undoHistory();

        boolean afterUndoBool = model.getAddressBook().getAppointmentList().get(0).isCompleted();

        assertEquals(afterUndoBool, initialApptBool);
    }

    /**
     * Test undo functionality with list upcoming appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterListApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_LIST_UPCOMING_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with list all appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterListAllApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_LIST_ALL_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with find appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFindApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_FIND_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with filter today's appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFilterTodayApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_FILTER_TODAY_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with filter upcoming appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFilterUpcomingApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_FILTER_UPCOMING_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with filter completed appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFilterCompletedApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_FILTER_COMPLETED_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

    /**
     * Test undo functionality with filter missed appointments command
     * Compatible with undo: NO
     */
    @Test
    public void testUndoAfterFilterMissedApptCommand() throws CommandException, ParseException {
        CommandResult commandResultOne = logic.execute(SAMPLE_FILTER_MISSED_APPT_COMMAND);

        try {
            CommandResult undoCommand = new UndoCommand().execute(model);
        } catch (CommandException e) {
            assertEquals(model.getUserHistoryManager().getUserHistorySize(), 1);
        }
    }

}
