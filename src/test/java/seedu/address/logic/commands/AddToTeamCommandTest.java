package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

public class AddToTeamCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPersonAndTeam_success() throws Exception {
        // Create a team first
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Person> initialMembers = new HashSet<>();
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        // Get a person to add
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddToTeamCommand command = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);

        // Execute the command and check the result message
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AddToTeamCommand.MESSAGE_SUCCESS,
                personToAdd.getName(), team.getTeamName());

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify that the team now contains the person
        Team updatedTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow();

        assertEquals(true, updatedTeam.hasMember(personToAdd));

        // Verify that the person now has the team in their teams set
        Person updatedPerson = model.getFilteredPersonList().stream()
                .filter(p -> p.getName().equals(personToAdd.getName()))
                .findFirst()
                .orElseThrow();

        assertEquals(true, updatedPerson.getTeams().stream()
                .anyMatch(t -> t.getTeamName().equals(teamName)));
    }

    @Test
    public void execute_personAlreadyInSameTeam_throwsCommandException() throws Exception {
        // Create an empty team first
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Team team = new Team(teamName, hackathonName, new HashSet<>());
        model.addTeam(team);

        // First, add the person to the team successfully
        AddToTeamCommand command1 = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);
        command1.execute(model);

        // Now try to add the same person to the same team again - this should throw an exception
        AddToTeamCommand command2 = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command2, model,
                String.format(AddToTeamCommand.MESSAGE_PERSON_ALREADY_IN_THIS_TEAM,
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(), teamName));
    }

    @Test
    public void execute_personAlreadyInDifferentTeamSameHackathon_throwsCommandException() throws Exception {
        // Create the first team and add a person to it
        TeamName teamName1 = new TeamName("Team Alpha");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Team team1 = new Team(teamName1, hackathonName, new HashSet<>());
        model.addTeam(team1);

        // Add the person to the first team
        AddToTeamCommand command1 = new AddToTeamCommand(teamName1, INDEX_FIRST_PERSON);
        command1.execute(model);

        // Create a second team with the same hackathon
        TeamName teamName2 = new TeamName("Team Beta");
        Team team2 = new Team(teamName2, hackathonName, new HashSet<>());
        model.addTeam(team2);

        // Try to add the same person to the second team -
        // should fail because they're already in a team for this hackathon
        AddToTeamCommand command2 = new AddToTeamCommand(teamName2, INDEX_FIRST_PERSON);

        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertCommandFailure(command2, model,
                String.format(AddToTeamCommand.MESSAGE_PERSON_ALREADY_IN_TEAM,
                        person.getName(), hackathonName));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        TeamName teamName = new TeamName("Test Team");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddToTeamCommand command = new AddToTeamCommand(teamName, outOfBoundIndex);

        assertCommandFailure(command, model, AddToTeamCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        TeamName nonExistentTeamName = new TeamName("Non Existent Team");
        AddToTeamCommand command = new AddToTeamCommand(nonExistentTeamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model,
                String.format(AddToTeamCommand.MESSAGE_TEAM_NOT_FOUND, nonExistentTeamName));
    }

    @Test
    public void execute_teamNameDifferentCase_success() throws Exception {
        // Create a team with original casing
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Person> initialMembers = new HashSet<>();
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        // Get a person to add
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Use different casing for team name - should still find the team
        TeamName differentCaseTeamName = new TeamName("TEST TEAM");
        AddToTeamCommand command = new AddToTeamCommand(differentCaseTeamName, INDEX_FIRST_PERSON);

        // Execute the command - should succeed
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AddToTeamCommand.MESSAGE_SUCCESS,
                personToAdd.getName(), team.getTeamName());

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify that the team now contains the person
        Team updatedTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow();

        assertEquals(true, updatedTeam.hasMember(personToAdd));
    }

    @Test
    public void execute_personAlreadyInSameTeamDifferentCase_throwsCommandException() throws Exception {
        // Create an empty team with original casing
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Team team = new Team(teamName, hackathonName, new HashSet<>());
        model.addTeam(team);

        // First, add the person to the team successfully
        AddToTeamCommand command1 = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);
        command1.execute(model);

        // Try to add the same person to the same team using different case - should still throw exception
        TeamName differentCaseTeamName = new TeamName("test team");
        AddToTeamCommand command2 = new AddToTeamCommand(differentCaseTeamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command2, model,
                String.format(AddToTeamCommand.MESSAGE_PERSON_ALREADY_IN_THIS_TEAM,
                        model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName(),
                        differentCaseTeamName));
    }

    @Test
    public void equals() {
        TeamName teamName = new TeamName("Team A");
        AddToTeamCommand addToTeamFirstCommand = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);
        AddToTeamCommand addToTeamSecondCommand = new AddToTeamCommand(teamName, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertEquals(addToTeamFirstCommand, addToTeamFirstCommand);

        // same values -> returns true
        assertEquals(addToTeamFirstCommand, addToTeamSecondCommand);

        // different types -> returns false
        assertEquals(false, addToTeamFirstCommand.equals(1));

        // null -> returns false
        assertEquals(false, addToTeamFirstCommand.equals(null));
    }
}
