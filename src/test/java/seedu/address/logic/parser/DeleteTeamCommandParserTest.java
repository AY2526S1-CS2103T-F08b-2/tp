package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteTeamCommand;
import seedu.address.model.team.TeamName;

/**
 * Tests for DeleteTeamCommandParser.
 */
public class DeleteTeamCommandParserTest {

    private DeleteTeamCommandParser parser = new DeleteTeamCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTeamCommand() {
        TeamName teamName = new TeamName("Team Alpha");
        assertParseSuccess(parser, " tn/Team Alpha", new DeleteTeamCommand(teamName));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty team name
        assertParseFailure(parser, " tn/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, " Team Alpha", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTeamCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        assertParseFailure(parser, " tn/Team Alpha tn/Team Beta",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TEAM_NAME));
    }
}

