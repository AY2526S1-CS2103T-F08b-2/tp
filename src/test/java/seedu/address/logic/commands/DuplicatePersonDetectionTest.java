package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.team.TeamName;
import seedu.address.model.util.SampleDataUtil;

/**
 * Test to verify duplicate person detection works with sample data
 */
public class DuplicatePersonDetectionTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Use the sample data with empty teams
        model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs());
    }

    @Test
    public void addPersonToTeam_addSamePersonTwice_throwsCommandException() throws CommandException {
        // First, add Alex (index 1) to Tech Innovators team - this should succeed
        TeamName techInnovatorsTeamName = new TeamName("Tech Innovators");
        AddPersonToTeamCommand command1 = new AddPersonToTeamCommand(techInnovatorsTeamName, INDEX_FIRST_PERSON);
        command1.execute(model); // This should work fine

        // Now try to add Alex to Tech Innovators again - this should throw an exception
        AddPersonToTeamCommand command2 = new AddPersonToTeamCommand(techInnovatorsTeamName, INDEX_FIRST_PERSON);
        assertThrows(CommandException.class, () -> command2.execute(model));
    }
}
