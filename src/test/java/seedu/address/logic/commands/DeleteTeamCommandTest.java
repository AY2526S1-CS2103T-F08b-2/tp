package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TeamBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteTeamCommand}.
 */
public class DeleteTeamCommandTest {

    @Test
    public void execute_validIndexUnfilteredListWithMembers_success() {
        // Create persons
        Person alice = new PersonBuilder().withName("Alice").withEmail("alice@test.com")
                .withTelegram("alice").withGitHub("alice").build();
        Person bob = new PersonBuilder().withName("Bob").withEmail("bob@test.com")
                .withTelegram("bob").withGitHub("bob").build();

        // Create team with members
        Team teamWithMembers = new TeamBuilder()
                .withTeamName("Team Alpha")
                .withHackathonName("Hackathon 2024")
                .withMembers(alice, bob)
                .build();

        // Create persons with team
        Person aliceWithTeam = new PersonBuilder(alice).withTeam(teamWithMembers).build();
        Person bobWithTeam = new PersonBuilder(bob).withTeam(teamWithMembers).build();

        // Build model
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(aliceWithTeam);
        addressBook.addPerson(bobWithTeam);
        addressBook.addTeam(teamWithMembers);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute delete
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(DeleteTeamCommand.MESSAGE_DELETE_TEAM_SUCCESS,
                Messages.format(teamWithMembers));

        // Build expected model
        AddressBook expectedAddressBook = new AddressBook();
        Person aliceWithoutTeam = new PersonBuilder(alice).build();
        Person bobWithoutTeam = new PersonBuilder(bob).build();
        expectedAddressBook.addPerson(aliceWithoutTeam);
        expectedAddressBook.addPerson(bobWithoutTeam);
        ModelManager expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());

        assertCommandSuccess(deleteTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListEmptyTeam_success() {
        // Create empty team (no members)
        Team emptyTeam = new TeamBuilder()
                .withTeamName("Empty Team")
                .withHackathonName("Test Hackathon")
                .withoutMembers()
                .build();

        // Build model
        AddressBook addressBook = new AddressBook();
        addressBook.addTeam(emptyTeam);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute delete
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(DeleteTeamCommand.MESSAGE_DELETE_TEAM_SUCCESS,
                Messages.format(emptyTeam));

        // Build expected model (empty address book)
        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

        assertCommandSuccess(deleteTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Team team = new TeamBuilder().withTeamName("Team One").withoutMembers().build();

        AddressBook addressBook = new AddressBook();
        addressBook.addTeam(team);
        Model model = new ModelManager(addressBook, new UserPrefs());

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTeamList().size() + 1);
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(outOfBoundIndex);

        assertCommandFailure(deleteTeamCommand, model, Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Create two teams
        Team team1 = new TeamBuilder().withTeamName("Team Alpha").withoutMembers().build();
        Team team2 = new TeamBuilder().withTeamName("Team Beta").withoutMembers().build();

        AddressBook addressBook = new AddressBook();
        addressBook.addTeam(team1);
        addressBook.addTeam(team2);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Filter to show only first team
        model.updateFilteredTeamList(team -> team.getTeamName().equals(team1.getTeamName()));

        // Delete the first team in filtered list
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(DeleteTeamCommand.MESSAGE_DELETE_TEAM_SUCCESS,
                Messages.format(team1));

        // Build expected model with only team2
        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addTeam(team2);
        Model expectedModel = new ModelManager(expectedAddressBook, new UserPrefs());
        expectedModel.updateFilteredTeamList(team -> team.getTeamName().equals(team1.getTeamName()));

        assertCommandSuccess(deleteTeamCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Team team1 = new TeamBuilder().withTeamName("Team Alpha").withoutMembers().build();
        Team team2 = new TeamBuilder().withTeamName("Team Beta").withoutMembers().build();

        AddressBook addressBook = new AddressBook();
        addressBook.addTeam(team1);
        addressBook.addTeam(team2);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Filter to show only first team
        model.updateFilteredTeamList(team -> team.getTeamName().equals(team1.getTeamName()));

        // Try to delete index 1 (which doesn't exist in filtered list)
        Index outOfBoundIndex = Index.fromZeroBased(1);
        // Ensure that outOfBoundIndex is still valid in unfiltered list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTeamList().size());

        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(outOfBoundIndex);

        assertCommandFailure(deleteTeamCommand, model, Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteTeamWithMultipleMembers_allMembersUpdated() {
        // Create three persons
        Person person1 = new PersonBuilder().withName("Person 1").withEmail("p1@test.com")
                .withTelegram("person1").withGitHub("person1").build();
        Person person2 = new PersonBuilder().withName("Person 2").withEmail("p2@test.com")
                .withTelegram("person2").withGitHub("person2").build();
        Person person3 = new PersonBuilder().withName("Person 3").withEmail("p3@test.com")
                .withTelegram("person3").withGitHub("person3").build();

        // Create team with all three members
        Team team = new TeamBuilder()
                .withTeamName("Big Team")
                .withHackathonName("Major Hackathon")
                .withMembers(person1, person2, person3)
                .build();

        // Create persons with team
        Person p1WithTeam = new PersonBuilder(person1).withTeam(team).build();
        Person p2WithTeam = new PersonBuilder(person2).withTeam(team).build();
        Person p3WithTeam = new PersonBuilder(person3).withTeam(team).build();

        // Build model
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(p1WithTeam);
        addressBook.addPerson(p2WithTeam);
        addressBook.addPerson(p3WithTeam);
        addressBook.addTeam(team);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute delete
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        try {
            deleteTeamCommand.execute(model);

            // Verify all persons have no team
            for (Person person : model.getAddressBook().getPersonList()) {
                assertTrue(person.getTeams().isEmpty(),
                        "Person " + person.getName() + " should have no team after deletion");
            }

            // Verify team is deleted
            assertTrue(model.getAddressBook().getTeamList().isEmpty(),
                    "Team list should be empty after deletion");

        } catch (Exception e) {
            throw new AssertionError("Execution should not fail", e);
        }
    }

    @Test
    public void execute_deleteTeamWithPartialMembers_onlyTeamMembersUpdated() {
        // Create persons
        Person teamMember = new PersonBuilder().withName("Team Member")
                .withEmail("member@test.com").withTelegram("member").withGitHub("member").build();
        Person nonMember = new PersonBuilder().withName("Non Member")
                .withEmail("non@test.com").withTelegram("non").withGitHub("non").build();

        // Create team with one member
        Team team = new TeamBuilder()
                .withTeamName("Partial Team")
                .withHackathonName("Test Hack")
                .withMembers(teamMember)
                .build();

        // Create another team for the non-member
        Team otherTeam = new TeamBuilder()
                .withTeamName("Other Team")
                .withHackathonName("Other Hack")
                .withMembers(nonMember)
                .build();

        // Assign teams
        Person memberWithTeam = new PersonBuilder(teamMember).withTeam(team).build();
        Person nonMemberWithTeam = new PersonBuilder(nonMember).withTeam(otherTeam).build();

        // Build model
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(memberWithTeam);
        addressBook.addPerson(nonMemberWithTeam);
        addressBook.addTeam(team);
        addressBook.addTeam(otherTeam);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Execute delete on first team
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        try {
            deleteTeamCommand.execute(model);

            // Verify team member has no team
            Person updatedMember = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().fullName.equals("Team Member"))
                    .findFirst()
                    .orElseThrow();
            assertTrue(updatedMember.getTeams().isEmpty(),
                    "Team member should have no team after deletion");

            // Verify non-member still has their team
            Person updatedNonMember = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().fullName.equals("Non Member"))
                    .findFirst()
                    .orElseThrow();
            assertFalse(updatedNonMember.getTeams().isEmpty(),
                    "Non-member should still have their team");
            assertTrue(updatedNonMember.getTeams().stream()
                    .anyMatch(t -> t.getTeamName().equals(otherTeam.getTeamName())),
                    "Non-member should still have the same team");

        } catch (Exception e) {
            throw new AssertionError("Execution should not fail", e);
        }
    }

    @Test
    public void execute_deleteTeam_hackathonNotAddedToInterested() {
        // Create a person with no interested hackathons initially
        Person person = new PersonBuilder()
                .withName("Test Person")
                .withEmail("test@test.com")
                .withTelegram("testperson")
                .withGitHub("testperson")
                .build();

        // Create team with hackathon and add the person to it
        Team team = new TeamBuilder()
                .withTeamName("Test Team")
                .withHackathonName("Test Hackathon")
                .withMembers(person)
                .build();

        // Create person with team (will have hackathon in participating)
        Person personWithTeam = new PersonBuilder(person)
                .withTeam(team)
                .withParticipatingHackathons("Test Hackathon")
                .build();

        // Build model
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(personWithTeam);
        addressBook.addTeam(team);
        Model model = new ModelManager(addressBook, new UserPrefs());

        // Store the size of interested hackathons before deletion
        int interestedHackathonsSizeBefore = personWithTeam.getInterestedHackathons().size();

        // Execute delete team
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(Index.fromZeroBased(0));

        try {
            deleteTeamCommand.execute(model);

            // Get updated person from model
            Person updatedPerson = model.getAddressBook().getPersonList().get(0);

            // Verify the person has no team
            assertTrue(updatedPerson.getTeams().isEmpty(),
                    "Person should have no team after team deletion");

            // Verify the hackathon is NOT in participating hackathons
            assertEquals(0, updatedPerson.getParticipatingHackathons().size(),
                    "Person should have no participating hackathons after team deletion");

            // Verify the hackathon is NOT added to interested hackathons
            assertEquals(interestedHackathonsSizeBefore, updatedPerson.getInterestedHackathons().size(),
                    "Interested hackathons size should remain the same");

            assertFalse(updatedPerson.getInterestedHackathons().stream()
                    .anyMatch(h -> h.value.equals("Test Hackathon")),
                    "Test Hackathon should NOT be added to interested hackathons");

        } catch (Exception e) {
            throw new AssertionError("Execution should not fail", e);
        }
    }

    @Test
    public void equals() {
        DeleteTeamCommand deleteFirstCommand = new DeleteTeamCommand(INDEX_FIRST_PERSON);
        DeleteTeamCommand deleteSecondCommand = new DeleteTeamCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTeamCommand deleteFirstCommandCopy = new DeleteTeamCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different team -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTeamCommand deleteTeamCommand = new DeleteTeamCommand(targetIndex);
        String expected = DeleteTeamCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteTeamCommand.toString());
    }
}

