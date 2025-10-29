package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_TEAM = "Teams list contains duplicate team(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedTeam> teams = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and teams.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("teams") List<JsonAdaptedTeam> teams) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (teams != null) {
            this.teams.addAll(teams);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        // Collect all persons from both the person list and team members to ensure no data loss
        java.util.Set<String> knownPersonNames = new java.util.HashSet<>();

        // Add top-level persons first
        for (Person person : source.getPersonList()) {
            persons.add(new JsonAdaptedPerson(person));
            knownPersonNames.add(person.getName().fullName);
        }

        // Add any persons who are only in teams but not in the top-level person list
        for (Team team : source.getTeamList()) {
            for (Person member : team.getMembers()) {
                if (!knownPersonNames.contains(member.getName().fullName)) {
                    persons.add(new JsonAdaptedPerson(member));
                    knownPersonNames.add(member.getName().fullName);
                }
            }
        }

        // Serialize teams (members will be represented by names)
        teams.addAll(source.getTeamList().stream().map(JsonAdaptedTeam::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        // Load persons first
        // Also build a map from name -> Person so teams can resolve member references
        Map<String, Person> personByName = new HashMap<>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
            personByName.put(person.getName().fullName, person);
        }

        // Load teams after persons (since teams reference persons)
        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType(personByName);
            if (addressBook.hasTeam(team)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEAM);
            }
            addressBook.addTeam(team);
        }

        return addressBook;
    }

}
