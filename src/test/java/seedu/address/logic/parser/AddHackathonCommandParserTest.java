package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON;
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

    private final AddHackathonCommandParser parser = new AddHackathonCommandParser();

    @Test
    public void parse_validArgsSingleHackathon_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, " p/1 " + PREFIX_HACKATHON + "NUSHack", expectedCommand);
    }

    @Test
    public void parse_validArgsMultipleHackathons_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));
        expectedHackathons.add(new HackathonName("iNTUition"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, " p/1 " + PREFIX_HACKATHON + "NUSHack "
                + PREFIX_HACKATHON + "iNTUition", expectedCommand);
    }

    @Test
    public void parse_validArgsWithSpaces_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUS Hack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, " p/1 " + PREFIX_HACKATHON + "NUS Hack", expectedCommand);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // negative index
        assertParseFailure(parser, " p/-1 " + PREFIX_HACKATHON + "NUSHack",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, " p/0 " + PREFIX_HACKATHON + "NUSHack",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));

        // non-numeric index
        assertParseFailure(parser, " p/a " + PREFIX_HACKATHON + "NUSHack",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HACKATHON + "NUSHack",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingHackathon_throwsParseException() {
        assertParseFailure(parser, " p/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidHackathonName_throwsParseException() {
        // empty hackathon name - throws HackathonName validation error
        assertParseFailure(parser, " p/1 " + PREFIX_HACKATHON,
                HackathonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateHackathons_throwsParseException() {
        assertParseFailure(parser, " p/1 " + PREFIX_HACKATHON + "NUSHack "
                + PREFIX_HACKATHON + "NUSHack",
                "Duplicate hackathon detected: NUSHack. Each hackathon can only be added once.");
    }

    @Test
    public void parse_duplicateHackathonsCaseInsensitive_throwsParseException() {
        assertParseFailure(parser, " p/1 " + PREFIX_HACKATHON + "NUSHack "
                + PREFIX_HACKATHON + "nushack",
                "Duplicate hackathon detected: nushack. Each hackathon can only be added once.");
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefixBeforeIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_HACKATHON + "NUSHack",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleValidHackathons_returnsAddHackathonCommand() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("HackMIT"));
        expectedHackathons.add(new HackathonName("HackNYU2024"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        assertParseSuccess(parser, " p/1 " + PREFIX_HACKATHON + "HackMIT "
                + PREFIX_HACKATHON + "HackNYU2024", expectedCommand);
    }

    @Test
    public void parse_hackathonWithLeadingTrailingSpaces_trimmedCorrectly() {
        Set<HackathonName> expectedHackathons = new HashSet<>();
        expectedHackathons.add(new HackathonName("NUSHack"));

        AddHackathonCommand expectedCommand = new AddHackathonCommand(INDEX_FIRST_PERSON, expectedHackathons);

        // Parser should trim leading and trailing spaces
        assertParseSuccess(parser, " p/1 " + PREFIX_HACKATHON + "  NUSHack  ", expectedCommand);
    }
}

