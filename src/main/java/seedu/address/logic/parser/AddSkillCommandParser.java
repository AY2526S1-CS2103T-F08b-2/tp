package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+", 2);

        if (parts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(parts[0]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE), pe);
        }

        // Parse the skills from the remaining arguments
        String[] skillStrings = parts[1].split("\\s+");
        Set<Skill> skills;
        try {
            skills = ParserUtil.parseSkillsFromArray(skillStrings);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }

        if (skills.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSkillCommand.MESSAGE_USAGE));
        }

        return new AddSkillCommand(index, skills);
    }
}
