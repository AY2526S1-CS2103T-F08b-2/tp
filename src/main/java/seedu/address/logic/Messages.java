package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX = "The team index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Skills: ");
        person.getSkills().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code team} for display to the user.
     */
    public static String format(Team team) {
        final StringBuilder builder = new StringBuilder();
        builder.append(team.getTeamName());
        return builder.toString();
    }

    /**
     * Returns a persons listed overview message with correct singular/plural form.
     * Per project rule: 0 or 1 -> "person", 2+ -> "persons".
     */
    public static String getPersonsListedOverview(int count) {
        String noun = (count <= 1) ? "person" : "persons";
        return String.format("%d %s listed!", count, noun);
    }

}
