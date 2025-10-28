package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void addPersonToTeam_hackathonInInterested_movesToParticipating() {
        // Setup: Create a person with an interested hackathon
        seedu.address.model.person.Person person = new seedu.address.testutil.PersonBuilder()
                .withName("Test Person")
                .withEmail("test@example.com")
                .withTelegram("testperson")
                .withGitHub("testperson")
                .withInterestedHackathons("AI Challenge 2024")
                .build();

        // Create a team with the same hackathon
        seedu.address.model.team.Team team = new seedu.address.model.team.Team(
                new seedu.address.model.team.TeamName("Test Team"),
                new seedu.address.model.hackathon.HackathonName("AI Challenge 2024"),
                new java.util.HashSet<>());

        modelManager.addPerson(person);
        modelManager.addTeam(team);

        // Before adding to team: hackathon should be in interested, not in participating
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
        assertTrue(person.getParticipatingHackathons().isEmpty());

        // Add person to team
        modelManager.addPersonToTeam(team, person);

        // After adding to team: hackathon should be in participating, NOT in interested
        seedu.address.model.person.Person updatedPerson = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(person))
                .findFirst()
                .get();

        assertFalse(updatedPerson.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")),
                "Hackathon should be removed from interestedHackathons");
        assertTrue(updatedPerson.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")),
                "Hackathon should be added to participatingHackathons");
    }

    @Test
    public void removePersonFromTeam_hackathonInParticipating_removedFromParticipating() {
        // Setup: Create a person with participating hackathon
        seedu.address.model.person.Person person = new seedu.address.testutil.PersonBuilder()
                .withName("Test Person")
                .withEmail("test@example.com")
                .withTelegram("testperson")
                .withGitHub("testperson")
                .withParticipatingHackathons("AI Challenge 2024")
                .build();
        // Create a team with the same hackathon
        seedu.address.model.team.Team team = new seedu.address.model.team.Team(
                new seedu.address.model.team.TeamName("Test Team"),
                new seedu.address.model.hackathon.HackathonName("AI Challenge 2024"),
                new java.util.HashSet<>());
        modelManager.addPerson(person);
        modelManager.addTeam(team);
        // Add person to team first - this returns the updated team
        seedu.address.model.team.Team updatedTeam = modelManager.addPersonToTeam(team, person);
        // Get the updated person after adding to team
        seedu.address.model.person.Person personAfterAdd = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(person))
                .findFirst()
                .get();
        // Remove person from team using the updated team reference
        modelManager.removePersonFromTeam(updatedTeam, personAfterAdd);
        // After removing from team: hackathon should be removed from participating, NOT added to interested
        seedu.address.model.person.Person updatedPerson = modelManager.getFilteredPersonList().stream()
                .filter(p -> p.isSamePerson(person))
                .findFirst()
                .get();
        assertFalse(updatedPerson.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")),
                "Hackathon should NOT be added back to interestedHackathons");
        assertFalse(updatedPerson.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")),
                "Hackathon should be removed from participatingHackathons");
    }
}
