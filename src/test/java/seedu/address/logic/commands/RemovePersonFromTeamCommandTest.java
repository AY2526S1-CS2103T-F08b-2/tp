package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

public class RemovePersonFromTeamCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPersonAndTeam_success() throws Exception {
        // Create a team and add a person into it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Person> initialMembers = new HashSet<>();
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        // Add the person to the team using AddPersonToTeamCommand
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPersonToTeamCommand addCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);
        addCommand.execute(model);

        // Now remove the person using RemovePersonFromTeamCommand
        RemovePersonFromTeamCommand removeCommand = new RemovePersonFromTeamCommand(teamName, INDEX_FIRST_PERSON);
        CommandResult result = removeCommand.execute(model);

        String expectedMessage = String.format(RemovePersonFromTeamCommand.MESSAGE_SUCCESS,
                personToModify.getName(), team.getTeamName());

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify the team no longer has the person
        Team updatedTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow();

        assertEquals(false, updatedTeam.hasMember(personToModify));

        // Verify that the person no longer has the team in their teams set
        Person updatedPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(personToModify.getName()))
                .findFirst()
                .orElseThrow();

        assertEquals(false, updatedPerson.getTeams().stream()
                .anyMatch(t -> t.getTeamName().equals(teamName)));
    }

    @Test
    public void execute_personNotInTeam_throwsCommandException() throws Exception {
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Team team = new Team(teamName, hackathonName, new HashSet<>());
        model.addTeam(team);

        // Person is not in the team yet, removing should throw
        RemovePersonFromTeamCommand removeCommand = new RemovePersonFromTeamCommand(teamName, INDEX_FIRST_PERSON);

        assertCommandFailure(removeCommand, model,
                String.format(RemovePersonFromTeamCommand.MESSAGE_PERSON_NOT_IN_TEAM,
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                        teamName));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        TeamName teamName = new TeamName("Test Team");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemovePersonFromTeamCommand command = new RemovePersonFromTeamCommand(teamName, outOfBoundIndex);

        assertCommandFailure(command, model, RemovePersonFromTeamCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        TeamName nonExistentTeamName = new TeamName("Non Existent Team");
        RemovePersonFromTeamCommand command = new RemovePersonFromTeamCommand(nonExistentTeamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model,
                String.format(RemovePersonFromTeamCommand.MESSAGE_TEAM_NOT_FOUND, nonExistentTeamName));
    }

    @Test
    public void execute_removePersonFromTeam_hackathonAddedBackToInterested() throws Exception {
        // Create a team with a hackathon
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Person> initialMembers = new HashSet<>();
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        // Add person to the team
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPersonToTeamCommand addCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);
        addCommand.execute(model);

        // Verify the person is now participating in the hackathon
        Person personAfterAdd = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(personToModify.getName()))
                .findFirst()
                .orElseThrow();

        assertEquals(true, personAfterAdd.getParticipatingHackathons().contains(hackathonName));

        // Store the size of interested hackathons before removal
        int interestedHackathonsSizeBefore = personAfterAdd.getInterestedHackathons().size();

        // Remove the person from the team
        RemovePersonFromTeamCommand removeCommand = new RemovePersonFromTeamCommand(teamName, INDEX_FIRST_PERSON);
        removeCommand.execute(model);

        // Verify the hackathon is removed from participating hackathons
        Person personAfterRemove = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(personToModify.getName()))
                .findFirst()
                .orElseThrow();

        // The hackathon should NOT be in participating hackathons
        assertEquals(false, personAfterRemove.getParticipatingHackathons().contains(hackathonName));

        // The hackathon SHOULD be added back to interested hackathons
        assertEquals(true, personAfterRemove.getInterestedHackathons().contains(hackathonName));

        // The size of interested hackathons should increase by 1
        assertEquals(interestedHackathonsSizeBefore + 1, personAfterRemove.getInterestedHackathons().size());
    }

    @Test
    public void execute_teamNameDifferentCase_success() throws Exception {
        // Create a team and add a person into it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Person> initialMembers = new HashSet<>();
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        // Add the person to the team using AddPersonToTeamCommand
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddPersonToTeamCommand addCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);
        addCommand.execute(model);

        // Remove the person using different case for team name - should still work
        TeamName differentCaseTeamName = new TeamName("TEST TEAM");
        RemovePersonFromTeamCommand removeCommand = new RemovePersonFromTeamCommand(
                differentCaseTeamName, INDEX_FIRST_PERSON);
        CommandResult result = removeCommand.execute(model);

        String expectedMessage = String.format(RemovePersonFromTeamCommand.MESSAGE_SUCCESS,
                Messages.format(personToModify), Messages.format(team));

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify the team no longer has the person
        Team updatedTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow();

        assertEquals(false, updatedTeam.hasMember(personToModify));
    }

    @Test
    public void execute_personNotInTeamDifferentCase_throwsCommandException() throws Exception {
        // Create a team without adding any person to it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Team team = new Team(teamName, hackathonName, new HashSet<>());
        model.addTeam(team);

        // Try to remove person using different case for team name - should still throw correct error
        TeamName differentCaseTeamName = new TeamName("test team");
        RemovePersonFromTeamCommand removeCommand = new RemovePersonFromTeamCommand(
                differentCaseTeamName, INDEX_FIRST_PERSON);

        assertCommandFailure(removeCommand, model,
                String.format(RemovePersonFromTeamCommand.MESSAGE_PERSON_NOT_IN_TEAM,
                        Messages.format(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())),
                        differentCaseTeamName));
    }

    @Test
    public void equals() {
        TeamName teamName = new TeamName("Team A");
        RemovePersonFromTeamCommand firstCommand = new RemovePersonFromTeamCommand(teamName, INDEX_FIRST_PERSON);
        RemovePersonFromTeamCommand secondCommand = new RemovePersonFromTeamCommand(teamName, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        assertEquals(firstCommand, secondCommand);

        // different types -> returns false
        assertEquals(false, firstCommand.equals(1));

        // null -> returns false
        assertEquals(false, firstCommand.equals(null));
    }
}
