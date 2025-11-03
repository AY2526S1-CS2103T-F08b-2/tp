package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddSkillCommand.
 */
public class AddSkillCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addSkillUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Skill> skillsToAdd = new HashSet<>();
        skillsToAdd.add(new Skill("docker", ExperienceLevel.INTERMEDIATE));

        AddSkillCommand addSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillsToAdd);

        Set<Skill> updatedSkills = new HashSet<>(firstPerson.getSkills());
        updatedSkills.addAll(skillsToAdd);

        Person editedPerson = new PersonBuilder(firstPerson).withSkillSet(updatedSkills).build();
        String expectedMessage = String.format(AddSkillCommand.MESSAGE_SKILL_EDITED, editedPerson.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addSkillCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSkillWithHigherLevel_upgradesSkill() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Get an existing skill from the person (assume it has Beginner level)
        Skill existingSkill = firstPerson.getSkills().iterator().next();

        // Try to add the same skill with a higher level (Advanced)
        Set<Skill> skillsToAdd = new HashSet<>();
        Skill upgradedSkill = new Skill(existingSkill.skillName, ExperienceLevel.ADVANCED);
        skillsToAdd.add(upgradedSkill);

        AddSkillCommand addSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillsToAdd);

        // The skill should be upgraded to Advanced level
        Set<Skill> updatedSkills = new HashSet<>(firstPerson.getSkills());
        updatedSkills.remove(existingSkill); // Remove old skill
        updatedSkills.add(upgradedSkill); // Add upgraded skill

        Person editedPerson = new PersonBuilder(firstPerson).withSkillSet(updatedSkills).build();
        String expectedMessage = String.format(AddSkillCommand.MESSAGE_SKILL_EDITED, editedPerson.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addSkillCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSkillWithSameOrLowerLevel_keepsExistingSkill() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Get an existing skill from the person
        Skill existingSkill = firstPerson.getSkills().iterator().next();

        // Try to add the same skill with the same level
        Set<Skill> skillsToAdd = new HashSet<>();
        skillsToAdd.add(existingSkill);

        AddSkillCommand addSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillsToAdd);

        // Since no change occurs (same skill and level), command should fail with a concise message
        assertCommandFailure(addSkillCommand, model, AddSkillCommand.MESSAGE_SKILL_ALREADY_PRESENT);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Set<Skill> skillsToAdd = new HashSet<>();
        skillsToAdd.add(new Skill("python"));

        AddSkillCommand addSkillCommand = new AddSkillCommand(outOfBoundIndex, skillsToAdd);

        assertCommandFailure(addSkillCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addMultipleSkills_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Skill> skillsToAdd = new HashSet<>();
        skillsToAdd.add(new Skill("docker", ExperienceLevel.INTERMEDIATE));
        skillsToAdd.add(new Skill("kubernetes", ExperienceLevel.BEGINNER));

        AddSkillCommand addSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillsToAdd);

        Set<Skill> updatedSkills = new HashSet<>(firstPerson.getSkills());
        updatedSkills.addAll(skillsToAdd);

        Person editedPerson = new PersonBuilder(firstPerson).withSkillSet(updatedSkills).build();
        String expectedMessage = String.format(AddSkillCommand.MESSAGE_SKILL_EDITED, editedPerson.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addSkillCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<Skill> skillSet1 = new HashSet<>();
        skillSet1.add(new Skill("java"));
        Set<Skill> skillSet2 = new HashSet<>();
        skillSet2.add(new Skill("python"));

        AddSkillCommand addSkillFirstCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillSet1);
        AddSkillCommand addSkillSecondCommand = new AddSkillCommand(INDEX_SECOND_PERSON, skillSet1);
        AddSkillCommand addSkillDifferentSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillSet2);

        // same object -> returns true
        assertTrue(addSkillFirstCommand.equals(addSkillFirstCommand));

        // same values -> returns true
        AddSkillCommand addSkillFirstCommandCopy = new AddSkillCommand(INDEX_FIRST_PERSON, skillSet1);
        assertTrue(addSkillFirstCommand.equals(addSkillFirstCommandCopy));

        // different types -> returns false
        assertFalse(addSkillFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addSkillFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(addSkillFirstCommand.equals(addSkillSecondCommand));

        // different skill -> returns false
        assertFalse(addSkillFirstCommand.equals(addSkillDifferentSkillCommand));
    }

    @Test
    public void toStringMethod() {
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(new Skill("java"));
        AddSkillCommand addSkillCommand = new AddSkillCommand(INDEX_FIRST_PERSON, skillSet);
        String expected = "AddSkillCommand{targetIndex=" + INDEX_FIRST_PERSON + ", skillsToAdd=" + skillSet + "}";
        assertEquals(expected, addSkillCommand.toString());
    }
}
