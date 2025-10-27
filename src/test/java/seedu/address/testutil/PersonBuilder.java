package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.Team;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TELEGRAM = "amybee_tg";
    public static final String DEFAULT_GITHUB = "amybee-github";

    private Name name;
    private Email email;
    private Telegram telegram;
    private GitHub github;
    private Set<Skill> skills;
    private Set<Team> teams;
    private Set<HackathonName> interestedHackathons;
    private Set<HackathonName> participatingHackathons;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        github = new GitHub(DEFAULT_GITHUB);
        skills = new HashSet<>();
        teams = new HashSet<>();
        interestedHackathons = new HashSet<>();
        participatingHackathons = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        email = personToCopy.getEmail();
        telegram = personToCopy.getTelegram();
        github = personToCopy.getGitHub();
        skills = new HashSet<>(personToCopy.getSkills());
        teams = new HashSet<>(personToCopy.getTeams());
        interestedHackathons = new HashSet<>(personToCopy.getInterestedHackathons());
        participatingHackathons = new HashSet<>(personToCopy.getParticipatingHackathons());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        this.telegram = new Telegram(telegram);
        return this;
    }

    /**
     * Sets the {@code GitHub} of the {@code Person} that we are building.
     */
    public PersonBuilder withGitHub(String github) {
        this.github = new GitHub(github);
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeam(Team team) {
        this.teams.add(team);
        return this;
    }

    /**
     * Parses the {@code hackathons} into a {@code Set<HackathonName>} and set it to the {@code Person}
     * that we are building.
     */
    public PersonBuilder withInterestedHackathons(String ... hackathons) {
        this.interestedHackathons = new HashSet<>();
        for (String hackathon : hackathons) {
            this.interestedHackathons.add(new HackathonName(hackathon));
        }
        return this;
    }

    /**
     * Parses the {@code hackathons} into a {@code Set<HackathonName>} and set it as the current hackathons
     * that the {@code Person} is participating in.
     */
    public PersonBuilder withParticipatingHackathons(String ... hackathons) {
        this.participatingHackathons = new HashSet<>();
        for (String hackathon : hackathons) {
            this.participatingHackathons.add(new HackathonName(hackathon));
        }
        return this;
    }

    /**
     * Builds and returns a {@code Person} object with the current builder's field values.
     *
     * @return A new Person object constructed with the builder's current field values.
     */
    public Person build() {
        return new Person(name, email, telegram, github, skills, teams,
                interestedHackathons, participatingHackathons);
    }

}
