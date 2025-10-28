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
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();

        // Create all persons with both interested and participating hackathons
        Person alex = new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                new Telegram("alexyeoh"), new GitHub("alexyeoh"),
                getSkillSet(new Skill("java", ExperienceLevel.INTERMEDIATE),
                            new Skill("python", ExperienceLevel.BEGINNER),
                            new Skill("git", ExperienceLevel.INTERMEDIATE),
                            new Skill("mongodb", ExperienceLevel.BEGINNER),
                            new Skill("spring", ExperienceLevel.INTERMEDIATE),
                            new Skill("mysql", ExperienceLevel.INTERMEDIATE),
                            new Skill("rest", ExperienceLevel.INTERMEDIATE),
                            new Skill("junit", ExperienceLevel.BEGINNER),
                            new Skill("maven", ExperienceLevel.BEGINNER),
                            new Skill("hibernate", ExperienceLevel.BEGINNER),
                            new Skill("postman", ExperienceLevel.INTERMEDIATE),
                            new Skill("tensorflow", ExperienceLevel.BEGINNER)),
                new HashSet<>(), getHackathonSet("NUSHacks", "AI Challenge 2024"),
                getHackathonSet());

        Person bernice = new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                new Telegram("bernicey"), new GitHub("berniceyu"),
                getSkillSet(new Skill("python", ExperienceLevel.ADVANCED),
                            new Skill("javascript", ExperienceLevel.ADVANCED),
                            new Skill("django", ExperienceLevel.INTERMEDIATE),
                            new Skill("postgresql", ExperienceLevel.INTERMEDIATE),
                            new Skill("docker", ExperienceLevel.BEGINNER),
                            new Skill("git", ExperienceLevel.ADVANCED),
                            new Skill("flask", ExperienceLevel.ADVANCED),
                            new Skill("redis", ExperienceLevel.INTERMEDIATE),
                            new Skill("numpy", ExperienceLevel.ADVANCED),
                            new Skill("pandas", ExperienceLevel.ADVANCED),
                            new Skill("scikit", ExperienceLevel.INTERMEDIATE),
                            new Skill("pytest", ExperienceLevel.INTERMEDIATE),
                            new Skill("kubernetes", ExperienceLevel.BEGINNER),
                            new Skill("graphql", ExperienceLevel.INTERMEDIATE),
                            new Skill("celery", ExperienceLevel.BEGINNER)),
                new HashSet<>(), getHackathonSet("Web Dev Contest"),
                getHackathonSet());

        Person charlotte = new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                new Telegram("charoliveiro"), new GitHub("charlotteoliveiro"),
                getSkillSet(new Skill("react", ExperienceLevel.ADVANCED),
                            new Skill("typescript", ExperienceLevel.ADVANCED),
                            new Skill("nodejs", ExperienceLevel.INTERMEDIATE),
                            new Skill("css", ExperienceLevel.ADVANCED),
                            new Skill("git", ExperienceLevel.INTERMEDIATE),
                            new Skill("firebase", ExperienceLevel.BEGINNER),
                            new Skill("nextjs", ExperienceLevel.ADVANCED),
                            new Skill("tailwind", ExperienceLevel.ADVANCED),
                            new Skill("redux", ExperienceLevel.INTERMEDIATE),
                            new Skill("webpack", ExperienceLevel.INTERMEDIATE),
                            new Skill("jest", ExperienceLevel.INTERMEDIATE),
                            new Skill("figma", ExperienceLevel.ADVANCED),
                            new Skill("html", ExperienceLevel.ADVANCED),
                            new Skill("sass", ExperienceLevel.ADVANCED),
                            new Skill("vite", ExperienceLevel.INTERMEDIATE)),
                new HashSet<>(), getHackathonSet("Mobile App Hackathon", "Innovation Day"),
                getHackathonSet());

        Person david = new Person(new Name("David Li"), new Email("lidavid@example.com"),
                new Telegram("davidli"), new GitHub("davidli"),
                getSkillSet(new Skill("c++", ExperienceLevel.INTERMEDIATE),
                            new Skill("c", ExperienceLevel.ADVANCED),
                            new Skill("rust", ExperienceLevel.BEGINNER),
                            new Skill("git", ExperienceLevel.INTERMEDIATE),
                            new Skill("linux", ExperienceLevel.INTERMEDIATE),
                            new Skill("go", ExperienceLevel.INTERMEDIATE),
                            new Skill("bash", ExperienceLevel.ADVANCED),
                            new Skill("cmake", ExperienceLevel.INTERMEDIATE),
                            new Skill("opengl", ExperienceLevel.BEGINNER),
                            new Skill("assembly", ExperienceLevel.BEGINNER),
                            new Skill("cuda", ExperienceLevel.BEGINNER),
                            new Skill("grpc", ExperienceLevel.BEGINNER)),
                new HashSet<>(), getHackathonSet("Backend Challenge"),
                getHackathonSet());

        Person irfan = new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                new Telegram("irfanib"), new GitHub("irfanibrahim"),
                getSkillSet(new Skill("nodejs", ExperienceLevel.ADVANCED),
                            new Skill("javascript", ExperienceLevel.ADVANCED),
                            new Skill("express", ExperienceLevel.INTERMEDIATE),
                            new Skill("mongodb", ExperienceLevel.INTERMEDIATE),
                            new Skill("aws", ExperienceLevel.BEGINNER),
                            new Skill("git", ExperienceLevel.ADVANCED),
                            new Skill("nestjs", ExperienceLevel.INTERMEDIATE),
                            new Skill("socketio", ExperienceLevel.ADVANCED),
                            new Skill("jwt", ExperienceLevel.INTERMEDIATE),
                            new Skill("oauth", ExperienceLevel.INTERMEDIATE),
                            new Skill("nginx", ExperienceLevel.BEGINNER),
                            new Skill("jenkins", ExperienceLevel.BEGINNER),
                            new Skill("mocha", ExperienceLevel.INTERMEDIATE),
                            new Skill("stripe", ExperienceLevel.BEGINNER),
                            new Skill("rabbitmq", ExperienceLevel.BEGINNER)),
                new HashSet<>(), getHackathonSet("NUSHacks", "Backend Challenge"),
                getHackathonSet());

        Person roy = new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                new Telegram("roybala"), new GitHub("roybalakrishnan"),
                getSkillSet(new Skill("swift", ExperienceLevel.ADVANCED),
                            new Skill("kotlin", ExperienceLevel.INTERMEDIATE),
                            new Skill("ios", ExperienceLevel.ADVANCED),
                            new Skill("firebase", ExperienceLevel.INTERMEDIATE),
                            new Skill("git", ExperienceLevel.ADVANCED),
                            new Skill("android", ExperienceLevel.INTERMEDIATE),
                            new Skill("flutter", ExperienceLevel.ADVANCED),
                            new Skill("dart", ExperienceLevel.ADVANCED),
                            new Skill("swiftui", ExperienceLevel.ADVANCED),
                            new Skill("xcode", ExperienceLevel.ADVANCED),
                            new Skill("realm", ExperienceLevel.INTERMEDIATE),
                            new Skill("sqlite", ExperienceLevel.INTERMEDIATE),
                            new Skill("jetpack", ExperienceLevel.BEGINNER),
                            new Skill("arkit", ExperienceLevel.BEGINNER)),
                new HashSet<>(), getHackathonSet("Mobile App Hackathon"),
                getHackathonSet());

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
     * Returns a skill set containing the list of skills given.
     */
    public static Set<Skill> getSkillSet(Skill... skills) {
        return new HashSet<>(Arrays.asList(skills));
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
