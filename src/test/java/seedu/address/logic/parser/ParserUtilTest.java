package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.skill.Skill;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SKILL = "#friend";
    private static final String INVALID_HACKATHON = ""; // Empty hackathon name

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_SKILL_1 = "coding";
    private static final String VALID_SKILL_2 = "testing";
    private static final String VALID_HACKATHON_1 = "NUSHack";
    private static final String VALID_HACKATHON_2 = "HackForGood";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseSkill_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkill(null));
    }

    @Test
    public void parseSkill_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkill(INVALID_SKILL));
    }

    @Test
    public void parseSkill_validValueWithoutWhitespace_returnsSkill() throws Exception {
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(VALID_SKILL_1));
    }

    @Test
    public void parseSkill_validValueWithWhitespace_returnsTrimmedSkill() throws Exception {
        String skillWithWhitespace = WHITESPACE + VALID_SKILL_1 + WHITESPACE;
        Skill expectedSkill = new Skill(VALID_SKILL_1);
        assertEquals(expectedSkill, ParserUtil.parseSkill(skillWithWhitespace));
    }

    @Test
    public void parseSkills_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSkills(null));
    }

    @Test
    public void parseSkills_collectionWithInvalidSkills_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, INVALID_SKILL)));
    }

    @Test
    public void parseSkills_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseSkills(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSkills_collectionWithValidSkills_returnsSkillSet() throws Exception {
        Set<Skill> actualSkillSet = ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_2));
        Set<Skill> expectedSkillSet = new HashSet<Skill>(Arrays.asList(
                new Skill(VALID_SKILL_1), new Skill(VALID_SKILL_2)));

        assertEquals(expectedSkillSet, actualSkillSet);
    }

    @Test
    public void parseSkills_collectionWithDuplicateSkills_throwsParseException() {
        // Test duplicate skill names
        assertThrows(ParseException.class, () -> ParserUtil.parseSkills(Arrays.asList(VALID_SKILL_1, VALID_SKILL_1)));
    }

    @Test
    public void parseSkills_collectionWithDuplicateSkillsWithDifferentLevels_throwsParseException() {
        // Test duplicate skill names with different experience levels
        assertThrows(ParseException.class, () ->
            ParserUtil.parseSkills(Arrays.asList("java:beginner", "java:intermediate")));
    }

    @Test
    public void parseHackathonName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHackathonName(null));
    }

    @Test
    public void parseHackathonName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHackathonName(INVALID_HACKATHON));
    }

    @Test
    public void parseHackathonName_validValueWithoutWhitespace_returnsHackathonName() throws Exception {
        HackathonName expectedHackathon = new HackathonName(VALID_HACKATHON_1);
        assertEquals(expectedHackathon, ParserUtil.parseHackathonName(VALID_HACKATHON_1));
    }

    @Test
    public void parseHackathonName_validValueWithWhitespace_returnsTrimmedHackathonName() throws Exception {
        String hackathonWithWhitespace = WHITESPACE + VALID_HACKATHON_1 + WHITESPACE;
        HackathonName expectedHackathon = new HackathonName(VALID_HACKATHON_1);
        assertEquals(expectedHackathon, ParserUtil.parseHackathonName(hackathonWithWhitespace));
    }

    @Test
    public void parseHackathonNames_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHackathonNames(null));
    }

    @Test
    public void parseHackathonNames_collectionWithInvalidHackathons_throwsParseException() {
        assertThrows(ParseException.class, () ->
            ParserUtil.parseHackathonNames(Arrays.asList(VALID_HACKATHON_1, INVALID_HACKATHON)));
    }

    @Test
    public void parseHackathonNames_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseHackathonNames(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseHackathonNames_collectionWithValidHackathons_returnsHackathonSet() throws Exception {
        Set<HackathonName> actualHackathonSet = ParserUtil.parseHackathonNames(
            Arrays.asList(VALID_HACKATHON_1, VALID_HACKATHON_2));
        Set<HackathonName> expectedHackathonSet = new HashSet<>(Arrays.asList(
            new HackathonName(VALID_HACKATHON_1), new HackathonName(VALID_HACKATHON_2)));

        assertEquals(expectedHackathonSet, actualHackathonSet);
    }

    @Test
    public void parseHackathonNames_collectionWithDuplicateHackathons_throwsParseException() {
        // Test duplicate hackathon names
        assertThrows(ParseException.class, () ->
            ParserUtil.parseHackathonNames(Arrays.asList(VALID_HACKATHON_1, VALID_HACKATHON_1)));
    }

    @Test
    public void parseHackathonNames_collectionWithDuplicateHackathonsCaseInsensitive_throwsParseException() {
        // Test duplicate hackathon names with different cases
        assertThrows(ParseException.class, () ->
            ParserUtil.parseHackathonNames(Arrays.asList("NUSHack", "nushack")));
        assertThrows(ParseException.class, () ->
            ParserUtil.parseHackathonNames(Arrays.asList("TechChallenge", "TECHCHALLENGE")));
        assertThrows(ParseException.class, () ->
            ParserUtil.parseHackathonNames(Arrays.asList("AI Contest", "ai contest", "AI CONTEST")));
    }
}
