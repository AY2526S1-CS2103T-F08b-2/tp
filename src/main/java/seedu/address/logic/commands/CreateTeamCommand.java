package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Creates a team in the address book.
 */
public class CreateTeamCommand extends Command {

    public static final String COMMAND_WORD = "createTeam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a team in Mate. "
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "Development Team";

    public static final String MESSAGE_SUCCESS = "New team created: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the address book";

    private final Team toCreate;

    /**
     * Creates a CreateTeamCommand to create the specified {@code Team}
     */
    public CreateTeamCommand(Team team) {
        requireNonNull(team);
        toCreate = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeam(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        model.addTeam(toCreate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toCreate)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateTeamCommand)) {
            return false;
        }

        CreateTeamCommand otherCreateTeamCommand = (CreateTeamCommand) other;
        return toCreate.equals(otherCreateTeamCommand.toCreate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toCreate", toCreate)
                .toString();
    }
}
