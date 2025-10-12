package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    private final String teamName;
    private final String hackathonName;
    private final List<JsonAdaptedPerson> members = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("teamName") String teamName,
                           @JsonProperty("hackathonName") String hackathonName,
                           @JsonProperty("members") List<JsonAdaptedPerson> members) {
        this.teamName = teamName;
        this.hackathonName = hackathonName;
        if (members != null) {
            this.members.addAll(members);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        teamName = source.getTeamName().fullTeamName;
        hackathonName = source.getHackathonName() != null ? source.getHackathonName().value : null;
        members.addAll(source.getMembers().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted team.
     */
    public Team toModelType() throws IllegalValueException {
        if (teamName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TeamName.class.getSimpleName()));
        }
        if (!TeamName.isValidTeamName(teamName)) {
            throw new IllegalValueException(TeamName.MESSAGE_CONSTRAINTS);
        }
        final TeamName modelTeamName = new TeamName(teamName);

        final HackathonName modelHackathonName;
        if (hackathonName != null) {
            if (!HackathonName.isValidHackathonName(hackathonName)) {
                throw new IllegalValueException(HackathonName.MESSAGE_CONSTRAINTS);
            }
            modelHackathonName = new HackathonName(hackathonName);
        } else {
            modelHackathonName = null;
        }

        final Set<Person> modelMembers = new HashSet<>();
        for (JsonAdaptedPerson member : members) {
            modelMembers.add(member.toModelType());
        }

        if (modelHackathonName != null) {
            return new Team(modelTeamName, modelHackathonName, modelMembers);
        } else {
            return new Team(modelTeamName, modelMembers);
        }
    }
}
