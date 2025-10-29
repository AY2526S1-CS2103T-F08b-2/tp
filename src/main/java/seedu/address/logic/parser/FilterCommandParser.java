package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.HackathonContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HACKATHON);

        boolean hasHackathonPrefix = argMultimap.getValue(PREFIX_HACKATHON).isPresent();

        if (!hasHackathonPrefix) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Predicate<Person> predicate = person -> true;


        if (hasHackathonPrefix) {
            String hackathonArgs = argMultimap.getValue(PREFIX_HACKATHON).get().trim();
            if (hackathonArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            String[] hackathonKeywords = hackathonArgs.split("\\s+");
            predicate = predicate.and(new HackathonContainsKeywordsPredicate(Arrays.asList(hackathonKeywords)));
        }

        return new FilterCommand(predicate);
    }
}
