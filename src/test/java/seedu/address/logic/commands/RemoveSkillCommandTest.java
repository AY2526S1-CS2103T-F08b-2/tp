package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
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
import seedu.address.model.skill.Skill;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveSkillCommand.
 */
public class RemoveSkillCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSingleSkill_success() {
        // Get the first person and a skill name they have
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Skill skill = person.getSkills().iterator().next();
        String skillName = skill.skillName;

        Set<String> skillNames = new HashSet<>();
        skillNames.add(skillName);
        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillNames);

        Person expectedPerson = person.removeSkill(skill);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(person, expectedPerson);

        String expectedMessage = String.format(RemoveSkillCommand.MESSAGE_REMOVE_SKILL_SUCCESS,
                expectedPerson.getName());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validMultipleSkills_success() {
        // Create a person with multiple skills
        Person personWithSkills = new PersonBuilder()
                .withName("Test Person")
                .withEmail("test@example.com")
                .withTelegram("testperson")
                .withGitHub("testperson")
                .withSkills("java", "python", "docker")
                .build();

        model.addPerson(personWithSkills);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        // Remove multiple skills
        Set<String> skillNames = new HashSet<>();
        skillNames.add("java");
        skillNames.add("python");
        RemoveSkillCommand command = new RemoveSkillCommand(indexOfPerson, skillNames);

        // Expected person should only have docker
        Person expectedPerson = new PersonBuilder(personWithSkills)
                .withSkills("docker")
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithSkills, expectedPerson);

        String expectedMessage = String.format(RemoveSkillCommand.MESSAGE_REMOVE_SKILL_SUCCESS,
                expectedPerson.getName());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeAllSkills_success() {
        // Create a person with skills
        Person personWithSkills = new PersonBuilder()
                .withName("Another Person")
                .withEmail("another@example.com")
                .withTelegram("anotherperson")
                .withGitHub("anotherperson")
                .withSkills("java", "python")
                .build();

        model.addPerson(personWithSkills);
        Index indexOfPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        // Remove all skills
        Set<String> skillNames = new HashSet<>();
        skillNames.add("java");
        skillNames.add("python");
        RemoveSkillCommand command = new RemoveSkillCommand(indexOfPerson, skillNames);

        // Expected person should have no skills
        Person expectedPerson = new PersonBuilder(personWithSkills)
                .withSkills()
                .build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personWithSkills, expectedPerson);

        String expectedMessage = String.format(RemoveSkillCommand.MESSAGE_REMOVE_SKILL_SUCCESS,
                expectedPerson.getName());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_skillNotFound_throwsCommandException() {
        Set<String> skillNames = new HashSet<>();
        skillNames.add("nonexistentskill");
        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillNames);

        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = RemoveSkillCommand.MESSAGE_SKILL_NOT_FOUND;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_oneSkillNotFound_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Skill existingSkill = person.getSkills().iterator().next();

        Set<String> skillNames = new HashSet<>();
        skillNames.add(existingSkill.skillName);
        skillNames.add("nonexistentskill");
        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillNames);

        String expectedMessage = RemoveSkillCommand.MESSAGE_SKILL_NOT_FOUND;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Set<String> skillNames = new HashSet<>();
        skillNames.add("java");
        RemoveSkillCommand command = new RemoveSkillCommand(
                Index.fromOneBased(model.getFilteredPersonList().size() + 1), skillNames);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSkillName_throwsCommandException() {
        Set<String> skillNames = new HashSet<>();
        skillNames.add("Java"); // Invalid: uppercase
        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillNames);

        assertCommandFailure(command, model, Skill.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void equals() {
        Set<String> skillSet1 = new HashSet<>();
        skillSet1.add("java");
        Set<String> skillSet2 = new HashSet<>();
        skillSet2.add("python");

        RemoveSkillCommand removeSkillFirstCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillSet1);
        RemoveSkillCommand removeSkillSecondCommand = new RemoveSkillCommand(Index.fromOneBased(2), skillSet1);
        RemoveSkillCommand removeSkillDifferentSkillCommand = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillSet2);

        // same object -> returns true
        assertTrue(removeSkillFirstCommand.equals(removeSkillFirstCommand));

        // same values -> returns true
        Set<String> skillSet1Copy = new HashSet<>();
        skillSet1Copy.add("java");
        RemoveSkillCommand removeSkillFirstCommandCopy = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillSet1Copy);
        assertTrue(removeSkillFirstCommand.equals(removeSkillFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeSkillFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeSkillFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(removeSkillFirstCommand.equals(removeSkillSecondCommand));

        // different skills -> returns false
        assertFalse(removeSkillFirstCommand.equals(removeSkillDifferentSkillCommand));
    }
}
