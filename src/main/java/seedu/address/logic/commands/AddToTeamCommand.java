package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Adds a person to an existing team in the address book.
 */
public class AddToTeamCommand extends Command {

    public static final String COMMAND_WORD = "addToTeam";

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

    private final TeamName teamName;
    private final Index personIndex;

    /**
     * Creates an AddToTeamCommand to add the specified person to the team
     */
    public AddToTeamCommand(TeamName teamName, Index personIndex) {
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

        // Check if person is already in this specific team by checking the person's teams
        boolean isAlreadyInTeam = personToAdd.getTeams().stream()
                .anyMatch(team -> team.getTeamName().equals(teamName));

        if (isAlreadyInTeam) {
            throw new CommandException(
                    String.format(MESSAGE_PERSON_ALREADY_IN_THIS_TEAM,
                            Messages.format(personToAdd), teamName));
        }

        // Use the model's relationship management to add person to team
        // This automatically handles all bidirectional relationship updates
        Team updatedTeam = model.addToTeam(targetTeam, personToAdd);

        // Update the filtered team list to show all teams
        model.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(personToAdd), Messages.format(updatedTeam)),
                false, false, true); // showTeams = true to display teams list
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddToTeamCommand)) {
            return false;
        }

        AddToTeamCommand otherCommand = (AddToTeamCommand) other;
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
