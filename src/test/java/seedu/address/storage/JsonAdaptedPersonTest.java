package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Telegram;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TELEGRAM = "john@doe";
    private static final String INVALID_GITHUB = "john_doe_";
    private static final String INVALID_SKILL = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_TELEGRAM = BENSON.getTelegram().toString();
    private static final String VALID_GITHUB = BENSON.getGitHub().toString();
    private static final List<JsonAdaptedSkill> VALID_SKILLS = BENSON.getSkills().stream()
            .map(JsonAdaptedSkill::new)
            .collect(Collectors.toList());
    private static final boolean VALID_LOOKING_FOR_TEAM = false;
    private static final List<String> VALID_HACKATHONS = new ArrayList<>();
    private static final List<String> VALID_CURRENT_HACKATHONS = new ArrayList<>();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_EMAIL,
                        VALID_TELEGRAM, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                        VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_EMAIL,
                VALID_TELEGRAM, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_EMAIL,
                        VALID_TELEGRAM, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                        VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null,
                VALID_TELEGRAM, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_EMAIL,
                INVALID_TELEGRAM, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = Telegram.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTelegram_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_EMAIL,
                null, VALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Telegram.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    // 🟩 NEW TESTS for GitHub
    @Test
    public void toModelType_invalidGitHub_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_EMAIL,
                VALID_TELEGRAM, INVALID_GITHUB, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = GitHub.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGitHub_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_EMAIL,
                VALID_TELEGRAM, null, VALID_SKILLS, null, VALID_LOOKING_FOR_TEAM,
                VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, GitHub.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSkills_throwsIllegalValueException() {
        List<JsonAdaptedSkill> invalidSkills = new ArrayList<>(VALID_SKILLS);
        invalidSkills.add(new JsonAdaptedSkill(INVALID_SKILL, "BEGINNER"));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_EMAIL,
                        VALID_TELEGRAM, VALID_GITHUB, invalidSkills, null, VALID_LOOKING_FOR_TEAM,
                        VALID_HACKATHONS, VALID_CURRENT_HACKATHONS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
