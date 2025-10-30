package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.skill.Skill;

/**
 * Removes one or more skills from a person identified by index.
 */
public class RemoveSkillCommand extends Command {
    public static final String COMMAND_WORD = "removeskill";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified skills from the person"
            + " identified by the index number in the displayed person list.\n"
            + "Command is case-insensitive (removeskill, removeSkill, REMOVESKILL all work).\n"
            + "Parameters:" + PREFIX_PERSON + "INDEX (must be a positive integer) " + PREFIX_SKILL
            + "SKILL [" + PREFIX_SKILL + "MORE_SKILLS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 " + PREFIX_SKILL + "java "
            + PREFIX_SKILL + "python " + PREFIX_SKILL + "docker";
    public static final String MESSAGE_REMOVE_SKILL_SUCCESS = "Removed skills from Person: %1$s";
    public static final String MESSAGE_SKILL_NOT_FOUND = "Skill '%1$s' not found for Person: %2$s";
    private final Index targetIndex;
    private final Set<String> skillNames;

    /**
     * Constructs a RemoveSkillCommand to remove skills from a person.
     *
     * @param targetIndex Index of the person in the displayed list (1-based).
     * @param skillNames Set of skill names to remove from the person.
     */
    public RemoveSkillCommand(Index targetIndex, Set<String> skillNames) {
        requireNonNull(targetIndex);
        requireNonNull(skillNames);
        this.targetIndex = targetIndex;
        this.skillNames = skillNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = personToEdit;

        // Validate and remove each skill
        for (String skillName : skillNames) {
            // Validate skill name format
            if (!Skill.isValidSkillName(skillName)) {
                throw new CommandException(Skill.MESSAGE_CONSTRAINTS);
            }

            Skill skillToRemove = new Skill(skillName);
            if (!editedPerson.getSkills().contains(skillToRemove)) {
                throw new CommandException(String.format(MESSAGE_SKILL_NOT_FOUND, skillName,
                        Messages.format(personToEdit)));
            }
            editedPerson = editedPerson.removeSkill(skillToRemove);
        }

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_SKILL_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof RemoveSkillCommand)) {
            return false;
        }
        RemoveSkillCommand otherCommand = (RemoveSkillCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && skillNames.equals(otherCommand.skillNames);
    }
}
