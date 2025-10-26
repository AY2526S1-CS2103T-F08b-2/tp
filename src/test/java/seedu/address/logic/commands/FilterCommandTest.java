package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.HackathonContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        HackathonContainsKeywordsPredicate firstPredicate =
                new HackathonContainsKeywordsPredicate(Arrays.asList("NUSHacks"));
        HackathonContainsKeywordsPredicate secondPredicate =
                new HackathonContainsKeywordsPredicate(Arrays.asList("NUSHacks"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // same predicate type -> returns true
        assertTrue(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_hackathonFilter_personsFound() {
        String expectedMessage = String.format(seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        HackathonContainsKeywordsPredicate predicate =
                new HackathonContainsKeywordsPredicate(Arrays.asList("NUSHacks"));
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        HackathonContainsKeywordsPredicate predicate =
                new HackathonContainsKeywordsPredicate(Arrays.asList("NUSHacks"));
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
