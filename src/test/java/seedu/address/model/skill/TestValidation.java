package seedu.address.model.skill;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for Skill name validation.
 * Tests that skill names must be lowercase, at least 1 character long,
 * and can contain + and # symbols but cannot start with # or +.
 */
public class TestValidation {

    /**
     * Tests that valid skill names return true when validated.
     * This method tests various valid skill name formats including:
     * - Standard lowercase skill names (java, python, javascript)
     * - Skills containing numbers (c++, c#, html5, react16)
     * - Skills with valid symbols +, #, ., -, _ (not # at start)
     * - Minimum length skills with 1 character (a, b, 1, 2)
     */
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

        // Valid with dots (for .NET, React.js, etc.)
        assertTrue(Skill.isValidSkillName("node.js"));
        assertTrue(Skill.isValidSkillName("vue.js"));
        assertTrue(Skill.isValidSkillName(".net"));
        assertTrue(Skill.isValidSkillName("asp.net"));
        assertTrue(Skill.isValidSkillName("react.js"));

        // Valid with hyphens (for react-native, etc.)
        assertTrue(Skill.isValidSkillName("spring-boot"));
        assertTrue(Skill.isValidSkillName("react-native"));
        assertTrue(Skill.isValidSkillName("vue-router"));

        // Valid with underscores (for scikit_learn, etc.)
        assertTrue(Skill.isValidSkillName("scikit_learn"));
        assertTrue(Skill.isValidSkillName("tensor_flow"));

        // Valid minimum length (1 character)
        assertTrue(Skill.isValidSkillName("a"));
        assertTrue(Skill.isValidSkillName("b"));
        assertTrue(Skill.isValidSkillName("1"));
        assertTrue(Skill.isValidSkillName("2"));
    }

    /**
     * Tests that invalid skill names return false when validated.
     * This method tests various invalid skill name formats including:
     * - Skills containing uppercase letters (Java, PYTHON, JavaScript, C++, C#)
     * - Skills starting with forbidden symbol (#)
     * - Skills containing invalid special characters (/, @, spaces)
     * - Empty strings and whitespace-only strings
     */
    @Test
    public void isValidSkillName_invalidSkillNames_returnsFalse() {
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

        // Contains invalid special characters (/, @, spaces, etc.)
        assertFalse(Skill.isValidSkillName("c++/java"));
        assertFalse(Skill.isValidSkillName("react@16"));
        assertFalse(Skill.isValidSkillName("node js")); // space
        assertFalse(Skill.isValidSkillName("spring boot")); // space
        assertFalse(Skill.isValidSkillName("c++\\java")); // backslash
        assertFalse(Skill.isValidSkillName("react&vue")); // ampersand
        assertFalse(Skill.isValidSkillName("node$js")); // dollar sign

        // Empty or whitespace
        assertFalse(Skill.isValidSkillName(""));
        assertFalse(Skill.isValidSkillName(" "));
        assertFalse(Skill.isValidSkillName("  "));
    }

    /**
     * Tests edge cases for skill name validation.
     * This method tests complex scenarios including:
     * - Skills with mixed valid symbols (framework+library#version)
     * - Skills starting with numbers or dots (3dmodeling, .net)
     * - Skills with multiple consecutive valid symbols (skill++, tool##)
     * - Skills starting with invalid symbols (++skill, ##tool, #sharp)
     * - Complex combinations of dots, hyphens, and underscores
     * These edge cases ensure the validation regex handles complex combinations correctly.
     */
    @Test
    public void isValidSkillName_edgeCases_correctValidation() {
        // Mixed valid symbols
        assertTrue(Skill.isValidSkillName("framework+library#version"));
        assertTrue(Skill.isValidSkillName("tool123+addon#extension"));
        assertTrue(Skill.isValidSkillName("web3.0-framework"));
        assertTrue(Skill.isValidSkillName("lib_name.v2+ext"));

        // Numbers at start
        assertTrue(Skill.isValidSkillName("3dmodeling"));
        assertTrue(Skill.isValidSkillName("2dgraphics"));

        // Dot at start (for .NET)
        assertTrue(Skill.isValidSkillName(".net"));
        assertTrue(Skill.isValidSkillName(".core"));

        // Multiple consecutive symbols
        assertTrue(Skill.isValidSkillName("skill++"));
        assertTrue(Skill.isValidSkillName("tool##"));
        assertTrue(Skill.isValidSkillName("lang+#hybrid"));
        assertTrue(Skill.isValidSkillName("lib--version"));
        assertTrue(Skill.isValidSkillName("tool__ext"));

        // Complex real-world examples
        assertTrue(Skill.isValidSkillName("next.js"));
        assertTrue(Skill.isValidSkillName("express.js"));
        assertTrue(Skill.isValidSkillName("scikit-learn"));
        assertTrue(Skill.isValidSkillName("react-native"));

        // Invalid: starts with #
        assertFalse(Skill.isValidSkillName("#sharp"));
        assertFalse(Skill.isValidSkillName("#python"));

        // Invalid: contains spaces or other special chars
        assertFalse(Skill.isValidSkillName("react native"));
        assertFalse(Skill.isValidSkillName("node@js"));
        assertFalse(Skill.isValidSkillName("c++/python"));
    }
}
