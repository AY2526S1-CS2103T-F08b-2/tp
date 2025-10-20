package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

public class AddPersonToTeamCommandTest {

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

        AddPersonToTeamCommand command = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);

        // Execute the command and check the result message
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(AddPersonToTeamCommand.MESSAGE_SUCCESS,
                Messages.format(personToAdd), Messages.format(team));

        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify that the team now contains the person
        Team updatedTeam = model.getFilteredTeamList().stream()
                .filter(t -> t.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow();

        assertEquals(true, updatedTeam.hasMember(personToAdd));
    }

    @Test
    public void execute_personAlreadyInSameTeam_throwsCommandException() throws Exception {
        // Create a team with a person already in it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Person> initialMembers = new HashSet<>();
        initialMembers.add(personToAdd);
        Team team = new Team(teamName, hackathonName, initialMembers);
        model.addTeam(team);

        AddPersonToTeamCommand command = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model,
                String.format(AddPersonToTeamCommand.MESSAGE_PERSON_ALREADY_IN_THIS_TEAM,
                        Messages.format(personToAdd), teamName));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        TeamName teamName = new TeamName("Test Team");
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPersonToTeamCommand command = new AddPersonToTeamCommand(teamName, outOfBoundIndex);

        assertCommandFailure(command, model, AddPersonToTeamCommand.MESSAGE_INVALID_PERSON_INDEX);
    }

    @Test
    public void execute_teamNotFound_throwsCommandException() {
        TeamName nonExistentTeamName = new TeamName("Non Existent Team");
        AddPersonToTeamCommand command = new AddPersonToTeamCommand(nonExistentTeamName, INDEX_FIRST_PERSON);

        assertCommandFailure(command, model,
                String.format(AddPersonToTeamCommand.MESSAGE_TEAM_NOT_FOUND, nonExistentTeamName));
    }

    @Test
    public void equals() {
        TeamName teamName = new TeamName("Team A");
        AddPersonToTeamCommand addPersonToTeamFirstCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);
        AddPersonToTeamCommand addPersonToTeamSecondCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);

        // same object -> returns true
        assertEquals(addPersonToTeamFirstCommand, addPersonToTeamFirstCommand);

        // same values -> returns true
        assertEquals(addPersonToTeamFirstCommand, addPersonToTeamSecondCommand);

        // different types -> returns false
        assertEquals(false, addPersonToTeamFirstCommand.equals(1));

        // null -> returns false
        assertEquals(false, addPersonToTeamFirstCommand.equals(null));
    }
}
