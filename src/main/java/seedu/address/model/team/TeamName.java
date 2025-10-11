package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Team's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTeamName(String)}
 */
public class TeamName {

    public static final String MESSAGE_CONSTRAINTS =
            "Team names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTeamName;

    /**
     * Constructs a {@code TeamName}.
     *
     * @param teamName A valid team name.
     */
    public TeamName(String teamName) {
        requireNonNull(teamName);
        checkArgument(isValidTeamName(teamName), MESSAGE_CONSTRAINTS);
        fullTeamName = teamName;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidTeamName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullTeamName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeamName)) {
            return false;
        }

        TeamName otherTeamName = (TeamName) other;
        return fullTeamName.equals(otherTeamName.fullTeamName);
    }

    @Override
    public int hashCode() {
        return fullTeamName.hashCode();
    }
}
