package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegram(String)}
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles should be 5–32 characters long, "
                    + "contain only letters, numbers, and underscores, "
                    + "cannot start with '@', cannot contain spaces, "
                    + "cannot start or end with an underscore, "
                    + "and cannot have consecutive underscores.";

    // Explanation:
    // (?!@) → must not start with '@'
    // (?!_) → must not start with '_'
    // (?!.*__) → must not contain consecutive underscores
    // (?!.*_$) → must not end with '_'
    // [A-Za-z0-9_]{5,32} → only valid characters (length 5–32)
    public static final String VALIDATION_REGEX = "^(?!@)(?!_)(?!.*__)(?!.*_$)[A-Za-z0-9_]{5,32}$";

    public final String value;

    /**
     * Constructs a {@code Telegram}.
     *
     * @param handle A valid Telegram handle.
     */
    public Telegram(String handle) {
        requireNonNull(handle);
        checkArgument(isValidTelegram(handle), MESSAGE_CONSTRAINTS);
        value = handle;
    }

    /**
     * Returns true if a given string is a valid Telegram handle.
     */
    public static boolean isValidTelegram(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Telegram
                && value.equalsIgnoreCase(((Telegram) other).value));
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
