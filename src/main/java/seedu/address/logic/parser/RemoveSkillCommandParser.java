package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveSkillCommand object
 */
public class RemoveSkillCommandParser implements Parser<RemoveSkillCommand> {
    @Override
    public RemoveSkillCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_SKILL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_SKILL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
        }

        // Parse multiple skill names and convert to lowercase for case-insensitive comparison
        List<String> skillValues = argMultimap.getAllValues(PREFIX_SKILL);
        Set<String> skillNameSet = new HashSet<>();
        for (String skill : skillValues) {
            String trimmedSkill = skill.trim().toLowerCase();
            if (trimmedSkill.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveSkillCommand.MESSAGE_USAGE));
            }
            skillNameSet.add(trimmedSkill);
        }

        return new RemoveSkillCommand(index, skillNameSet);
    }
}
