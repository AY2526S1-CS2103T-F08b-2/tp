package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team ALPHA_TEAM;
    public static final Team BETA_TEAM;
    public static final Team GAMMA_TEAM;
    public static final Team DELTA_TEAM;
    public static final Team EPSILON_TEAM;
    public static final Team ZETA_TEAM;
    public static final Team ETA_TEAM;

    static {
        // Create teams with different configurations for comprehensive testing
        Set<Person> alphaMembers = new HashSet<>();
        alphaMembers.add(TypicalPersons.ALICE);
        alphaMembers.add(TypicalPersons.BENSON);
        ALPHA_TEAM = new Team(new TeamName("Alpha Team"),
                             new HackathonName("Tech Innovation 2024"),
                             alphaMembers);

        Set<Person> betaMembers = new HashSet<>();
        betaMembers.add(TypicalPersons.CARL);
        betaMembers.add(TypicalPersons.DANIEL);
        BETA_TEAM = new Team(new TeamName("Beta Squad"),
                            new HackathonName("AI Challenge 2024"),
                            betaMembers);

        Set<Person> gammaMembers = new HashSet<>();
        gammaMembers.add(TypicalPersons.ELLE);
        gammaMembers.add(TypicalPersons.FIONA);
        gammaMembers.add(TypicalPersons.GEORGE);
        GAMMA_TEAM = new Team(new TeamName("Gamma Force"),
                             new HackathonName("Web Dev Contest"),
                             gammaMembers);

        // Team without hackathon
        Set<Person> deltaMembers = new HashSet<>();
        deltaMembers.add(TypicalPersons.ALICE);
        deltaMembers.add(TypicalPersons.CARL);
        DELTA_TEAM = new Team(new TeamName("Delta Warriors"), deltaMembers);

        // Empty team with hackathon
        EPSILON_TEAM = new Team(new TeamName("Epsilon Pioneers"),
                               new HackathonName("Data Science Marathon"),
                               new HashSet<>());

        // Empty team without hackathon
        ZETA_TEAM = new Team(new TeamName("Zeta Innovators"), new HashSet<>());

        // Single member team
        Set<Person> etaMembers = new HashSet<>();
        etaMembers.add(TypicalPersons.HOON);
        ETA_TEAM = new Team(new TeamName("Eta Solo"),
                           new HackathonName("Mobile App Challenge"),
                           etaMembers);
    }

    private TypicalTeams() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical teams.
     */
    public static AddressBook getTypicalAddressBookWithTeams() {
        AddressBook ab = new AddressBook();

        // Add all typical persons first
        for (Person person : TypicalPersons.getTypicalPersons()) {
            ab.addPerson(person);
        }

        // Add all typical teams
        for (Team team : getTypicalTeams()) {
            ab.addTeam(team);
        }
        return ab;
    }

    /**
     * Returns an {@code AddressBook} with only typical teams (no persons).
     * Note: This may cause issues if teams reference persons that don't exist.
     */
    public static AddressBook getTypicalAddressBookTeamsOnly() {
        AddressBook ab = new AddressBook();
        for (Team team : getTypicalTeams()) {
            ab.addTeam(team);
        }
        return ab;
    }

    public static List<Team> getTypicalTeams() {
        return new ArrayList<>(Arrays.asList(ALPHA_TEAM, BETA_TEAM, GAMMA_TEAM,
                                            DELTA_TEAM, EPSILON_TEAM, ZETA_TEAM, ETA_TEAM));
    }

    /**
     * Returns a list of teams with various edge cases for testing.
     */
    public static List<Team> getEdgeCaseTeams() {
        List<Team> teams = new ArrayList<>();

        // Team with very long name (but still valid)
        teams.add(new Team(new TeamName("Very Long Team Name With Many Words That Still Fits Validation"),
                          new HackathonName("Long Hackathon Name Event"), new HashSet<>()));

        // Team with numbers in name
        teams.add(new Team(new TeamName("Team 42"),
                          new HackathonName("Hackathon 2024"), new HashSet<>()));

        // Team with minimal valid name
        teams.add(new Team(new TeamName("A"), new HackathonName("B"), new HashSet<>()));

        return teams;
    }
}
