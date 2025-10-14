package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * A utility class to help with building Team objects for testing.
 * Follows the Builder pattern to provide a fluent interface for creating teams with different configurations.
 */
public class TeamBuilder {

    public static final String DEFAULT_TEAM_NAME = "Default Team";
    public static final String DEFAULT_HACKATHON_NAME = "Default Hackathon";

    private TeamName teamName;
    private HackathonName hackathonName;
    private Set<Person> members;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        teamName = new TeamName(DEFAULT_TEAM_NAME);
        hackathonName = new HackathonName(DEFAULT_HACKATHON_NAME);
        members = new HashSet<>();
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        teamName = teamToCopy.getTeamName();
        hackathonName = teamToCopy.getHackathonName();
        members = new HashSet<>(teamToCopy.getMembers());
    }

    /**
     * Sets the {@code TeamName} of the {@code Team} that we are building.
     */
    public TeamBuilder withTeamName(String teamName) {
        this.teamName = new TeamName(teamName);
        return this;
    }

    /**
     * Sets the {@code HackathonName} of the {@code Team} that we are building.
     */
    public TeamBuilder withHackathonName(String hackathonName) {
        this.hackathonName = new HackathonName(hackathonName);
        return this;
    }

    /**
     * Removes the {@code HackathonName} from the {@code Team} that we are building.
     * This creates a team without a hackathon.
     */
    public TeamBuilder withoutHackathonName() {
        this.hackathonName = null;
        return this;
    }

    /**
     * Sets the {@code members} of the {@code Team} that we are building.
     */
    public TeamBuilder withMembers(Person... members) {
        this.members = new HashSet<>();
        for (Person member : members) {
            this.members.add(member);
        }
        return this;
    }

    /**
     * Sets the {@code members} of the {@code Team} that we are building.
     */
    public TeamBuilder withMembers(Set<Person> members) {
        this.members = new HashSet<>(members);
        return this;
    }

    /**
     * Adds a {@code member} to the {@code Team} that we are building.
     */
    public TeamBuilder addMember(Person member) {
        this.members.add(member);
        return this;
    }

    /**
     * Removes all members from the {@code Team} that we are building.
     */
    public TeamBuilder withoutMembers() {
        this.members = new HashSet<>();
        return this;
    }

    /**
     * Builds and returns the {@code Team} with the configured properties.
     */
    public Team build() {
        if (hackathonName != null) {
            return new Team(teamName, hackathonName, members);
        } else {
            return new Team(teamName, members);
        }
    }
}
