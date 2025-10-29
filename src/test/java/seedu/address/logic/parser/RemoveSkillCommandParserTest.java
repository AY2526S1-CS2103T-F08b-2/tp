package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveSkillCommand;

/**
 * Contains tests for RemoveSkillCommandParser.
 */
public class RemoveSkillCommandParserTest {

    private final RemoveSkillCommandParser parser = new RemoveSkillCommandParser();

    @Test
    public void parse_validArgsSingleSkill_returnsRemoveSkillCommand() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("java");

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java", expectedCommand);
    }

    @Test
    public void parse_validArgsSingleSkillUpperCase_returnsRemoveSkillCommand() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("java"); // Should be converted to lowercase

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "Java", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleSkills_returnsRemoveSkillCommand() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("java");
        expectedSkillNames.add("python");

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java " + PREFIX_SKILL + "python", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleSkillsMixedCase_returnsRemoveSkillCommand() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("java");
        expectedSkillNames.add("python");

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "Java " + PREFIX_SKILL + "PYTHON", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // negative index
        assertParseFailure(parser, " p/-1 " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, " p/0 " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));

        // non-numeric index
        assertParseFailure(parser, " p/a " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSkill_throwsParseException() {
        assertParseFailure(parser, " p/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptySkillName_throwsParseException() {
        assertParseFailure(parser, " p/1 " + PREFIX_SKILL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefixBeforeIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_skillWithLeadingTrailingSpaces_trimmedCorrectly() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("java");

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        // Parser should trim leading and trailing spaces
        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "  java  ", expectedCommand);
    }

    @Test
    public void parse_skillWithSpecialCharacters_success() {
        Set<String> expectedSkillNames = new HashSet<>();
        expectedSkillNames.add("c++");
        expectedSkillNames.add("c#");

        RemoveSkillCommand expectedCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, expectedSkillNames);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "c++ " + PREFIX_SKILL + "c#", expectedCommand);
    }
}

