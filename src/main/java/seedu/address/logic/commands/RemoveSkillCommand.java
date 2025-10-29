package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.skill.Skill;

/**
 * Removes a skill from a person identified by index.
 */
public class RemoveSkillCommand extends Command {
    public static final String COMMAND_WORD = "removeskill";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified skill from the person"
            + " identified by the index number in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) SKILL\n"
            + "Example: " + COMMAND_WORD + " 1 java";
    public static final String MESSAGE_REMOVE_SKILL_SUCCESS = "Removed skill '%1$s' from Person: %2$s";
    public static final String MESSAGE_SKILL_NOT_FOUND = "Skill '%1$s' not found for Person: %2$s";
    private final Index targetIndex;
    private final String skillName;

    /**
     * Constructs a RemoveSkillCommand to remove a skill from a person.
     *
     * @param targetIndex Index of the person in the displayed list (1-based).
     * @param skillName Name of the skill to remove from the person.
     */
    public RemoveSkillCommand(Index targetIndex, String skillName) {
        this.targetIndex = targetIndex;
        this.skillName = skillName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Validate skill name format
        if (!Skill.isValidSkillName(skillName)) {
            throw new CommandException(Skill.MESSAGE_CONSTRAINTS);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Skill skillToRemove = new Skill(skillName);
        if (!personToEdit.getSkills().contains(skillToRemove)) {
            throw new CommandException(String.format(MESSAGE_SKILL_NOT_FOUND, skillName,
                    Messages.format(personToEdit)));
        }
        Person editedPerson = personToEdit.removeSkill(skillToRemove);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_REMOVE_SKILL_SUCCESS, skillName, Messages.format(editedPerson)));
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
                && skillName.equals(otherCommand.skillName);
    }
}
