package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Additionally, no two persons are allowed to share the same email, telegram handle, or github handle.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list, and must not share email/telegram/github with another person.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd) || hasConflictingContact(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     * Edited person must not introduce a conflict on email/telegram/github with any other person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        // If editedPerson has different identity (name) and matches an existing person by identity, it's a duplicate
        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        // Ensure editedPerson does not conflict on contact fields with any other person (except the target itself)
        for (int i = 0; i < internalList.size(); i++) {
            if (i == index) {
                continue; // skip the target being replaced
            }
            Person other = internalList.get(i);
            if (hasContactConflictBetween(editedPerson, other)) {
                throw new DuplicatePersonException();
            }
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniquePersonList)) {
            return false;
        }

        UniquePersonList otherUniquePersonList = (UniquePersonList) other;
        return internalList.equals(otherUniquePersonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     * Uniqueness is checked by identity (name) and also that no two persons share email, telegram, or github.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                Person a = persons.get(i);
                Person b = persons.get(j);
                if (a.isSamePerson(b) || hasContactConflictBetween(a, b)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the given person would conflict with any existing person in contact fields
     * (email, telegram, github).
     */
    private boolean hasConflictingContact(Person person) {
        for (Person other : internalList) {
            if (hasContactConflictBetween(person, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if two persons conflict on email, telegram, or github.
     */
    private boolean hasContactConflictBetween(Person a, Person b) {
        if (a == null || b == null) {
            return false;
        }
        // If they are the same person identity (name), don't treat as conflict here
        if (a.isSamePerson(b)) {
            return false;
        }

        boolean emailConflict = a.getEmail().equals(b.getEmail());
        boolean telegramConflict = a.getTelegram().equals(b.getTelegram());
        boolean githubConflict = a.getGitHub().equals(b.getGitHub());

        return emailConflict || telegramConflict || githubConflict;
    }
}
