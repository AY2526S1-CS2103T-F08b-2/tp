package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTeams;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    // New test files for team functionality
    private static final Path TYPICAL_TEAMS_FILE = TEST_DATA_FOLDER.resolve("typicalTeamsAddressBook.json");
    private static final Path INVALID_TEAM_FILE = TEST_DATA_FOLDER.resolve("invalidTeamAddressBook.json");
    private static final Path DUPLICATE_TEAM_FILE = TEST_DATA_FOLDER.resolve("duplicateTeamAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalTeamsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TEAMS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        // Verify that teams are loaded correctly
        assertEquals(2, addressBookFromFile.getTeamList().size());
        assertEquals(2, addressBookFromFile.getPersonList().size());
    }

    @Test
    public void toModelType_invalidTeamFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_TEAM_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTeams_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEAM_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_TEAM,
                dataFromFile::toModelType);
    }

    @Test
    public void constructor_typicalAddressBookWithTeams_success() {
        AddressBook originalAddressBook = TypicalTeams.getTypicalAddressBookWithTeams();
        JsonSerializableAddressBook jsonAddressBook = new JsonSerializableAddressBook(originalAddressBook);

        // The constructor should not throw any exceptions
        // Actual validation happens during toModelType()
    }

    @Test
    public void toModelType_roundTripConversion_success() throws Exception {
        AddressBook originalAddressBook = TypicalTeams.getTypicalAddressBookWithTeams();
        JsonSerializableAddressBook jsonAddressBook = new JsonSerializableAddressBook(originalAddressBook);
        AddressBook reconstructedAddressBook = jsonAddressBook.toModelType();

        assertEquals(originalAddressBook, reconstructedAddressBook);
    }

    @Test
    public void toModelType_emptyAddressBook_success() throws Exception {
        AddressBook emptyAddressBook = new AddressBook();
        JsonSerializableAddressBook jsonAddressBook = new JsonSerializableAddressBook(emptyAddressBook);
        AddressBook reconstructedAddressBook = jsonAddressBook.toModelType();

        assertEquals(emptyAddressBook, reconstructedAddressBook);
        assertEquals(0, reconstructedAddressBook.getPersonList().size());
        assertEquals(0, reconstructedAddressBook.getTeamList().size());
    }

}
