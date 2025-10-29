package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveSkillCommand object
 */
public class RemoveSkillCommandParser implements Parser<RemoveSkillCommand> {
    @Override
    public RemoveSkillCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_SKILL);

        if (!argMultimap.getValue(PREFIX_PERSON).isPresent()
                || !argMultimap.getValue(PREFIX_SKILL).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON, PREFIX_SKILL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveSkillCommand.MESSAGE_USAGE), pe);
        }

        String skillName = argMultimap.getValue(PREFIX_SKILL).get().trim();
        return new RemoveSkillCommand(index, skillName);
    }
}
