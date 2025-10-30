package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddHackathonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hackathon.HackathonName;

/**
 * Parses input arguments and creates a new AddHackathonCommand object
 */
public class AddHackathonCommandParser implements Parser<AddHackathonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddHackathonCommand
     * and returns an AddHackathonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddHackathonCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_HACKATHON_FILTER);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE), pe);
        }

        // Check if hackathon names are provided
        if (argMultimap.getAllValues(PREFIX_HACKATHON_FILTER).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHackathonCommand.MESSAGE_USAGE));
        }

        Set<HackathonName> hackathonNames;
        try {
            hackathonNames = ParserUtil.parseHackathonNames(argMultimap.getAllValues(PREFIX_HACKATHON_FILTER));
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }

        return new AddHackathonCommand(index, hackathonNames);
    }
}

