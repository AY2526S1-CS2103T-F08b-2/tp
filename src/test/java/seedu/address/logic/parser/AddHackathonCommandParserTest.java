package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddHackathonCommand;
import seedu.address.model.hackathon.HackathonName;

/**
 * Contains tests for AddHackathonCommandParser.
 */
public class AddHackathonCommandParserTest {

    private AddHackathonCommandParser parser = new AddHackathonCommandParser();

    @Test
    public void parse_validArgsSingleHackathon_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, "1 " + PREFIX_HACKATHON_NAME + "NUSHack", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleHackathons_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));
        expectedHackathons.add(new HackathonName("iNTUition"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, "1 " + PREFIX_HACKATHON_NAME + "NUSHack "
                + PREFIX_HACKATHON_NAME + "iNTUition", expectedCommand);
    }

    @Test
    public void parse_validArgsWithSpaces_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUS Hack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, "1 " + PREFIX_HACKATHON_NAME + "NUS Hack", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-1 " + PREFIX_HACKATHON_NAME + "NUSHack", MESSAGE_INVALID_COMMAND_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_HACKATHON_NAME + "NUSHack", MESSAGE_INVALID_COMMAND_FORMAT);

        // non-numeric index
        assertParseFailure(parser, "a " + PREFIX_HACKATHON_NAME + "NUSHack", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, PREFIX_HACKATHON_NAME + "NUSHack", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_missingHackathon_throwsParseException() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_COMMAND_FORMAT);
        assertParseFailure(parser, "1 ", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidHackathonName_throwsParseException() {
        // empty hackathon name
        assertParseFailure(parser, "1 " + PREFIX_HACKATHON_NAME + "", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_duplicateHackathons_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_HACKATHON_NAME + "NUSHack "
                + PREFIX_HACKATHON_NAME + "NUSHack",
                "Duplicate hackathon detected: NUSHack. Each hackathon can only be added once.");
    }

    @Test
    public void parse_duplicateHackathonsCaseInsensitive_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_HACKATHON_NAME + "NUSHack "
                + PREFIX_HACKATHON_NAME + "nushack",
                "Duplicate hackathon detected: nushack. Each hackathon can only be added once.");
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "   ", MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_invalidPrefixBeforeIndex_throwsParseException() {
        assertParseFailure(parser, PREFIX_HACKATHON_NAME + "1 " + PREFIX_HACKATHON_NAME + "NUSHack",
                MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_multipleValidHackathonsWithSpecialCharacters_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("Hack@MIT"));
        expectedHackathons.add(new HackathonName("HackNYU2024"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, "1 " + PREFIX_HACKATHON_NAME + "Hack@MIT "
                + PREFIX_HACKATHON_NAME + "HackNYU2024", expectedCommand);
    }

    @Test
    public void parse_hackathonWithLeadingTrailingSpaces_trimmedCorrectly() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        // Parser should trim leading and trailing spaces
        assertParseSuccess(parser, "1 " + PREFIX_HACKATHON_NAME + "  NUSHack  ", expectedCommand);
    }
}

