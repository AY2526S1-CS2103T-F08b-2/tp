package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.testutil.TeamBuilder;
import seedu.address.testutil.TypicalPersons;

public class CreateTeamCommandTest {

    private final TeamName validTeamName = new TeamName("Alpha Team");
    private final HackathonName validHackathonName = new HackathonName("Tech Challenge 2024");
    private final Set<Index> validIndices = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));

    @Test
    public void constructor_nullTeamName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CreateTeamCommand(null, validHackathonName, validIndices));
    }

    @Test
    public void constructor_nullHackathonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CreateTeamCommand(validTeamName, null, validIndices));
    }

    @Test
    public void constructor_nullPersonIndices_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            new CreateTeamCommand(validTeamName, validHackathonName, null));
    }

    @Test
    public void execute_teamAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded();
        Set<Index> indices = Set.of(Index.fromOneBased(1));

        CommandResult commandResult = new CreateTeamCommand(validTeamName, validHackathonName, indices)
                .execute(modelStub);

        assertEquals(String.format(CreateTeamCommand.MESSAGE_SUCCESS,
                Messages.format(modelStub.teamsAdded.get(0))), commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.teamsAdded.size());
    }

    @Test
    public void execute_duplicateTeam_throwsCommandException() {
        Team validTeam = new TeamBuilder()
                .withTeamName("Alpha Team")
                .withHackathonName("Tech Challenge 2024")
                .withMembers(ALICE)
                .build();
        CreateTeamCommand createTeamCommand = new CreateTeamCommand(validTeamName, validHackathonName,
                Set.of(Index.fromOneBased(1)));
        ModelStub modelStub = new ModelStubWithTeam(validTeam);

        assertThrows(CommandException.class, CreateTeamCommand.MESSAGE_DUPLICATE_TEAM, () ->
            createTeamCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        CreateTeamCommand createTeamCommand = new CreateTeamCommand(validTeamName, validHackathonName,
                Set.of(Index.fromOneBased(10))); // Index out of bounds
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded();

        assertThrows(CommandException.class, CreateTeamCommand.MESSAGE_INVALID_PERSON_INDEX, () ->
            createTeamCommand.execute(modelStub));
    }

    @Test
    public void execute_multipleValidIndices_success() throws Exception {
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded();
        Set<Index> indices = Set.of(Index.fromOneBased(1), Index.fromOneBased(2));

        CommandResult commandResult = new CreateTeamCommand(validTeamName, validHackathonName, indices)
                .execute(modelStub);

        assertEquals(1, modelStub.teamsAdded.size());
        Team createdTeam = modelStub.teamsAdded.get(0);
        assertEquals(2, createdTeam.getSize());
        assertTrue(createdTeam.hasMember(ALICE));
        assertTrue(createdTeam.hasMember(BENSON));
    }

    @Test
    public void execute_emptyPersonIndices_success() throws Exception {
        ModelStubAcceptingTeamAdded modelStub = new ModelStubAcceptingTeamAdded();
        Set<Index> emptyIndices = new HashSet<>();

        CommandResult commandResult = new CreateTeamCommand(validTeamName, validHackathonName, emptyIndices)
                .execute(modelStub);

        assertEquals(1, modelStub.teamsAdded.size());
        Team createdTeam = modelStub.teamsAdded.get(0);
        assertEquals(0, createdTeam.getSize());
    }

    @Test
    public void equals() {
        Set<Index> indicesAlice = Set.of(Index.fromOneBased(1));
        Set<Index> indicesBenson = Set.of(Index.fromOneBased(2));
        CreateTeamCommand createAliceTeamCommand = new CreateTeamCommand(validTeamName,
                validHackathonName,
                indicesAlice);
        CreateTeamCommand createBensonTeamCommand = new CreateTeamCommand(validTeamName,
                validHackathonName,
                indicesBenson);

        // same object -> returns true
        assertTrue(createAliceTeamCommand.equals(createAliceTeamCommand));

        // same values -> returns true
        CreateTeamCommand createAliceTeamCommandCopy = new CreateTeamCommand(validTeamName,
                validHackathonName,
                indicesAlice);
        assertTrue(createAliceTeamCommand.equals(createAliceTeamCommandCopy));

        // different types -> returns false
        assertFalse(createAliceTeamCommand.equals(1));

        // null -> returns false
        assertFalse(createAliceTeamCommand.equals(null));

        // different person indices -> returns false
        assertFalse(createAliceTeamCommand.equals(createBensonTeamCommand));

        // different team name -> returns false
        CreateTeamCommand differentTeamNameCommand = new CreateTeamCommand(new TeamName("Beta Team"),
                validHackathonName, indicesAlice);
        assertFalse(createAliceTeamCommand.equals(differentTeamNameCommand));

        // different hackathon name -> returns false
        CreateTeamCommand differentHackathonCommand = new CreateTeamCommand(validTeamName,
                new HackathonName("Different Hackathon"), indicesAlice);
        assertFalse(createAliceTeamCommand.equals(differentHackathonCommand));
    }

    @Test
    public void toString_validCommand_correctFormat() {
        CreateTeamCommand command = new CreateTeamCommand(validTeamName, validHackathonName, validIndices);
        String expected = "seedu.address.logic.commands.CreateTeamCommand{teamName=Alpha Team, "
                + "hackathonName=Tech Challenge 2024, "
                + "personIndices=" + validIndices
                + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Team addPersonToTeam(Team team, Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Team removePersonFromTeam(Team team, Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single team.
     */
    private class ModelStubWithTeam extends ModelStub {
        private final Team team;

        ModelStubWithTeam(Team team) {
            requireNonNull(team);
            this.team = team;
        }

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return this.team.isSameTeam(team);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return TypicalPersons.getTypicalAddressBook().getPersonList();
        }
    }

    /**
     * A Model stub that always accept the team being added.
     */
    private class ModelStubAcceptingTeamAdded extends ModelStub {
        private final ArrayList<Team> teamsAdded = new ArrayList<>();
        private final ArrayList<Person> personsUpdated = new ArrayList<>();

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return teamsAdded.stream().anyMatch(team::isSameTeam);
        }

        @Override
        public void addTeam(Team team) {
            requireNonNull(team);
            teamsAdded.add(team);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);
            personsUpdated.add(editedPerson);
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            requireNonNull(target);
            requireNonNull(editedTeam);
            // Replace the old team with the updated team
            for (int i = 0; i < teamsAdded.size(); i++) {
                if (teamsAdded.get(i).isSameTeam(target)) {
                    teamsAdded.set(i, editedTeam);
                    break;
                }
            }
        }

        @Override
        public Team addPersonToTeam(Team team, Person person) {
            requireNonNull(team);
            requireNonNull(person);

            // Create updated team with new member (simulate the model's behavior)
            Set<Person> updatedMembers = new HashSet<>(team.getMembers());
            updatedMembers.add(person);
            Team updatedTeam = new Team(team.getTeamName(), team.getHackathonName(), updatedMembers);

            // Update the team in our stub
            setTeam(team, updatedTeam);

            // Update person's teams list (simulate the model's behavior)
            Set<Team> updatedTeams = new HashSet<>(person.getTeams());
            updatedTeams.add(updatedTeam);
            Person updatedPerson = new Person(
                    person.getName(),
                    person.getEmail(),
                    person.getTelegram(),
                    person.getGitHub(),
                    person.getSkills(),
                    updatedTeams,
                    person.isLookingForTeam(),
                    person.getInterestedHackathons()
            );

            setPerson(person, updatedPerson);

            return updatedTeam;
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            // Do nothing for test stub
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return TypicalPersons.getTypicalAddressBook().getPersonList();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
