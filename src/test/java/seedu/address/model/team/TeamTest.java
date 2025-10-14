package seedu.address.model.team;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.testutil.TeamBuilder;

public class TeamTest {

    private final TeamName validTeamName = new TeamName("Alpha Team");
    private final HackathonName validHackathonName = new HackathonName("Tech Challenge 2024");
    private final Set<Person> validMembers = Set.of(ALICE, BENSON);

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null));
        assertThrows(NullPointerException.class, () -> new Team(null, validMembers));
        assertThrows(NullPointerException.class, () -> new Team(null, validHackathonName, validMembers));
    }

    @Test
    public void constructor_nullMembers_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(validTeamName, (Set<Person>) null));
        assertThrows(NullPointerException.class, () -> new Team(validTeamName, validHackathonName, null));
    }

    @Test
    public void constructor_nullHackathonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(validTeamName, null, validMembers));
    }

    @Test
    public void constructor_teamNameOnly_success() {
        Team team = new Team(validTeamName);
        assertEquals(validTeamName, team.getTeamName());
        assertEquals(null, team.getHackathonName());
        assertEquals(0, team.getSize());
        assertTrue(team.getMembers().isEmpty());
    }

    @Test
    public void constructor_teamNameAndMembers_success() {
        Team team = new Team(validTeamName, validMembers);
        assertEquals(validTeamName, team.getTeamName());
        assertEquals(null, team.getHackathonName());
        assertEquals(2, team.getSize());
        assertTrue(team.hasMember(ALICE));
        assertTrue(team.hasMember(BENSON));
    }

    @Test
    public void constructor_fullConstructor_success() {
        Team team = new Team(validTeamName, validHackathonName, validMembers);
        assertEquals(validTeamName, team.getTeamName());
        assertEquals(validHackathonName, team.getHackathonName());
        assertEquals(2, team.getSize());
        assertTrue(team.hasMember(ALICE));
        assertTrue(team.hasMember(BENSON));
    }

    @Test
    public void constructor_emptyMembers_success() {
        Set<Person> emptyMembers = new HashSet<>();
        Team team = new Team(validTeamName, validHackathonName, emptyMembers);
        assertEquals(validTeamName, team.getTeamName());
        assertEquals(validHackathonName, team.getHackathonName());
        assertEquals(0, team.getSize());
        assertTrue(team.getMembers().isEmpty());
    }

    @Test
    public void getMembers_modifyReturnedSet_originalSetUnchanged() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        Set<Person> returnedMembers = team.getMembers();

        // Should throw UnsupportedOperationException when trying to modify
        assertThrows(UnsupportedOperationException.class, () -> returnedMembers.add(CARL));

        // Original team should be unchanged
        assertEquals(2, team.getSize());
        assertFalse(team.hasMember(CARL));
    }

    @Test
    public void hasMember_nullPerson_throwsNullPointerException() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE, BENSON)
                .build();
        assertThrows(NullPointerException.class, () -> team.hasMember(null));
    }

    @Test
    public void hasMember_personInTeam_returnsTrue() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE, BENSON)
                .build();
        assertTrue(team.hasMember(ALICE));
        assertTrue(team.hasMember(BENSON));
    }

    @Test
    public void hasMember_personNotInTeam_returnsFalse() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE, BENSON)
                .build();
        assertFalse(team.hasMember(CARL));
    }

    @Test
    public void isSameTeam_sameObject_returnsTrue() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE, BENSON)
                .build();
        assertTrue(team.isSameTeam(team));
    }

    @Test
    public void isSameTeam_null_returnsFalse() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .build();
        assertFalse(team.isSameTeam(null));
    }

    @Test
    public void isSameTeam_sameTeamName_returnsTrue() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Different Hackathon")
                .withMembers(CARL)
                .build();
        assertTrue(team1.isSameTeam(team2));
    }

    @Test
    public void isSameTeam_differentTeamName_returnsFalse() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Beta Team")
                .build();
        assertFalse(team1.isSameTeam(team2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE)
                .build();
        assertTrue(team.equals(team));
    }

    @Test
    public void equals_null_returnsFalse() {
        Team team = new TeamBuilder().build();
        assertFalse(team.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Team team = new TeamBuilder().build();
        assertFalse(team.equals("not a team"));
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        assertTrue(team1.equals(team2));
    }

    @Test
    public void equals_differentTeamName_returnsFalse() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Beta Team")
                .withMembers(ALICE)
                .build();
        assertFalse(team1.equals(team2));
    }

    @Test
    public void equals_differentHackathonName_returnsFalse() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Different Hackathon")
                .withMembers(ALICE)
                .build();
        assertFalse(team1.equals(team2));
    }

    @Test
    public void equals_oneHackathonNameNull_returnsFalse() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withoutHackathonName()
                .withMembers(ALICE)
                .build();
        assertFalse(team1.equals(team2));
    }

    @Test
    public void equals_bothHackathonNamesNull_returnsTrue() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withoutHackathonName()
                .withMembers(ALICE)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withoutHackathonName()
                .withMembers(ALICE)
                .build();
        assertTrue(team1.equals(team2));
    }

    @Test
    public void equals_differentMembers_returnsFalse() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(ALICE, BENSON)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withMembers(CARL)
                .build();
        assertFalse(team1.equals(team2));
    }

    @Test
    public void hashCode_sameAttributes_sameHashCode() {
        Team team1 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        Team team2 = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        assertEquals(team1.hashCode(), team2.hashCode());
    }

    @Test
    public void toString_teamWithHackathon_correctFormat() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE, BENSON)
                .build();
        String expected = "seedu.address.model.team.Team{teamName=Alpha Team, "
                + "hackathonName=Tech Challenge 2024,"
                + " members=2 member(s)}";
        assertEquals(expected, team.toString());
    }

    @Test
    public void toString_teamWithoutHackathon_correctFormat() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withoutHackathonName()
                .withMembers(ALICE, BENSON)
                .build();
        String expected = "seedu.address.model.team.Team{teamName=Alpha Team, members=2 member(s)}";
        assertEquals(expected, team.toString());
    }

    @Test
    public void toString_emptyTeam_correctFormat() {
        Team team = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withoutHackathonName()
                .withoutMembers()
                .build();
        String expected = "seedu.address.model.team.Team{teamName=Alpha Team, members=0 member(s)}";
        assertEquals(expected, team.toString());
    }
}
