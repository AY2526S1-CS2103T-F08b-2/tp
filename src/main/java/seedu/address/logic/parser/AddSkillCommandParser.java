package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSkillCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new AddSkillCommand object
 */
public class AddSkillCommandParser implements Parser<AddSkillCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSkillCommand
     * and returns an AddSkillCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddSkillCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_SKILL);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PERSON).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_SKILL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        }

        Set<Skill> skills = ParserUtil.parseSkills(argMultimap.getAllValues(PREFIX_SKILL));

        if (skills.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        }

        return new AddSkillCommand(index, skills);
    }
}
