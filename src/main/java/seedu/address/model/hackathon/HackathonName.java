package seedu.address.model.hackathon;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a hackathon's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHackathonName(String)}
 */
public class HackathonName {

    public static final String MESSAGE_CONSTRAINTS =
            "Hackathon names should only contain alphanumeric characters and spaces.";

    /*
     * The first character of the hackathon name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code HackathonName}.
     *
     * @param hackathonName A valid hackathon name.
     */
    public HackathonName(String hackathonName) {
        requireNonNull(hackathonName);
        checkArgument(isValidHackathonName(hackathonName), MESSAGE_CONSTRAINTS);
        value = hackathonName;
    }

    /**
     * Returns true if a given string is a valid hackathon name.
     */
    public static boolean isValidHackathonName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HackathonName)) {
            return false;
        }

        HackathonName otherHackathonName = (HackathonName) other;
        return value.equalsIgnoreCase(otherHackathonName.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
