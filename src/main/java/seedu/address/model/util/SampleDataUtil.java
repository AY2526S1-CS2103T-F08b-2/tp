package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                        new Telegram("alexyeoh"), new GitHub("alexyeoh"), getSkillSet("java")),

            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                        new Telegram("bernicey"), new GitHub("berniceyu"), getSkillSet("python", "javascript")),

            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                        new Telegram("charoliveiro"), new GitHub("charlotteoliveiro"), getSkillSet("react")),

            new Person(new Name("David Li"), new Email("lidavid@example.com"),
                        new Telegram("davidli"), new GitHub("davidli"), getSkillSet("c")),

            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                        new Telegram("irfanib"), new GitHub("irfanibrahim"), getSkillSet("nodejs")),

            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                        new Telegram("roybala"), new GitHub("roybalakrishnan"), getSkillSet("swift"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        // Create all persons
        Person alex = new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                new Telegram("alexyeoh"), new GitHub("alexyeoh"), getSkillSet("java"),
                new HashSet<>(), getHackathonSet("NUSHacks", "AI Challenge 2024"));

        Person bernice = new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                new Telegram("bernicey"), new GitHub("berniceyu"), getSkillSet("python", "javascript"),
                new HashSet<>(), getHackathonSet("Web Dev Contest"));

        Person charlotte = new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                new Telegram("charoliveiro"), new GitHub("charlotteoliveiro"), getSkillSet("react"),
                new HashSet<>(), getHackathonSet("Mobile App Hackathon", "Innovation Day"));

        Person david = new Person(new Name("David Li"), new Email("lidavid@example.com"),
                new Telegram("davidli"), new GitHub("davidli"), getSkillSet("c"),
                new HashSet<>(), getHackathonSet("Backend Challenge"));

        Person irfan = new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                new Telegram("irfanib"), new GitHub("irfanibrahim"), getSkillSet("nodejs"),
                new HashSet<>(), getHackathonSet("NUSHacks", "Backend Challenge"));

        Person roy = new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                new Telegram("roybala"), new GitHub("roybalakrishnan"), getSkillSet("swift"),
                new HashSet<>(), getHackathonSet("Mobile App Hackathon"));

        // Add all persons first
        sampleAb.addPerson(alex);
        sampleAb.addPerson(bernice);
        sampleAb.addPerson(charlotte);
        sampleAb.addPerson(david);
        sampleAb.addPerson(irfan);
        sampleAb.addPerson(roy);

        // Create all teams empty initially
        Team techInnovators = new Team(new TeamName("Tech Innovators"),
                new HackathonName("AI Challenge 2024"), new HashSet<>());
        Team codeMasters = new Team(new TeamName("Code Masters"),
                new HackathonName("Web Dev Contest"), new HashSet<>());
        Team digitalPioneers = new Team(new TeamName("Digital Pioneers"),
                new HackathonName("Mobile App Hackathon"), new HashSet<>());
        Team soloDevelopers = new Team(new TeamName("Solo Developers"),
                new HackathonName("Backend Challenge"), new HashSet<>());
        Team openTeam = new Team(new TeamName("Open Team"),
                new HackathonName("Innovation Day"), new HashSet<>());

        // Add all empty teams first
        sampleAb.addTeam(techInnovators);
        sampleAb.addTeam(codeMasters);
        sampleAb.addTeam(digitalPioneers);
        sampleAb.addTeam(soloDevelopers);
        sampleAb.addTeam(openTeam);

        return sampleAb;
    }

    /**
     * Returns a skill set containing the list of strings given.
     */
    public static Set<Skill> getSkillSet(String... strings) {
        return Arrays.stream(strings)
                .map(Skill::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a hackathon set containing the list of strings given.
     */
    public static Set<HackathonName> getHackathonSet(String... strings) {
        return Arrays.stream(strings)
                .map(HackathonName::new)
                .collect(Collectors.toSet());
    }
}
