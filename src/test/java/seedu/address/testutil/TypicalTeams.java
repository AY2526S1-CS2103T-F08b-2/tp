package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A utility class containing a list of {@code Team} objects to be used in tests.
 */
public class TypicalTeams {

    public static final Team ALPHA_TEAM = new TeamBuilder()
            .withTeamName("Alpha Team")
            .withHackathonName("Tech Innovation 2024")
            .withMembers(TypicalPersons.ALICE, TypicalPersons.BENSON)
            .build();

    public static final Team BETA_TEAM = new TeamBuilder()
            .withTeamName("Beta Squad")
            .withHackathonName("AI Challenge 2024")
            .withMembers(TypicalPersons.CARL, TypicalPersons.DANIEL)
            .build();

    public static final Team GAMMA_TEAM = new TeamBuilder()
            .withTeamName("Gamma Force")
            .withHackathonName("Web Dev Contest")
            .withMembers(TypicalPersons.ELLE, TypicalPersons.FIONA, TypicalPersons.GEORGE)
            .build();

    // Team without hackathon
    public static final Team DELTA_TEAM = new TeamBuilder()
            .withTeamName("Delta Warriors")
            .withoutHackathonName()
            .withMembers(TypicalPersons.ALICE, TypicalPersons.CARL)
            .build();

    // Empty team with hackathon
    public static final Team EPSILON_TEAM = new TeamBuilder()
            .withTeamName("Epsilon Pioneers")
            .withHackathonName("Data Science Marathon")
            .withoutMembers()
            .build();

    // Empty team without hackathon
    public static final Team ZETA_TEAM = new TeamBuilder()
            .withTeamName("Zeta Innovators")
            .withoutHackathonName()
            .withoutMembers()
            .build();

    // Single member team
    public static final Team ETA_TEAM = new TeamBuilder()
            .withTeamName("Eta Solo")
            .withHackathonName("Mobile App Challenge")
            .withMembers(TypicalPersons.HOON)
            .build();

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

        // Ensure all persons referenced in teams are in the address book
        // This prevents data loss during serialization/deserialization
        java.util.Set<String> knownPersonNames = new java.util.HashSet<>();
        for (Person person : ab.getPersonList()) {
            knownPersonNames.add(person.getName().fullName);
        }

        for (Team team : ab.getTeamList()) {
            for (Person member : team.getMembers()) {
                if (!knownPersonNames.contains(member.getName().fullName)) {
                    ab.addPerson(member);
                    knownPersonNames.add(member.getName().fullName);
                }
            }
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
        teams.add(new TeamBuilder()
                .withTeamName("Very Long Team Name With Many Words That Still Fits Validation")
                .withHackathonName("Long Hackathon Name Event")
                .withoutMembers()
                .build());

        // Team with numbers in name
        teams.add(new TeamBuilder()
                .withTeamName("Team 42")
                .withHackathonName("Hackathon 2024")
                .withoutMembers()
                .build());

        // Team with minimal valid name
        teams.add(new TeamBuilder()
                .withTeamName("A")
                .withHackathonName("B")
                .withoutMembers()
                .build());

        return teams;
    }
}
