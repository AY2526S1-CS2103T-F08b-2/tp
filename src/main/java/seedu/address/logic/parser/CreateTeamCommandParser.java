package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Parses input arguments and creates a new CreateTeamCommand object
 */
public class CreateTeamCommandParser implements Parser<CreateTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateTeamCommand
     * and returns a CreateTeamCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TEAM_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateTeamCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM_NAME);
        TeamName teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAM_NAME).get());

        Team team = new Team(teamName);

        return new CreateTeamCommand(team);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
