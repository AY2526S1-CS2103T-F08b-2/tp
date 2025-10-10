package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram handle in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegram(String)}
 */
public class Telegram {

    public static final String MESSAGE_CONSTRAINTS =
            "Telegram handles should be 3–32 characters long, "
                    + "contain only letters, numbers, and underscores, "
                    + "cannot start with '@', cannot contain spaces, "
                    + "and cannot end with an underscore.";

    // Explanation:
    // (?!@) → must not start with '@'
    // [A-Za-z0-9_]{3,32} → only valid characters (length 3–32)
    // (?<!_) → must not end with '_'
    // no spaces allowed by default
    public static final String VALIDATION_REGEX = "^(?!@)[A-Za-z0-9_]{3,32}(?<!_)$";

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
                && value.equals(((Telegram) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
