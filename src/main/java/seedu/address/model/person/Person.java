package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.Team;

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
    private final Set<Skill> skills = new HashSet<>();
    private final Optional<Team> team;
    private final boolean isLookingForTeam;
    private final Set<HackathonName> interestedHackathons = new HashSet<>();

    /**
     * Every field must be present and not null. Team is optional.
     */
    // To be removed in future
    public Person(Name name, Email email, Telegram telegram, GitHub github, Set<Skill> skills) {
        this(name, email, telegram, github, skills, Optional.empty(), false, new HashSet<>());
    }

    /**
     * Every field must be present and not null. Team is optional.
     */
    public Person(Name name, Email email, Telegram telegram, GitHub github, Set<Skill> skills,
                  Optional<Team> team, boolean isLookingForTeam, Set<HackathonName> interestedHackathons) {
        requireAllNonNull(name, email, telegram, github, skills, team, isLookingForTeam, interestedHackathons);
        this.name = name;
        this.email = email;
        this.telegram = telegram;
        this.github = github;
        this.skills.addAll(skills);
        this.team = team;
        this.isLookingForTeam = isLookingForTeam;
        this.interestedHackathons.addAll(interestedHackathons);
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
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
     * Returns the team of this person, if any.
     */
    public Optional<Team> getTeam() {
        return team;
    }

    /**
     * Returns whether this person is looking for a team.
     */
    public boolean isLookingForTeam() {
        return isLookingForTeam;
    }

    /**
     * Returns an immutable hackathon set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<HackathonName> getInterestedHackathons() {
        return Collections.unmodifiableSet(this.interestedHackathons);
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
                && telegram.equals(otherPerson.telegram)
                && github.equals(otherPerson.github)
                && skills.equals(otherPerson.skills)
                && team.equals(otherPerson.team)
                && isLookingForTeam == otherPerson.isLookingForTeam
                && interestedHackathons.equals(otherPerson.interestedHackathons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, telegram, github, skills, team, isLookingForTeam, interestedHackathons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("email", email)
                .add("telegram", telegram)
                .add("github", github)
                .add("skills", skills)
                .add("team", team.map(Team::getTeamName).orElse(null))
                .add("lookingForTeam", isLookingForTeam)
                .add("interestedHackathons", interestedHackathons)
                .toString();
    }

    /**
     * Returns a new Person with the given skill removed.
     */
    public Person removeSkill(Skill skill) {
        Set<Skill> updatedSkills = new HashSet<>(skills);
        updatedSkills.remove(skill);
        return new Person(name, email, telegram, github, updatedSkills, team, isLookingForTeam, interestedHackathons);
    }

}
