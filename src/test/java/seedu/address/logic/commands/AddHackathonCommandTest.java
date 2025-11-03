package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddHackathonCommand.
 */
public class AddHackathonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        assertThrows(NullPointerException.class, () -> new AddHackathonCommand(null, hackathons));
    }

    @Test
    public void constructor_nullHackathons_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddHackathonCommand(INDEX_FIRST_PERSON, null));
    }

    @Test
    public void execute_addSingleHackathonUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("NewHackathon"));

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonsToAdd);

        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(firstPerson.getInterestedHackathons());
        updatedInterestedHackathons.addAll(hackathonsToAdd);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withInterestedHackathons(updatedInterestedHackathons.stream()
                        .map(h -> h.value)
                        .toArray(String[]::new))
                .build();

        String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ADD_HACKATHON_SUCCESS,
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addHackathonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addMultipleHackathonsUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("HackathonA"));
        hackathonsToAdd.add(new HackathonName("HackathonB"));

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonsToAdd);

        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(firstPerson.getInterestedHackathons());
        updatedInterestedHackathons.addAll(hackathonsToAdd);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withInterestedHackathons(updatedInterestedHackathons.stream()
                        .map(h -> h.value)
                        .toArray(String[]::new))
                .build();

        String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ADD_HACKATHON_SUCCESS,
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addHackathonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_alreadyInterestedHackathon_ignoresDuplicate() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Get an existing interested hackathon
        if (!firstPerson.getInterestedHackathons().isEmpty()) {
            HackathonName existingHackathon = firstPerson.getInterestedHackathons().iterator().next();

            Set<HackathonName> hackathonsToAdd = new HashSet<>();
            hackathonsToAdd.add(existingHackathon);

            AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonsToAdd);

            // Should not fail, just keep existing hackathon
            String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ALREADY_INTERESTED,
                    firstPerson.getName());

            assertCommandFailure(addHackathonCommand, model, expectedMessage);
        }
    }

    @Test
    public void execute_hackathonAlreadyParticipating_throwsCommandException() {
        Person personWithParticipatingHackathon = new PersonBuilder()
                .withName("Test Person")
                .withEmail("test@example.com")
                .withTelegram("test_tg")
                .withGitHub("test-github")
                .withParticipatingHackathons("NUSHack")
                .build();

        model.addPerson(personWithParticipatingHackathon);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("NUSHack"));

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(indexOfPerson, hackathonsToAdd);

        String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ALREADY_PARTICIPATING, "NUSHack");

        assertCommandFailure(addHackathonCommand, model, expectedMessage);
    }

    @Test
    public void execute_hackathonAlreadyParticipatingCaseInsensitive_throwsCommandException() {
        Person personWithParticipatingHackathon = new PersonBuilder()
                .withName("Test Person")
                .withEmail("test@example.com")
                .withTelegram("test_tg")
                .withGitHub("test-github")
                .withParticipatingHackathons("NUSHack")
                .build();

        model.addPerson(personWithParticipatingHackathon);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("nushack")); // Different case

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(indexOfPerson, hackathonsToAdd);

        String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ALREADY_PARTICIPATING, "nushack");

        assertCommandFailure(addHackathonCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("NUSHack"));

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(outOfBoundIndex, hackathonsToAdd);

        assertCommandFailure(addHackathonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("NUSHack"));
        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonsToAdd);

        assertThrows(NullPointerException.class, () -> addHackathonCommand.execute(null));
    }

    @Test
    public void equals() {
        Set<HackathonName> hackathonSet1 = new HashSet<>();
        hackathonSet1.add(new HackathonName("NUSHack"));
        Set<HackathonName> hackathonSet2 = new HashSet<>();
        hackathonSet2.add(new HackathonName("iNTUition"));

        AddHackathonCommand addHackathonFirstCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonSet1);
        AddHackathonCommand addHackathonSecondCommand = new AddHackathonCommand(INDEX_SECOND_PERSON, hackathonSet1);
        AddHackathonCommand addHackathonDifferentHackathonCommand =
                new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonSet2);

        // same object -> returns true
        assertTrue(addHackathonFirstCommand.equals(addHackathonFirstCommand));

        // same values -> returns true
        AddHackathonCommand addHackathonFirstCommandCopy = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonSet1);
        assertTrue(addHackathonFirstCommand.equals(addHackathonFirstCommandCopy));

        // different types -> returns false
        assertFalse(addHackathonFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addHackathonFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addHackathonFirstCommand.equals(addHackathonSecondCommand));

        // different hackathon -> returns false
        assertFalse(addHackathonFirstCommand.equals(addHackathonDifferentHackathonCommand));
    }

    @Test
    public void toStringMethod() {
        Set<HackathonName> hackathonSet = new HashSet<>();
        hackathonSet.add(new HackathonName("NUSHack"));
        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonSet);
        String expected = "AddHackathonCommand{targetIndex=" + INDEX_FIRST_PERSON
                + ", hackathonsToAdd=" + hackathonSet + "}";
        assertEquals(expected, addHackathonCommand.toString());
    }

    @Test
    public void execute_addHackathonWithMixedCase_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<HackathonName> hackathonsToAdd = new HashSet<>();
        hackathonsToAdd.add(new HackathonName("HaCkAtHoN"));

        AddHackathonCommand addHackathonCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, hackathonsToAdd);

        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(firstPerson.getInterestedHackathons());
        updatedInterestedHackathons.addAll(hackathonsToAdd);

        Person editedPerson = new PersonBuilder(firstPerson)
                .withInterestedHackathons(updatedInterestedHackathons.stream()
                        .map(h -> h.value)
                        .toArray(String[]::new))
                .build();

        String expectedMessage = String.format(AddHackathonCommand.MESSAGE_ADD_HACKATHON_SUCCESS,
                editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addHackathonCommand, model, expectedMessage, expectedModel);
    }
}
