package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Team> PREDICATE_SHOW_ALL_TEAMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a team with the same identity as {@code team} exists in Mate
     */
    boolean hasTeam(Team team);

    /**
     * Deletes the given team.
     * The team must exist in Mate.
     */
    void deleteTeam(Team target);

    /**
     * Adds the given team.
     * {@code team} must not already exist in Mate.
     */
    void addTeam(Team team);

    /**
     * Replaces the given team {@code target} with {@code editedTeam}.
     * {@code target} must exist in Mate.
     * The team identity of {@code editedTeam} must not be the same as another existing team in Mate.
     */
    void setTeam(Team target, Team editedTeam);

    /** Returns an unmodifiable view of the filtered team list */
    ObservableList<Team> getFilteredTeamList();

    /**
     * Updates the filter of the filtered team list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTeamList(Predicate<Team> predicate);

    /**
     * Adds a person to a team, maintaining bidirectional relationship.
     * Updates both the team's member list and the person's team list.
     *
     * @param team The team to add the person to
     * @param person The person to add to the team
     * @return Updated team with the new member
     */
    Team addToTeam(Team team, Person person);

    /**
     * Removes a person from a team, maintaining bidirectional relationship.
     * Updates both the team's member list and the person's team list.
     *
     * @param team The team to remove the person from
     * @param person The person to remove from the team
     * @return Updated team without the person
     */
    Team removePersonFromTeam(Team team, Person person);

}
