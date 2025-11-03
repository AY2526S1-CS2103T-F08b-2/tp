package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;

/**
 * Removes hackathons from a person's interested hackathon list identified by index.
 */
public class RemoveHackathonCommand extends Command {
    public static final String COMMAND_WORD = "removehackathon";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified hackathons from the person's interested hackathon list"
            + " identified by the index number in the displayed person list.\n"
            + "Command is case-insensitive.\n"
            + "Parameters: p/INDEX (must be a positive integer) h/HACKATHON_NAME [h/HACKATHON_NAME]...\n"
            + "Example: " + COMMAND_WORD + " p/1 h/NUSHack h/TechChallenge";

    public static final String MESSAGE_DELETE_HACKATHON_SUCCESS = "Removed hackathon(s) from Person: %1$s";
    public static final String MESSAGE_HACKATHON_NOT_FOUND = "Hackathon '%1$s' not found in "
            + "interested list for Person: %2$s";
    public static final String MESSAGE_HACKATHON_IN_PARTICIPATING = "Cannot remove hackathon '%1$s' "
           + "because you are currently participating in it. Use removePersonFromTeam or deleteTeam first.";
    public static final String MESSAGE_NO_HACKATHONS_PROVIDED = "At least one hackathon must be specified for removal.";

    private static final Logger logger = LogsCenter.getLogger(RemoveHackathonCommand.class);

    private final Index targetIndex;
    private final Set<HackathonName> hackathonsToDelete;

    /**
     * Constructs a RemoveHackathonCommand to delete hackathons from a person's interested list.
     *
     * @param targetIndex Index of the person in the displayed list (1-based).
     * @param hackathonsToDelete Set of hackathon names to delete from the person's interested list.
     */
    public RemoveHackathonCommand(Index targetIndex, Set<HackathonName> hackathonsToDelete) {
        requireNonNull(targetIndex);
        requireNonNull(hackathonsToDelete);

        this.targetIndex = targetIndex;
        this.hackathonsToDelete = hackathonsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (hackathonsToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_NO_HACKATHONS_PROVIDED);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Person editedPerson = personToEdit;

        // Check if any hackathons are in participating list
        for (HackathonName hackathon : hackathonsToDelete) {
            if (personToEdit.getParticipatingHackathons().contains(hackathon)) {
                logger.warning("Attempted to delete participating hackathon: " + hackathon.value);
                throw new CommandException(String.format(MESSAGE_HACKATHON_IN_PARTICIPATING,
                        hackathon.value));
            }
        }

        // Remove each hackathon from interested list
        for (HackathonName hackathon : hackathonsToDelete) {
            if (!editedPerson.getInterestedHackathons().contains(hackathon)) {
                throw new CommandException(String.format(MESSAGE_HACKATHON_NOT_FOUND,
                        hackathon.value, Messages.format(personToEdit)));
            }
            editedPerson = editedPerson.removeInterestedHackathon(hackathon);
        }

        model.setPerson(personToEdit, editedPerson);
        logger.info("Successfully removed " + hackathonsToDelete.size() + " hackathon(s) from person at index "
                + targetIndex.getOneBased());

        return new CommandResult(String.format(MESSAGE_DELETE_HACKATHON_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveHackathonCommand)) {
            return false;
        }

        RemoveHackathonCommand otherCommand = (RemoveHackathonCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && hackathonsToDelete.equals(otherCommand.hackathonsToDelete);
    }

    @Override
    public String toString() {
        return "RemoveHackathonCommand{targetIndex=" + targetIndex
                + ", hackathonsToDelete=" + hackathonsToDelete + "}";
    }
}

