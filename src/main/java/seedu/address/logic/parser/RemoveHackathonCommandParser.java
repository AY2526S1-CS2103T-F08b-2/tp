package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveHackathonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hackathon.HackathonName;

/**
 * Parses input arguments and creates a new RemoveHackathonCommand object
 */
public class RemoveHackathonCommandParser implements Parser<RemoveHackathonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveHackathonCommand
     * and returns a RemoveHackathonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemoveHackathonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_HACKATHON);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveHackathonCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_HACKATHON)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveHackathonCommand.MESSAGE_USAGE));
        }

        Set<HackathonName> hackathonNames = ParserUtil.parseHackathonNames(
                argMultimap.getAllValues(PREFIX_HACKATHON));

        return new RemoveHackathonCommand(index, hackathonNames);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

