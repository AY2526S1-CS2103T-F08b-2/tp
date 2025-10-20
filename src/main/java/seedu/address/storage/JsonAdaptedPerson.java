package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String email;
    private final String telegram;
    private final String github;
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    private final String teamName;
    private final boolean isLookingForTeam;
    private final List<String> interestedHackathons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("email") String email,
                             @JsonProperty("telegram") String telegram,
                             @JsonProperty("github") String github,
                             @JsonProperty("skills") List<JsonAdaptedSkill> skills,
                             @JsonProperty("teamName") String teamName,
                             @JsonProperty("isLookingForTeam") boolean isLookingForTeam,
                             @JsonProperty("interestedHackathons") List<String> interestedHackathons) {
        this.name = name;
        this.email = email;
        this.telegram = telegram;
        this.github = github;
        if (skills != null) {
            this.skills.addAll(skills);
        }
        this.teamName = teamName;
        this.isLookingForTeam = isLookingForTeam;
        if (interestedHackathons != null) {
            this.interestedHackathons.addAll(interestedHackathons);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        email = source.getEmail().value;
        telegram = source.getTelegram().toString();
        github = source.getGitHub().toString();
        skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
        teamName = source.getTeam().map(team -> team.getTeamName().toString()).orElse(null);
        isLookingForTeam = source.isLookingForTeam();
        interestedHackathons.addAll(source.getInterestedHackathons().stream()
                .map(HackathonName::toString)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Skill> personSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skills) {
            personSkills.add(skill.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (telegram == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Telegram.class.getSimpleName()));
        }
        if (!Telegram.isValidTelegram(telegram)) {
            throw new IllegalValueException(Telegram.MESSAGE_CONSTRAINTS);
        }
        final Telegram modelTelegram = new Telegram(telegram);

        if (github == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, GitHub.class.getSimpleName()));
        }
        if (!GitHub.isValidGitHub(github)) {
            throw new IllegalValueException(GitHub.MESSAGE_CONSTRAINTS);
        }
        final GitHub modelGitHub = new GitHub(github);

        final Set<Skill> modelSkills = new HashSet<>(personSkills);

        final Optional<Team> modelTeam;
        if (teamName != null && !teamName.isEmpty()) {
            if (!TeamName.isValidTeamName(teamName)) {
                throw new IllegalValueException(TeamName.MESSAGE_CONSTRAINTS);
            }
            modelTeam = Optional.of(new Team(new TeamName(teamName)));
        } else {
            modelTeam = Optional.empty();
        }

        final Set<HackathonName> modelHackathons = new HashSet<>();
        for (String hackathon : interestedHackathons) {
            if (!HackathonName.isValidHackathonName(hackathon)) {
                throw new IllegalValueException(HackathonName.MESSAGE_CONSTRAINTS);
            }
            modelHackathons.add(new HackathonName(hackathon));
        }

        return new Person(modelName, modelEmail, modelTelegram, modelGitHub, modelSkills,
                modelTeam, isLookingForTeam, modelHackathons);
    }
}
