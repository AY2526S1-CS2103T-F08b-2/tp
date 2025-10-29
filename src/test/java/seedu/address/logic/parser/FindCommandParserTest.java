package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noKeywordPrefix_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyKeyword_throwsParseException() {
        assertParseFailure(parser, "k/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "k/   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " k/Alice k/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n k/Alice \n \t k/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_multiWordKeyword_returnsFindCommand() {
        // keyword with spaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("AI Hackathon 2024")));
        assertParseSuccess(parser, " k/AI Hackathon 2024", expectedFindCommand);
    }

    @Test
    public void parse_multipleKeywordsWithSpaces_returnsFindCommand() {
        // multiple keywords, some with spaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(
                        Arrays.asList("AI Hackathon 2024", "John", "Python Developer")));
        assertParseSuccess(parser, " k/AI Hackathon 2024 k/John k/Python Developer", expectedFindCommand);
    }

}
