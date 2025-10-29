package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveHackathonCommand.
 */
public class RemoveHackathonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests successful removal of a single hackathon from interested list.
     */
    @Test
    public void execute_validSingleHackathon_success() {
        Person personWithHackathon = new PersonBuilder()
                .withName("John Doe")
                .withEmail("john@example.com")
                .withTelegram("johndoe")
                .withGitHub("johndoe")
                .withInterestedHackathons("NUSHack", "TechChallenge")
                .build();

        model.addPerson(personWithHackathon);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("NUSHack"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        Person expectedPerson = new PersonBuilder(personWithHackathon)
                .withInterestedHackathons("TechChallenge")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithHackathon, expectedPerson);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_DELETE_HACKATHON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Tests successful removal of multiple hackathons from interested list.
     */
    @Test
    public void execute_validMultipleHackathons_success() {
        Person personWithHackathons = new PersonBuilder()
                .withName("Jane Smith")
                .withEmail("jane@example.com")
                .withTelegram("janesmith")
                .withGitHub("janesmith")
                .withInterestedHackathons("NUSHack", "TechChallenge", "AIContest")
                .build();

        model.addPerson(personWithHackathons);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("NUSHack"));
        hackathonsToDelete.add(new HackathonName("TechChallenge"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        Person expectedPerson = new PersonBuilder(personWithHackathons)
                .withInterestedHackathons("AIContest")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithHackathons, expectedPerson);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_DELETE_HACKATHON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Tests removal of a hackathon that is not in the interested list.
     */
    @Test
    public void execute_hackathonNotFound_throwsCommandException() {
        Person personWithHackathon = new PersonBuilder()
                .withName("Bob Lee")
                .withEmail("bob@example.com")
                .withTelegram("boblee")
                .withGitHub("boblee")
                .withInterestedHackathons("NUSHack")
                .build();

        model.addPerson(personWithHackathon);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("TechChallenge"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_HACKATHON_NOT_FOUND,
                "TechChallenge", Messages.format(personWithHackathon));

        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Tests removal of a hackathon that is in the participating list.
     */
    @Test
    public void execute_hackathonInParticipating_throwsCommandException() {
        Person personWithParticipating = new PersonBuilder()
                .withName("Alice Wong")
                .withEmail("alice@example.com")
                .withTelegram("alicewong")
                .withGitHub("alicewong")
                .withInterestedHackathons("TechChallenge")
                .withParticipatingHackathons("NUSHack")
                .build();

        model.addPerson(personWithParticipating);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("NUSHack"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_HACKATHON_IN_PARTICIPATING,
                "NUSHack");

        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Tests removal with an invalid person index.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("NUSHack"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(outOfBoundIndex, hackathonsToDelete);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests removal with no hackathons provided.
     */
    @Test
    public void execute_noHackathonsProvided_throwsCommandException() {
        Set<HackathonName> emptyHackathons = new HashSet<>();

        RemoveHackathonCommand command = new RemoveHackathonCommand(INDEX_FIRST_PERSON, emptyHackathons);

        assertCommandFailure(command, model, RemoveHackathonCommand.MESSAGE_NO_HACKATHONS_PROVIDED);
    }

    /**
     * Tests removal with case-insensitive hackathon names.
     */
    @Test
    public void execute_caseInsensitiveHackathonName_success() {
        Person personWithHackathon = new PersonBuilder()
                .withName("Charlie Brown")
                .withEmail("charlie@example.com")
                .withTelegram("charliebrown")
                .withGitHub("charliebrown")
                .withInterestedHackathons("NUSHack")
                .build();

        model.addPerson(personWithHackathon);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("nushack")); // lowercase

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        Person expectedPerson = new PersonBuilder(personWithHackathon)
                .withInterestedHackathons()
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithHackathon, expectedPerson);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_DELETE_HACKATHON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Tests removal of all interested hackathons.
     */
    @Test
    public void execute_deleteAllInterestedHackathons_success() {
        Person personWithHackathons = new PersonBuilder()
                .withName("David Lee")
                .withEmail("david@example.com")
                .withTelegram("davidlee")
                .withGitHub("davidlee")
                .withInterestedHackathons("NUSHack", "TechChallenge")
                .build();

        model.addPerson(personWithHackathons);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("NUSHack"));
        hackathonsToDelete.add(new HackathonName("TechChallenge"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        Person expectedPerson = new PersonBuilder(personWithHackathons)
                .withInterestedHackathons()
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithHackathons, expectedPerson);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_DELETE_HACKATHON_SUCCESS,
                Messages.format(expectedPerson));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Tests equals method with same object.
     */
    @Test
    public void equals_sameObject_returnsTrue() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons);

        assertTrue(command.equals(command));
    }

    /**
     * Tests equals method with same values.
     */
    @Test
    public void equals_sameValues_returnsTrue() {
        Set<HackathonName> hackathons1 = new HashSet<>();
        hackathons1.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command1 = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons1);

        Set<HackathonName> hackathons2 = new HashSet<>();
        hackathons2.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command2 = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons2);

        assertTrue(command1.equals(command2));
    }

    /**
     * Tests equals method with different index.
     */
    @Test
    public void equals_differentIndex_returnsFalse() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command1 = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons);
        RemoveHackathonCommand command2 = new RemoveHackathonCommand(Index.fromOneBased(2), hackathons);

        assertFalse(command1.equals(command2));
    }

    /**
     * Tests equals method with different hackathons.
     */
    @Test
    public void equals_differentHackathons_returnsFalse() {
        Set<HackathonName> hackathons1 = new HashSet<>();
        hackathons1.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command1 = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons1);

        Set<HackathonName> hackathons2 = new HashSet<>();
        hackathons2.add(new HackathonName("TechChallenge"));
        RemoveHackathonCommand command2 = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons2);

        assertFalse(command1.equals(command2));
    }

    /**
     * Tests equals method with null.
     */
    @Test
    public void equals_null_returnsFalse() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons);

        assertFalse(command.equals(null));
    }

    /**
     * Tests equals method with different type.
     */
    @Test
    public void equals_differentType_returnsFalse() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons);

        assertFalse(command.equals("string"));
    }

    /**
     * Tests toString method.
     */
    @Test
    public void toStringMethod() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("NUSHack"));
        RemoveHackathonCommand command = new RemoveHackathonCommand(INDEX_FIRST_PERSON, hackathons);

        String expected = "RemoveHackathonCommand{targetIndex=" + INDEX_FIRST_PERSON
                + ", hackathonsToDelete=" + hackathons + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Tests removal when one hackathon is valid but another is in participating list.
     */
    @Test
    public void execute_mixedValidAndParticipating_throwsCommandException() {
        Person personWithMixedHackathons = new PersonBuilder()
                .withName("Emma Wilson")
                .withEmail("emma@example.com")
                .withTelegram("emmawilson")
                .withGitHub("emmawilson")
                .withInterestedHackathons("TechChallenge")
                .withParticipatingHackathons("NUSHack")
                .build();

        model.addPerson(personWithMixedHackathons);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        Set<HackathonName> hackathonsToDelete = new HashSet<>();
        hackathonsToDelete.add(new HackathonName("TechChallenge"));
        hackathonsToDelete.add(new HackathonName("NUSHack"));

        RemoveHackathonCommand command = new RemoveHackathonCommand(indexOfPerson, hackathonsToDelete);

        String expectedMessage = String.format(RemoveHackathonCommand.MESSAGE_HACKATHON_IN_PARTICIPATING,
                "NUSHack");

        assertCommandFailure(command, model, expectedMessage);
    }
}

