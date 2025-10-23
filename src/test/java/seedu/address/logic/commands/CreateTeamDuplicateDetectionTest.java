package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.team.TeamName;
import seedu.address.model.util.SampleDataUtil;

/**
 * Test to verify the fix for createTeam + addPersonToTeam duplicate detection
 */
public class CreateTeamDuplicateDetectionTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs());
    }

    @Test
    public void createTeamThenAddSamePerson_shouldFailImmediately() throws CommandException {
        // Create a team with person 1 in it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Index> personIndices = new HashSet<>();
        personIndices.add(INDEX_FIRST_PERSON);

        CreateTeamCommand createCommand = new CreateTeamCommand(teamName, hackathonName, personIndices);
        createCommand.execute(model); // This should succeed

        // Now try to add the same person (person 1) to the same team - this should fail IMMEDIATELY
        AddPersonToTeamCommand addCommand = new AddPersonToTeamCommand(teamName, INDEX_FIRST_PERSON);
        assertThrows(CommandException.class, () -> addCommand.execute(model));
    }

    @Test
    public void createTeamThenAddDifferentPerson_shouldSucceed() throws CommandException {
        // Create a team with person 1 in it
        TeamName teamName = new TeamName("Test Team");
        HackathonName hackathonName = new HackathonName("Test Hackathon");
        Set<Index> personIndices = new HashSet<>();
        personIndices.add(INDEX_FIRST_PERSON);

        CreateTeamCommand createCommand = new CreateTeamCommand(teamName, hackathonName, personIndices);
        createCommand.execute(model); // This should succeed

        // Now try to add a different person (person 2) to the same team - this should succeed
        AddPersonToTeamCommand addCommand = new AddPersonToTeamCommand(teamName, INDEX_SECOND_PERSON);
        addCommand.execute(model); // This should not throw an exception
    }
}
