package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.skill.Skill;

public class RemoveSkillCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSkill_success() {
        // Get the first person and a skill name they have
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Skill skill = person.getSkills().iterator().next();
        String skillName = skill.skillName;

        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, skillName);

        Person expectedPerson = person.removeSkill(skill);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(person, expectedPerson);

        String expectedMessage = String.format(RemoveSkillCommand.MESSAGE_REMOVE_SKILL_SUCCESS,
                skillName,
                seedu.address.logic.Messages.format(expectedPerson));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_skillNotFound_throwsCommandException() {
        String nonExistentSkill = "nonexistentskill";
        RemoveSkillCommand command = new RemoveSkillCommand(INDEX_FIRST_PERSON, nonExistentSkill);

        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(RemoveSkillCommand.MESSAGE_SKILL_NOT_FOUND,
                nonExistentSkill,
                seedu.address.logic.Messages.format(person));

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        RemoveSkillCommand command = new RemoveSkillCommand(
            seedu.address.commons.core.index.Index.fromOneBased(model.getFilteredPersonList().size() + 1), "java");
        assertCommandFailure(command, model, seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
