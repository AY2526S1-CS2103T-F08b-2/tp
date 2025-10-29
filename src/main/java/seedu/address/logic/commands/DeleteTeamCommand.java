package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Deletes a team identified using its displayed index from the address book.
 * Also removes the team reference from all persons who belong to that team.
 */
public class DeleteTeamCommand extends Command {

    public static final String COMMAND_WORD = "deleteteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the team identified by the index number used in the displayed team list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TEAM_SUCCESS = "Deleted Team: %1$s";

    private final Index targetIndex;

    public DeleteTeamCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Team> lastShownList = model.getFilteredTeamList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }

        Team teamToDelete = lastShownList.get(targetIndex.getZeroBased());

        // Update all persons who belong to this team
        List<Person> allPersons = model.getAddressBook().getPersonList();
        for (Person person : allPersons) {
            if (person.getTeams().stream().anyMatch(team -> team.isSameTeam(teamToDelete))) {
                // Create a new person with the team removed from their teams set
                Person updatedPerson = person.removeTeam(teamToDelete);
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
        return targetIndex.equals(otherDeleteTeamCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

