package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for Skill name validation.
 * Tests that skill names must be lowercase, at least 2 characters long,
 * and can contain + and # symbols but cannot start with #.
 */
public class TestValidation {

    @Test
    public void isValidSkillName_validSkillNames_returnsTrue() {
        // Valid lowercase skill names
        assertTrue(Skill.isValidSkillName("java"));
        assertTrue(Skill.isValidSkillName("python"));
        assertTrue(Skill.isValidSkillName("javascript"));

        // Valid with numbers
        assertTrue(Skill.isValidSkillName("c++"));
        assertTrue(Skill.isValidSkillName("c#"));
        assertTrue(Skill.isValidSkillName("html5"));
        assertTrue(Skill.isValidSkillName("react16"));

        // Valid with + and # symbols (not at start)
        assertTrue(Skill.isValidSkillName("node+express"));
        assertTrue(Skill.isValidSkillName("spring#boot"));
        assertTrue(Skill.isValidSkillName("docker+kubernetes"));

        // Valid minimum length (2 characters)
        assertTrue(Skill.isValidSkillName("ab"));
        assertTrue(Skill.isValidSkillName("a1"));
        assertTrue(Skill.isValidSkillName("1a"));
        assertTrue(Skill.isValidSkillName("12"));
    }

    @Test
    public void isValidSkillName_invalidSkillNames_returnsFalse() {
        // Too short (single character)
        assertFalse(Skill.isValidSkillName("a"));
        assertFalse(Skill.isValidSkillName("1"));
        assertFalse(Skill.isValidSkillName("s"));

        // Contains uppercase letters
        assertFalse(Skill.isValidSkillName("Java"));
        assertFalse(Skill.isValidSkillName("PYTHON"));
        assertFalse(Skill.isValidSkillName("JavaScript"));
        assertFalse(Skill.isValidSkillName("C++"));
        assertFalse(Skill.isValidSkillName("C#"));

        // Starts with # symbol
        assertFalse(Skill.isValidSkillName("#python"));
        assertFalse(Skill.isValidSkillName("#java"));
        assertFalse(Skill.isValidSkillName("#nodejs"));

        // Starts with + symbol
        assertFalse(Skill.isValidSkillName("+python"));
        assertFalse(Skill.isValidSkillName("+java"));

        // Contains invalid special characters
        assertFalse(Skill.isValidSkillName("node.js"));
        assertFalse(Skill.isValidSkillName("c++/java"));
        assertFalse(Skill.isValidSkillName("spring-boot"));
        assertFalse(Skill.isValidSkillName("react@16"));
        assertFalse(Skill.isValidSkillName("vue.js"));

        // Empty or null
        assertFalse(Skill.isValidSkillName(""));
        assertFalse(Skill.isValidSkillName(" "));
        assertFalse(Skill.isValidSkillName("  "));
    }

    @Test
    public void isValidSkillName_edgeCases_correctValidation() {
        // Mixed valid symbols
        assertTrue(Skill.isValidSkillName("framework+library#version"));
        assertTrue(Skill.isValidSkillName("tool123+addon#extension"));

        // Numbers at start
        assertTrue(Skill.isValidSkillName("3dmodeling"));
        assertTrue(Skill.isValidSkillName("2dgraphics"));

        // Multiple consecutive symbols
        assertTrue(Skill.isValidSkillName("skill++"));
        assertTrue(Skill.isValidSkillName("tool##"));
        assertTrue(Skill.isValidSkillName("lang+#hybrid"));

        // Invalid consecutive symbols at start
        assertFalse(Skill.isValidSkillName("++skill"));
        assertFalse(Skill.isValidSkillName("##tool"));
        assertFalse(Skill.isValidSkillName("#+mixed"));
    }
}
