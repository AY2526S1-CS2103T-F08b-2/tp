package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPersonToTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamName;

/**
 * Parses input arguments and creates a new AddPersonToTeamCommand object
 */
public class AddPersonToTeamCommandParser implements Parser<AddPersonToTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonToTeamCommand
     * and returns an AddPersonToTeamCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonToTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM_NAME, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM_NAME, PREFIX_PERSON);

        TeamName teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM_NAME).get());
        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());

        return new AddPersonToTeamCommand(teamName, personIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
