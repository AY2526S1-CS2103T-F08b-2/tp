package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Adds a person to an existing team in the address book.
 */
public class AddPersonToTeamCommand extends Command {
    public static final String COMMAND_WORD = "addPersonToTeam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to an existing team. "
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_PERSON + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "Development Team "
            + PREFIX_PERSON + "1";

    public static final String MESSAGE_SUCCESS = "Person %1$s added to team: %2$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%1$s' does not exist";
    public static final String MESSAGE_PERSON_ALREADY_IN_TEAM = "Person %1$s is already in a team";
    public static final String MESSAGE_PERSON_ALREADY_IN_THIS_TEAM = "Person %1$s is already in team %2$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid";

    private static final Logger logger = Logger.getLogger(AddPersonToTeamCommand.class.getName());

    private final TeamName teamName;
    private final Index personIndex;

    /**
     * Creates an AddPersonToTeamCommand to add the specified person to the team
     */
    public AddPersonToTeamCommand(TeamName teamName, Index personIndex) {
        requireNonNull(teamName);
        requireNonNull(personIndex);
        this.teamName = teamName;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Team> teamList = model.getFilteredTeamList();

        // Validate person index
        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }


        // Get the person to add
        Person personToAdd = lastShownPersonList.get(personIndex.getZeroBased());

        // Find the target team
        Team targetTeam = teamList.stream()
                .filter(team -> team.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TEAM_NOT_FOUND, teamName)));

        logger.info("Person is in teams : " + personToAdd.getTeams().toString());

        // Check if person is already in this specific team by checking the person's teams
        boolean isAlreadyInTeam = personToAdd.getTeams().stream()
                .anyMatch(team -> team.getTeamName().equals(teamName));

        if (isAlreadyInTeam) {
            throw new CommandException(
                    String.format(MESSAGE_PERSON_ALREADY_IN_THIS_TEAM,
                            personToAdd.getName(), teamName));
        }

        // Check if person is already in any team for the same hackathon

        HackathonName hackathonName = targetTeam.getHackathonName();

        if (model.isPersonInHackathon(personToAdd, hackathonName)) {
            throw new CommandException(String.format("Person %s is already in a team for hackathon %s",
                    personToAdd.getName(), hackathonName));

        }


        // Use the model's relationship management to add person to team
        // This automatically handles all bidirectional relationship updates
        Team updatedTeam = model.addPersonToTeam(targetTeam, personToAdd);

        // Update the filtered team list to show all teams
        model.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToAdd.getName(), updatedTeam.getTeamName()),
                false, false, true); // showTeams = true to display teams list
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPersonToTeamCommand)) {
            return false;
        }

        AddPersonToTeamCommand otherCommand = (AddPersonToTeamCommand) other;
        return teamName.equals(otherCommand.teamName)
                && personIndex.equals(otherCommand.personIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .add("personIndex", personIndex)
                .toString();
    }
}
