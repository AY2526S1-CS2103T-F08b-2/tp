package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's GitHub username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGitHub(String)}
 */
public class GitHub {
    public static final String MESSAGE_CONSTRAINTS =
            "GitHub usernames should be 1–39 characters long, "
                    + "contain only letters, numbers, or hyphens, "
                    + "cannot start or end with a hyphen or have 2 consecutive hyphens, "
                    + "and cannot contain underscores, spaces, or symbols.";

    // Explanation:
    // (?!-)       → must not start with '-'
    // (?!.*--)    → no consecutive hyphens
    // [A-Za-z0-9-]{1,39} → only valid chars, length 1–39
    // (?<!-)      → must not end with '-'
    public static final String VALIDATION_REGEX = "^(?!-)(?!.*--)[A-Za-z0-9-]{1,39}(?<!-)$";

    public final String value;

    /**
     * Constructs a {@code GitHub}.
     *
     * @param username A valid GitHub username.
     */
    public GitHub(String username) {
        requireNonNull(username);
        checkArgument(isValidGitHub(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid GitHub username.
     */
    public static boolean isValidGitHub(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GitHub
                && value.equals(((GitHub) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
