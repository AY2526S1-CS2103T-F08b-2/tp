package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidSkillName_throwsIllegalArgumentException() {
        String invalidSkillName = "";
        assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkillName));
    }

    @Test
    public void isValidSkillName() {
        // null skill name
        assertThrows(NullPointerException.class, () -> Skill.isValidSkillName(null));

        // invalid skill names
        assertFalse(Skill.isValidSkillName("")); // empty string
        assertFalse(Skill.isValidSkillName(" ")); // spaces only
        assertFalse(Skill.isValidSkillName("Java")); // uppercase
        assertFalse(Skill.isValidSkillName("#sharp")); // starts with #
        assertFalse(Skill.isValidSkillName("skill name")); // contains space
        assertFalse(Skill.isValidSkillName("PYTHON")); // uppercase

        // valid skill names
        assertTrue(Skill.isValidSkillName("java")); // lowercase alphanumeric
        assertTrue(Skill.isValidSkillName("c++")); // with plus
        assertTrue(Skill.isValidSkillName("c#")); // with hash
        assertTrue(Skill.isValidSkillName(".net")); // starts with dot
        assertTrue(Skill.isValidSkillName("react.js")); // contains dot
        assertTrue(Skill.isValidSkillName("node.js")); // contains dot
        assertTrue(Skill.isValidSkillName("vue.js")); // contains dot
        assertTrue(Skill.isValidSkillName("react-native")); // contains hyphen
        assertTrue(Skill.isValidSkillName("vue-router")); // contains hyphen
        assertTrue(Skill.isValidSkillName("scikit_learn")); // contains underscore
        assertTrue(Skill.isValidSkillName("asp.net")); // contains dot
        assertTrue(Skill.isValidSkillName("f#")); // with hash
        assertTrue(Skill.isValidSkillName("python3")); // alphanumeric
        assertTrue(Skill.isValidSkillName("web3.0")); // with dot and number
        assertTrue(Skill.isValidSkillName("express.js")); // contains dot
        assertTrue(Skill.isValidSkillName("next.js")); // contains dot
    }

}
