package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HACKATHON_FILTER_DESC_TECH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_PYTHON;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HACKATHON_NAME_TECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_AMY + EMAIL_DESC_AMY
                        + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY + SKILL_DESC_PYTHON,
                new AddCommand(AMY));

        // multiple skills - all accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB
                        + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_PYTHON,
                new AddCommand(BOB));
    }

    @Test
    public void parse_repeatedNonSkillValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_PYTHON;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple telegrams
        assertParseFailure(parser, TELEGRAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // multiple github
        assertParseFailure(parser, GITHUB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GITHUB));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL,
                        PREFIX_TELEGRAM, PREFIX_GITHUB));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Person expectedPerson = new PersonBuilder(AMY).withSkills().build();
        assertParseSuccess(parser, NAME_DESC_AMY + EMAIL_DESC_AMY
                + TELEGRAM_DESC_AMY + GITHUB_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidTelegram_failure() {
        assertParseFailure(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY
                        + INVALID_TELEGRAM_DESC + GITHUB_DESC_AMY + SKILL_DESC_PYTHON,
                Telegram.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidGitHub_failure() {
        assertParseFailure(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY
                        + TELEGRAM_DESC_AMY + INVALID_GITHUB_DESC + SKILL_DESC_PYTHON,
                GitHub.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_PYTHON,
                Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_EMAIL_DESC
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_PYTHON,
                Email.MESSAGE_CONSTRAINTS);

        // invalid skill
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + INVALID_SKILL_DESC + VALID_SKILL_PYTHON,
                Skill.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_PYTHON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateSkills_failure() {
        // duplicate skills with same name
        String duplicateSkillCommand = NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + SKILL_DESC_JAVA + SKILL_DESC_JAVA;
        assertParseFailure(parser, duplicateSkillCommand,
                "Duplicate skill detected: java. Each skill can only be added once.");

        // duplicate skills with different experience levels
        String duplicateSkillDifferentLevels = NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + " s/java:beginner s/java:intermediate";
        assertParseFailure(parser, duplicateSkillDifferentLevels,
                "Duplicate skill detected: java. Each skill can only be added once.");
    }

    @Test
    public void parse_duplicateHackathons_failure() {
        // duplicate hackathon names
        String duplicateHackathonCommand = NAME_DESC_BOB + EMAIL_DESC_BOB
                + TELEGRAM_DESC_BOB + GITHUB_DESC_BOB + HACKATHON_FILTER_DESC_TECH + HACKATHON_FILTER_DESC_TECH;
        assertParseFailure(parser, duplicateHackathonCommand,
                "Duplicate hackathon detected: " + VALID_HACKATHON_NAME_TECH
                + ". Each hackathon can only be added once.");
    }
}
