package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Team> filteredTeams;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTeams = new FilteredList<>(this.addressBook.getTeamList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
        // Force UI refresh by temporarily changing predicate
        updateFilteredPersonList(p -> false);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return addressBook.hasTeam(team);
    }

    @Override
    public void deleteTeam(Team target) {
        addressBook.removeTeam(target);
    }

    @Override
    public void addTeam(Team team) {
        addressBook.addTeam(team);
        updateFilteredTeamList(PREDICATE_SHOW_ALL_TEAMS);
    }

    @Override
    public void setTeam(Team target, Team editedTeam) {
        requireAllNonNull(target, editedTeam);

        addressBook.setTeam(target, editedTeam);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Team List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Team} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return filteredTeams;
    }

    @Override
    public void updateFilteredTeamList(Predicate<Team> predicate) {
        requireNonNull(predicate);
        filteredTeams.setPredicate(predicate);
    }

    //=========== Team-Person Relationship Management ========================================================

    /**
     * Checks if a person is already in a team for the given hackathon.
     */
    @Override
    public boolean isPersonInHackathon(Person person, HackathonName hackathonName) {
        requireAllNonNull(person, hackathonName);
        ObservableList<Team> teams = addressBook.getTeamList();

        // Log the person and hackathon name being checked
        logger.info("Checking if person " + person.getName() + " is in hackathon " + hackathonName);

        // Log the list of teams
        logger.info("Teams in address book: " + addressBook.getTeamList());

        for (Team team : teams) {
            String teamNameStr = String.valueOf(team.getTeamName());
            HackathonName teamHackathon = team.getHackathonName();
            boolean hasMember = team.hasMember(person);
            boolean hackathonMatches = java.util.Objects.equals(teamHackathon, hackathonName);

            logger.info("Team: " + teamNameStr
                    + " | Hackathon: " + teamHackathon
                    + " | hasMember: " + hasMember
                    + " | hackathonMatches: " + hackathonMatches);

            if (hasMember && hackathonMatches) {
                logger.info("Found matching team: " + teamNameStr + " for hackathon: " + hackathonName);
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a person to a team, maintaining bidirectional relationship.
     * Updates both the team's member list and the person's team list.
     * Also updates the person's participatingHackathons to include the team's
     * hackathon, and removes it from interestedHackathons if present.
     *
     * @param team The team to add the person to
     * @param person The person to add to the team
     * @return Updated team with the new member
     */
    @Override
    public Team addPersonToTeam(Team team, Person person) {
        requireAllNonNull(team, person);

        // Create updated team with new member
        Set<Person> updatedMembers = new HashSet<>(team.getMembers());
        updatedMembers.add(person);
        Team updatedTeam = new Team(team.getTeamName(), team.getHackathonName(), updatedMembers);

        // Update the team in the model
        setTeam(team, updatedTeam);

        // Update person's teams list
        Set<Team> updatedTeams = new HashSet<>(person.getTeams());
        updatedTeams.add(updatedTeam);

        // Update person's interestedHackathons - remove the team's hackathon if present
        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(person.getInterestedHackathons());
        if (team.getHackathonName() != null) {
            updatedInterestedHackathons.remove(team.getHackathonName());
        }

        // Update person's participatingHackathons to include the team's hackathon
        Set<HackathonName> updatedParticipatingHackathons = new HashSet<>(person.getParticipatingHackathons());
        if (team.getHackathonName() != null) {
            updatedParticipatingHackathons.add(team.getHackathonName());
        }

        Person updatedPerson = new Person(
                person.getName(),
                person.getEmail(),
                person.getTelegram(),
                person.getGitHub(),
                person.getSkills(),
                updatedTeams,
                updatedInterestedHackathons,
                updatedParticipatingHackathons
        );

        // Update the person in the model
        setPerson(person, updatedPerson);

        return updatedTeam;
    }

    /**
     * Removes a person from a team. Updates both the team's member list and the person's team list.
     * If the team has an associated hackathon, removes it from the person's participating hackathons
     * and adds it back to interested hackathons.
     *
     * @param team The team to remove the person from
     * @param person The person to remove from the team
     * @return Updated team without the person
     */
    @Override
    public Team removePersonFromTeam(Team team, Person person) {
        requireAllNonNull(team, person);

        // Create updated team without the person
        Set<Person> updatedMembers = new HashSet<>(team.getMembers());
        // remove by identity to handle different instances representing
        // the same person
        updatedMembers.removeIf(member -> member.isSamePerson(person));
        Team updatedTeam = new Team(team.getTeamName(), team.getHackathonName(), updatedMembers);

        // Update the team in the model
        setTeam(team, updatedTeam);

        // Update person's teams list
        Set<Team> updatedTeams = new HashSet<>(person.getTeams());
        // remove team by identity (isSameTeam) to handle different instances
        updatedTeams.removeIf(t -> t.isSameTeam(team));

        // Update person's hackathons - remove from participating and add back to interested
        Set<HackathonName> updatedParticipatingHackathons = new HashSet<>(person.getParticipatingHackathons());
        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(person.getInterestedHackathons());

        if (team.getHackathonName() != null) {
            // Remove the team's hackathon from participating hackathons
            updatedParticipatingHackathons.remove(team.getHackathonName());
            // Add it back to interested hackathons (they were participating, so they must have been interested)
            updatedInterestedHackathons.add(team.getHackathonName());
        }

        Person updatedPerson = new Person(
                person.getName(),
                person.getEmail(),
                person.getTelegram(),
                person.getGitHub(),
                person.getSkills(),
                updatedTeams,
                updatedInterestedHackathons,
                updatedParticipatingHackathons
        );

        // Update the person in the model
        setPerson(person, updatedPerson);

        return updatedTeam;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredTeams.equals(otherModelManager.filteredTeams);
    }

}
