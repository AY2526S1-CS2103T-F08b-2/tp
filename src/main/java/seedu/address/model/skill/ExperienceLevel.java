package seedu.address.model.skill;

/**
 * Represents the experience level for a skill.
 */
public enum ExperienceLevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    public static final String MESSAGE_CONSTRAINTS = "Experience level should be one of: "
            + "Beginner, Intermediate, Advanced (case-insensitive)";

    private final String displayName;

    ExperienceLevel(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the experience level.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns true if a given string is a valid experience level.
     */
    public static boolean isValidExperienceLevel(String test) {
        for (ExperienceLevel level : ExperienceLevel.values()) {
            if (level.name().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a string into an ExperienceLevel.
     * Case-insensitive.
     *
     * @throws IllegalArgumentException if the string is not a valid experience level
     */
    public static ExperienceLevel fromString(String text) {
        for (ExperienceLevel level : ExperienceLevel.values()) {
            if (level.name().equalsIgnoreCase(text)) {
                return level;
            }
        }
        throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
    }

    @Override
    public String toString() {
        return displayName;
    }
}

