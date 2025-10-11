package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Team in the address book.
 * Guarantees: team name is present and not null, field values are validated, immutable.
 */
public class Team {

    private final TeamName teamName;
    private final Set<Person> members;

    /**
     * Creates a team with the given name and no members.
     */
    public Team(TeamName teamName) {
        requireNonNull(teamName);
        this.teamName = teamName;
        this.members = new HashSet<>();
    }

    /**
     * Creates a team with the given name and initial members.
     */
    public Team(TeamName teamName, Set<Person> members) {
        requireAllNonNull(teamName, members);
        this.teamName = teamName;
        this.members = new HashSet<>(members);
    }

    public TeamName getTeamName() {
        return teamName;
    }

    /**
     * Returns an immutable set of team members.
     */
    public Set<Person> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    /**
     * Returns the number of members in this team.
     */
    public int getSize() {
        return members.size();
    }

    /**
     * Returns true if the person is a member of this team.
     */
    public boolean hasMember(Person person) {
        requireNonNull(person);
        return members.contains(person);
    }

    /**
     * Returns true if both teams have the same team name.
     * This defines a weaker notion of equality between two teams.
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }

        return otherTeam != null
                && otherTeam.getTeamName().equals(getTeamName());
    }

    /**
     * Returns true if both teams have the same team name and members.
     * This defines a stronger notion of equality between two teams.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = (Team) other;
        return teamName.equals(otherTeam.teamName)
                && members.equals(otherTeam.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, members);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .add("members", members.size() + " member(s)")
                .toString();
    }
}
