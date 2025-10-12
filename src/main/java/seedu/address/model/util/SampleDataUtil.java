package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.skill.Skill;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Email("alexyeoh@example.com"),
                        new Telegram("alexyeoh"), new GitHub("alexyeoh"), getSkillSet("Java")),

            new Person(new Name("Bernice Yu"), new Email("berniceyu@example.com"),
                        new Telegram("bernicey"), new GitHub("berniceyu"), getSkillSet("Python", "JavaScript")),

            new Person(new Name("Charlotte Oliveiro"), new Email("charlotte@example.com"),
                        new Telegram("charoliveiro"), new GitHub("charlotteoliveiro"), getSkillSet("React")),

            new Person(new Name("David Li"), new Email("lidavid@example.com"),
                        new Telegram("davidli"), new GitHub("davidli"), getSkillSet("C")),

            new Person(new Name("Irfan Ibrahim"), new Email("irfan@example.com"),
                        new Telegram("irfanib"), new GitHub("irfanibrahim"), getSkillSet("NodeJS")),

            new Person(new Name("Roy Balakrishnan"), new Email("royb@example.com"),
                        new Telegram("roybala"), new GitHub("roybalakrishnan"), getSkillSet("Swift"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
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
}
