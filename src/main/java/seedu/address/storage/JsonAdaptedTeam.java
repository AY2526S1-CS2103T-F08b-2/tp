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
    // store members as references (person full names) instead of embedding full person objects
    private final List<String> memberNames = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given team details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("teamName") String teamName,
                           @JsonProperty("hackathonName") String hackathonName,
                           @JsonProperty("members") List<String> members) {
        this.teamName = teamName;
        this.hackathonName = hackathonName;
        if (members != null) {
            this.memberNames.addAll(members);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        teamName = source.getTeamName().fullTeamName;
        hackathonName = source.getHackathonName() != null ? source.getHackathonName().value : null;
        // serialize members as their full name values to avoid duplicating full Person objects
        memberNames.addAll(source.getMembers().stream()
                .map(person -> person.getName().fullName)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * Members are resolved against the provided mapping from name -> Person (populated from the
     * the top-level persons list).
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted team,
     *         or if a referenced member name cannot be found in the provided person map.
     */
    public Team toModelType(java.util.Map<String, Person> personByName) throws IllegalValueException {
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
        for (String name : memberNames) {
            Person member = personByName.get(name);
            if (member == null) {
                // Member reference not found in top-level persons -> treat as invalid data
                throw new IllegalValueException("Team member with name '" + name + "' not found in persons list.");
            }
            modelMembers.add(member);
        }

        if (modelHackathonName != null) {
            return new Team(modelTeamName, modelHackathonName, modelMembers);
        } else {
            return new Team(modelTeamName, modelMembers);
        }
    }
}
