package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTeam.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

public class JsonAdaptedTeamTest {
    private static final String INVALID_TEAM_NAME = "R@ndom Team!";
    private static final String INVALID_HACKATHON_NAME = "@Invalid Hackathon!";

    private static final String VALID_TEAM_NAME = "Alpha Team";
    private static final String VALID_HACKATHON_NAME = "Tech Innovation 2024";
    private static final List<String> VALID_MEMBERS;
    private static final Team VALID_TEAM;

    static {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        members.add(BENSON);

        VALID_MEMBERS = new ArrayList<>();
        VALID_MEMBERS.add(ALICE.getName().fullName);
        VALID_MEMBERS.add(BENSON.getName().fullName);

        VALID_TEAM = new Team(new TeamName(VALID_TEAM_NAME),
                             new HackathonName(VALID_HACKATHON_NAME),
                             members);
    }

    @Test
    public void toModelType_validTeamDetails_returnsTeam() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM);
        Map<String, Person> nameMap = new HashMap<>();
        nameMap.put(ALICE.getName().fullName, ALICE);
        nameMap.put(BENSON.getName().fullName, BENSON);
        assertEquals(VALID_TEAM, team.toModelType(nameMap));
    }

    @Test
    public void toModelType_validTeamDetailsWithoutHackathon_returnsTeam() throws Exception {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        members.add(BENSON);
        Team teamWithoutHackathon = new Team(new TeamName(VALID_TEAM_NAME), members);

        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(teamWithoutHackathon);
        Map<String, Person> nameMap = new HashMap<>();
        nameMap.put(ALICE.getName().fullName, ALICE);
        nameMap.put(BENSON.getName().fullName, BENSON);
        assertEquals(teamWithoutHackathon, jsonTeam.toModelType(nameMap));
    }

    @Test
    public void toModelType_validTeamDetailsEmptyMembers_returnsTeam() throws Exception {
        Team teamWithoutMembers = new Team(new TeamName(VALID_TEAM_NAME),
                                          new HackathonName(VALID_HACKATHON_NAME),
                                          new HashSet<>());

        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(teamWithoutMembers);
        Map<String, Person> nameMap = new HashMap<>();
        Team modelTeam = jsonTeam.toModelType(nameMap);

        assertEquals(teamWithoutMembers, modelTeam);
    }

    @Test
    public void toModelType_invalidTeamName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(INVALID_TEAM_NAME, VALID_HACKATHON_NAME, VALID_MEMBERS);
        String expectedMessage = TeamName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_nullTeamName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(null, VALID_HACKATHON_NAME, VALID_MEMBERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TeamName.class.getSimpleName());
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_invalidHackathonName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, INVALID_HACKATHON_NAME, VALID_MEMBERS);
        String expectedMessage = HackathonName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_nullHackathonName_returnsTeamWithoutHackathon() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, null, VALID_MEMBERS);
        Map<String, Person> nameMap = new HashMap<>();
        nameMap.put(ALICE.getName().fullName, ALICE);
        nameMap.put(BENSON.getName().fullName, BENSON);
        Team modelTeam = team.toModelType(nameMap);

        assertEquals(new TeamName(VALID_TEAM_NAME), modelTeam.getTeamName());
        assertEquals(null, modelTeam.getHackathonName());
        assertEquals(2, modelTeam.getSize());
    }

    @Test
    public void toModelType_nullMembers_returnsTeamWithEmptyMembers() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, VALID_HACKATHON_NAME, null);
        Map<String, Person> nameMap = new HashMap<>();
        Team modelTeam = team.toModelType(nameMap);

        assertEquals(new TeamName(VALID_TEAM_NAME), modelTeam.getTeamName());
        assertEquals(new HackathonName(VALID_HACKATHON_NAME), modelTeam.getHackathonName());
        assertEquals(0, modelTeam.getSize());
    }

    @Test
    public void toModelType_emptyMembers_returnsTeamWithEmptyMembers() throws Exception {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, VALID_HACKATHON_NAME, new ArrayList<>());
        Map<String, Person> nameMap = new HashMap<>();
        Team modelTeam = team.toModelType(nameMap);

        assertEquals(new TeamName(VALID_TEAM_NAME), modelTeam.getTeamName());
        assertEquals(new HackathonName(VALID_HACKATHON_NAME), modelTeam.getHackathonName());
        assertEquals(0, modelTeam.getSize());
    }

    @Test
    public void toModelType_invalidMember_throwsIllegalValueException() {
        List<String> invalidMembers = new ArrayList<>(VALID_MEMBERS);
        // Add a name that does not exist in the person map
        invalidMembers.add("Nonexistent Person");

        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, VALID_HACKATHON_NAME, invalidMembers);
        Map<String, Person> nameMap = new HashMap<>();
        nameMap.put(ALICE.getName().fullName, ALICE);
        nameMap.put(BENSON.getName().fullName, BENSON);

        assertThrows(IllegalValueException.class, () -> team.toModelType(nameMap));
    }

    @Test
    public void constructor_validTeam_success() {
        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(VALID_TEAM);
        // Constructor should not throw any exceptions
        // The actual validation is done during toModelType()
    }

    @Test
    public void constructor_teamWithNullHackathon_success() {
        Set<Person> members = new HashSet<>();
        members.add(ALICE);
        Team teamWithoutHackathon = new Team(new TeamName(VALID_TEAM_NAME), members);

        JsonAdaptedTeam jsonTeam = new JsonAdaptedTeam(teamWithoutHackathon);
        // Constructor should not throw any exceptions
    }

    @Test
    public void toModelType_edgeCaseEmptyTeamName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam("", VALID_HACKATHON_NAME, VALID_MEMBERS);
        String expectedMessage = TeamName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_edgeCaseWhitespaceOnlyTeamName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam("   ", VALID_HACKATHON_NAME, VALID_MEMBERS);
        String expectedMessage = TeamName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_edgeCaseEmptyHackathonName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, "", VALID_MEMBERS);
        String expectedMessage = HackathonName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_edgeCaseWhitespaceOnlyHackathonName_throwsIllegalValueException() {
        JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, "   ", VALID_MEMBERS);
        String expectedMessage = HackathonName.MESSAGE_CONSTRAINTS;
        Map<String, Person> nameMap = new HashMap<>();
        assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
    }

    @Test
    public void toModelType_specialCharactersInTeamName_throwsIllegalValueException() {
        String[] invalidNames = {"Team@Alpha", "Team#1", "Team$pecial", "Team%Beta", "Team&Gamma"};

        for (String invalidName : invalidNames) {
            JsonAdaptedTeam team = new JsonAdaptedTeam(invalidName, VALID_HACKATHON_NAME, VALID_MEMBERS);
            String expectedMessage = TeamName.MESSAGE_CONSTRAINTS;
            Map<String, Person> nameMap = new HashMap<>();
            assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
        }
    }

    @Test
    public void toModelType_specialCharactersInHackathonName_throwsIllegalValueException() {
        String[] invalidNames = {"Hack@thon", "Hack#1", "Hack$pecial", "Hack%Beta", "Hack&Gamma"};

        for (String invalidName : invalidNames) {
            JsonAdaptedTeam team = new JsonAdaptedTeam(VALID_TEAM_NAME, invalidName, VALID_MEMBERS);
            String expectedMessage = HackathonName.MESSAGE_CONSTRAINTS;
            Map<String, Person> nameMap = new HashMap<>();
            assertThrows(IllegalValueException.class, expectedMessage, () -> team.toModelType(nameMap));
        }
    }
}
