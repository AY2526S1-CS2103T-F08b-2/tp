package seedu.address.model.team;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;

/**
 * Represents a Team in the address book.
 * Guarantees: team name is present and not null, field values are validated, immutable.
 */
public class Team {

    private final TeamName teamName;
    private final HackathonName hackathonName;
    private final Set<Person> members;

    /**
     * Creates a team with the given name and no members.
     */
    public Team(TeamName teamName) {
        requireNonNull(teamName);
        this.teamName = teamName;
        this.hackathonName = null;
        this.members = new HashSet<>();
    }

    /**
     * Creates a team with the given name and initial members.
     */
    public Team(TeamName teamName, Set<Person> members) {
        requireAllNonNull(teamName, members);
        this.teamName = teamName;
        this.hackathonName = null;
        this.members = new HashSet<>(members);
    }

    /**
     * Creates a team with the given name, hackathon name, and initial members.
     */
    public Team(TeamName teamName, HackathonName hackathonName, Set<Person> members) {
        requireAllNonNull(teamName, hackathonName, members);
        this.teamName = teamName;
        this.hackathonName = hackathonName;
        this.members = new HashSet<>(members);
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public HackathonName getHackathonName() {
        return hackathonName;
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
        return members.stream().anyMatch(member -> member.isSamePerson(person));
    }

    /**
     * Returns true if both teams have the same team name.
     * This defines a weaker notion of equality between two teams.
     * Team name alone is used as the unique identifier.
     */
    public boolean isSameTeam(Team otherTeam) {
        if (otherTeam == this) {
            return true;
        }

        return otherTeam != null
                && otherTeam.getTeamName().equals(getTeamName());
    }

    /**
     * Returns true if both teams have the same team name, hackathon name, and members.
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
                && Objects.equals(hackathonName, otherTeam.hackathonName)
                && members.equals(otherTeam.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName, hackathonName, members);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("teamName", teamName);

        if (hackathonName != null) {
            builder.add("hackathonName", hackathonName);
        }

        builder.add("members", members.size() + " member(s)");
        return builder.toString();
    }
}



