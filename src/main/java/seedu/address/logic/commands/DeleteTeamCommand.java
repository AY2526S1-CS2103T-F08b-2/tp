package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * Deletes a team identified by its team name from the address book.
 * Also removes the team reference from all persons who belong to that team.
 */
public class DeleteTeamCommand extends Command {

    public static final String COMMAND_WORD = "deleteteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the team identified by the team name.\n"
            + "Parameters: " + PREFIX_TEAM_NAME + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TEAM_NAME + "Development Team";

    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted Team: %1$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team with name '%1$s' does not exist";

    private final TeamName targetTeamName;

    public DeleteTeamCommand(TeamName targetTeamName) {
        this.targetTeamName = targetTeamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> lastShownList = model.getFilteredTeamList();

        // Find the target team (case-insensitive match)
        Team teamToDelete = lastShownList.stream()
                .filter(team -> team.getTeamName().toString().equalsIgnoreCase(targetTeamName.toString()))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_TEAM_NOT_FOUND, targetTeamName)));

        // Update all persons who belong to this team
        List<Person> allPersons = model.getAddressBook().getPersonList();
        for (Person person : allPersons) {
            if (person.getTeams().stream().anyMatch(team -> team.isSameTeam(teamToDelete))) {
                // Create a new person with the team removed from their teams set
                Person updatedPerson = person.removeTeam(teamToDelete);

                // Also remove the associated hackathon from participating hackathons if it exists
                if (teamToDelete.getHackathonName() != null) {
                    updatedPerson = updatedPerson.removeParticipatingHackathon(teamToDelete.getHackathonName());
                }

                model.setPerson(person, updatedPerson);
            }
        }

        // Delete the team
        model.deleteTeam(teamToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TEAM_SUCCESS,
                Messages.format(teamToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTeamCommand)) {
            return false;
        }

        DeleteTeamCommand otherDeleteTeamCommand = (DeleteTeamCommand) other;
        return targetTeamName.equals(otherDeleteTeamCommand.targetTeamName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetTeamName", targetTeamName)
                .toString();
    }
}

