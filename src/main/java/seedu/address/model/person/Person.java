package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.skill.Skill;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Email email;
    private final Telegram telegram;
    private final GitHub github;

    // Data fields
    private final Address address;
    private final Phone phone;
    private final Set<Skill> skills = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address,
                  Telegram telegram, GitHub github, Set<Skill> skills) {
        requireAllNonNull(name, phone, email, address, telegram, github, skills);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.telegram = telegram;
        this.github = github;
        this.skills.addAll(skills);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public GitHub getGitHub() {
        return github;
    }

    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(this.skills);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && phone.equals(otherPerson.phone)
                && telegram.equals(otherPerson.telegram)
                && github.equals(otherPerson.github)
                && skills.equals(otherPerson.skills);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, telegram, github, skills);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("telegram", telegram)
                .add("github", github)
                .add("skills", skills)
                .toString();
    }

}
