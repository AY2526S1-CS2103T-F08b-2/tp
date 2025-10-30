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

import seedu.address.logic.commands.AddSkillCommand;
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;

/**
 * Contains tests for AddSkillCommandParser.
 */
public class AddSkillCommandParserTest {

    private final AddSkillCommandParser parser = new AddSkillCommandParser();

    @Test
    public void parse_validArgsSingleSkill_returnsAddSkillCommand() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java"));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java", expectedCommand);
    }

    @Test
    public void parse_validArgsSingleSkillUpperCase_returnsAddSkillCommand() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java")); // Should be converted to lowercase

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "Java", expectedCommand);
    }

    @Test
    public void parse_validArgsSingleSkillWithLevel_returnsAddSkillCommand() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java", ExperienceLevel.ADVANCED));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java:Advanced", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleSkills_returnsAddSkillCommand() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java"));
        expectedSkills.add(new Skill("python"));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java " + PREFIX_SKILL + "python", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleSkillsWithLevels_returnsAddSkillCommand() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java", ExperienceLevel.ADVANCED));
        expectedSkills.add(new Skill("python", ExperienceLevel.INTERMEDIATE));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "java:Advanced "
                + PREFIX_SKILL + "python:Intermediate", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // negative index
        assertParseFailure(parser, " p/-1 " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, " p/0 " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));

        // non-numeric index
        assertParseFailure(parser, " p/a " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSkill_throwsParseException() {
        assertParseFailure(parser, " p/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSkillName_throwsParseException() {
        // skill with invalid characters
        assertParseFailure(parser, " p/1 " + PREFIX_SKILL + "java*",
                Skill.MESSAGE_CONSTRAINTS);

        // skill starting with #
        assertParseFailure(parser, " p/1 " + PREFIX_SKILL + "#java",
                Skill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateSkills_throwsParseException() {
        assertParseFailure(parser, " p/1 " + PREFIX_SKILL + "java " + PREFIX_SKILL + "java",
                "Duplicate skill detected: java. Each skill can only be added once.");
    }

    @Test
    public void parse_duplicateSkillsCaseInsensitive_throwsParseException() {
        assertParseFailure(parser, " p/1 " + PREFIX_SKILL + "java " + PREFIX_SKILL + "Java",
                "Duplicate skill detected: java. Each skill can only be added once.");
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefixBeforeIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SKILL + "java",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_skillWithLeadingTrailingSpaces_trimmedCorrectly() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("java"));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        // Parser should trim leading and trailing spaces
        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "  java  ", expectedCommand);
    }

    @Test
    public void parse_skillWithSpecialCharacters_success() {
        Set<Skill> expectedSkills = new HashSet<>();
        expectedSkills.add(new Skill("c++"));
        expectedSkills.add(new Skill("c#"));

        AddSkillCommand expectedCommand = new AddSkillCommand(INDEX_FIRST_PERSON, expectedSkills);

        assertParseSuccess(parser, " p/1 " + PREFIX_SKILL + "c++ " + PREFIX_SKILL + "c#", expectedCommand);
    }
}

