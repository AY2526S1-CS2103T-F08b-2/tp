package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Removes a person from an existing team in the address book.
 */
public class RemovePersonFromTeamCommand extends Command {

    // =========================
    // ===== Static Fields =====
    // =========================

    public static final String COMMAND_WORD = "removepersonfromteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from an existing team. "
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_PERSON + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "Development Team "
            + PREFIX_PERSON + "1";

    public static final String MESSAGE_SUCCESS = "Person %1$s removed from team: %2$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%1$s' does not exist";
    public static final String MESSAGE_PERSON_NOT_IN_TEAM = "Person %1$s is not in team %2$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid";

    private static final Logger logger = LogsCenter.getLogger(RemovePersonFromTeamCommand.class);

    // ===========================
    // ===== Instance Fields =====
    // ===========================

    private final TeamName teamName;
    private final Index personIndex;

    /**
     * Creates a RemovePersonFromTeamCommand to remove the specified person from the team.
     */
    public RemovePersonFromTeamCommand(TeamName teamName, Index personIndex) {
        requireNonNull(teamName);
        requireNonNull(personIndex);
        this.teamName = teamName;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.fine("Executing RemovePersonFromTeamCommand: teamName=" + teamName + ", personIndex=" + personIndex);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        List<Team> teamList = model.getFilteredTeamList();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            logger.log(Level.WARNING, "Invalid person index: {0}. List size: {1}",
                    new Object[]{personIndex.getOneBased(), lastShownPersonList.size()});
            throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
        }

        Person personToRemove = lastShownPersonList.get(personIndex.getZeroBased());
        logger.fine("Person to remove resolved: " + personToRemove.getName());

        Team targetTeam = teamList.stream()
                .filter(team -> team.getTeamName().toString().equalsIgnoreCase(teamName.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TEAM_NOT_FOUND, teamName)));

        boolean isInTeam = personToRemove.getTeams().stream()
                .anyMatch(team -> team.getTeamName().toString().equalsIgnoreCase(teamName.toString()));

        if (!isInTeam) {
            logger.log(Level.WARNING, "Person {0} is not in team {1}",
                    new Object[]{personToRemove.getName(), teamName});
            throw new CommandException(
                    String.format(MESSAGE_PERSON_NOT_IN_TEAM,
                            personToRemove.getName(), teamName));
        }

        Team updatedTeam = model.removePersonFromTeam(targetTeam, personToRemove);
        model.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        logger.info("Removed person " + personToRemove.getName()
                + " from team " + teamName + ". Updated team: " + updatedTeam.getTeamName());

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToRemove.getName(), updatedTeam.getTeamName()),
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemovePersonFromTeamCommand)) {
            return false;
        }

        RemovePersonFromTeamCommand otherCommand = (RemovePersonFromTeamCommand) other;
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
