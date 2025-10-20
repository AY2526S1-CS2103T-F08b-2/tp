package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
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
    private Optional<Team> team;
    private boolean isLookingForTeam;
    private Set<HackathonName> interestedHackathons;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        telegram = new Telegram(DEFAULT_TELEGRAM);
        github = new GitHub(DEFAULT_GITHUB);
        skills = new HashSet<>();
        team = Optional.empty();
        isLookingForTeam = false;
        interestedHackathons = new HashSet<>();
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
        team = personToCopy.getTeam();
        isLookingForTeam = personToCopy.isLookingForTeam();
        interestedHackathons = new HashSet<>(personToCopy.getInterestedHackathons());
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
        this.team = Optional.of(team);
        return this;
    }

    /**
     * Sets the {@code isLookingForTeam} status of the {@code Person} that we are building.
     */
    public PersonBuilder withLookingForTeam(boolean isLookingForTeam) {
        this.isLookingForTeam = isLookingForTeam;
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

    public Person build() {
        return new Person(name, email, telegram, github, skills, team, isLookingForTeam, interestedHackathons);
    }

}
