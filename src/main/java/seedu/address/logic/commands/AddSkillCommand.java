package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.skill.Skill;

/**
 * Adds skills to a person identified by index.
 */
public class AddSkillCommand extends Command {
    public static final String COMMAND_WORD = "addskill";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds skills to the person identified by the index number in the displayed person list.\n"
            + "Command is case-insensitive.\n"
            + "Parameters: p/INDEX (must be a positive integer) sk/SKILL[:LEVEL]...\n"
            + "LEVEL can be: Beginner, Intermediate, or Advanced (default: Beginner)\n"
            + "Example: " + COMMAND_WORD + " p/1 sk/java:Advanced sk/python:Intermediate sk/docker";

    public static final String MESSAGE_SKILL_ALREADY_PRESENT = "Skill(s) already present";
    public static final String MESSAGE_SKILL_EDITED = "Edited skill(s) for %1$s";

    private final Index targetIndex;
    private final Set<Skill> skillsToAdd;

    /**
     * Constructs an AddSkillCommand to add skills to a person.
     *
     * @param targetIndex Index of the person in the displayed list (1-based).
     * @param skillsToAdd Set of skills to add to the person.
     */
    public AddSkillCommand(Index targetIndex, Set<Skill> skillsToAdd) {
        requireNonNull(targetIndex);
        requireNonNull(skillsToAdd);
        this.targetIndex = targetIndex;
        this.skillsToAdd = skillsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Set<Skill> existingSkills = personToEdit.getSkills();

        // Create updated skill set
        Set<Skill> updatedSkills = new HashSet<>(existingSkills);
        boolean hasUpgrade = false;
        boolean hasDowngrade = false;

        // For each skill to add, check if it exists and compare levels
        for (Skill skillToAdd : skillsToAdd) {
            Skill existingSkill = null;

            // Find if the skill already exists (by skill name)
            for (Skill skill : existingSkills) {
                if (skill.skillName.equals(skillToAdd.skillName)) {
                    existingSkill = skill;
                    break;
                }
            }

            if (existingSkill != null) {
                // Compare experience levels and keep the higher one
                int cmp = skillToAdd.getExperienceLevel().compareTo(existingSkill.getExperienceLevel());
                if (cmp > 0) {
                    // New skill has higher level - remove old and add new (upgrade)
                    updatedSkills.remove(existingSkill);
                    updatedSkills.add(skillToAdd);
                    hasUpgrade = true;
                } else if (cmp < 0) {
                    // New skill has lower level - remove old and add new (downgrade)
                    updatedSkills.remove(existingSkill);
                    updatedSkills.add(skillToAdd);
                    hasDowngrade = true;
                }
                // If new level is same or lower, keep existing skill (do nothing)
            } else {
                // Skill doesn't exist, add it
                updatedSkills.add(skillToAdd);
            }
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getEmail(),
                personToEdit.getTelegram(),
                personToEdit.getGitHub(),
                updatedSkills,
                personToEdit.getTeams(),
                personToEdit.getInterestedHackathons(),
                personToEdit.getParticipatingHackathons()
        );

        model.setPerson(personToEdit, editedPerson);

        // If nothing changed (no new skills added and no upgrades), inform user
        if (!hasUpgrade && !hasDowngrade && updatedSkills.equals(existingSkills)) {
            throw new CommandException(MESSAGE_SKILL_ALREADY_PRESENT);
        }

        return new CommandResult(String.format(MESSAGE_SKILL_EDITED, editedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddSkillCommand)) {
            return false;
        }
        AddSkillCommand otherCommand = (AddSkillCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && skillsToAdd.equals(otherCommand.skillsToAdd);
    }

    @Override
    public String toString() {
        return "AddSkillCommand{targetIndex=" + targetIndex + ", skillsToAdd=" + skillsToAdd + "}";
    }
}
