package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSkillName(String)}
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS = "Skills names should be lowercase alphanumeric "
            + "and may include '+' or '#' symbols, but cannot start with '#' and must be at least 1 character long";
    public static final String VALIDATION_REGEX = "[a-z0-9][a-z0-9+#]*";

    public final String skillName;
    public final ExperienceLevel experienceLevel;

    /**
     * Constructs a {@code Skill} with default experience level (BEGINNER).
     *
     * @param skillName A valid skill name.
     */
    public Skill(String skillName) {
        this(skillName, ExperienceLevel.BEGINNER);
    }

    /**
     * Constructs a {@code Skill} with specified experience level.
     *
     * @param skillName A valid skill name.
     * @param experienceLevel The experience level for this skill.
     */
    public Skill(String skillName, ExperienceLevel experienceLevel) {
        requireNonNull(skillName);
        requireNonNull(experienceLevel);
        checkArgument(isValidSkillName(skillName), MESSAGE_CONSTRAINTS);
        this.skillName = skillName;
        this.experienceLevel = experienceLevel;
    }

    /**
     * Returns true if a given string is a valid skill name.
     */
    public static boolean isValidSkillName(String test) {
        if (test == null) {
            throw new NullPointerException();
        }
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the experience level of this skill.
     */
    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Skill)) {
            return false;
        }

        Skill otherSkill = (Skill) other;
        // Only compare skill names - experience level can differ
        return skillName.equals(otherSkill.skillName);
    }

    @Override
    public int hashCode() {
        // Only use skill name for hashing
        return skillName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + skillName + " (" + experienceLevel + ')' + ']';
    }

}
