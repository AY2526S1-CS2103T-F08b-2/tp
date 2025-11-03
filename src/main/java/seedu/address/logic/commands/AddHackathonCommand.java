package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Person;

/**
 * Adds interested hackathons to a person identified by index.
 */
public class AddHackathonCommand extends Command {
    public static final String COMMAND_WORD = "addhackathon";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds interested hackathons to the person identified by the index number "
            + "in the displayed person list.\n"
            + "Command is case-insensitive.\n"
            + "Parameters: p/INDEX (must be a positive integer) h/HACKATHON_NAME...\n"
            + "Example: " + COMMAND_WORD + " p/1 h/NUSHack h/iNTUition";

    public static final String MESSAGE_ADD_HACKATHON_SUCCESS = "Added interested hackathon(s) to %1$s";
    public static final String MESSAGE_ALREADY_PARTICIPATING = "Cannot add hackathon '%1$s' to interested list. "
            + "You are already participating in this hackathon.";
    public static final String MESSAGE_ALREADY_INTERESTED = "Hackathon(s) already present in interested list for %1$s";

    private final Index targetIndex;
    private final Set<HackathonName> hackathonsToAdd;

    /**
     * Constructs an AddHackathonCommand to add interested hackathons to a person.
     *
     * @param targetIndex Index of the person in the displayed list (1-based).
     * @param hackathonsToAdd Set of hackathons to add to the person's interested list.
     */
    public AddHackathonCommand(Index targetIndex, Set<HackathonName> hackathonsToAdd) {
        requireNonNull(targetIndex);
        requireNonNull(hackathonsToAdd);
        this.targetIndex = targetIndex;
        this.hackathonsToAdd = hackathonsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        Set<HackathonName> existingInterestedHackathons = personToEdit.getInterestedHackathons();
        Set<HackathonName> participatingHackathons = personToEdit.getParticipatingHackathons();

        // Create updated interested hackathon set
        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(existingInterestedHackathons);

        // Check each hackathon to add
        for (HackathonName hackathonToAdd : hackathonsToAdd) {
            // Check if already participating (case-insensitive)
            for (HackathonName participating : participatingHackathons) {
                if (participating.value.equalsIgnoreCase(hackathonToAdd.value)) {
                    throw new CommandException(String.format(MESSAGE_ALREADY_PARTICIPATING, hackathonToAdd.value));
                }
            }

            // Check if already in interested list (case-insensitive)
            boolean alreadyInterested = false;
            for (HackathonName interested : existingInterestedHackathons) {
                if (interested.value.equalsIgnoreCase(hackathonToAdd.value)) {
                    alreadyInterested = true;
                    break;
                }
            }

            // Only add if not already interested
            if (!alreadyInterested) {
                updatedInterestedHackathons.add(hackathonToAdd);
            }
        }

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getEmail(),
                personToEdit.getTelegram(),
                personToEdit.getGitHub(),
                personToEdit.getSkills(),
                personToEdit.getTeams(),
                updatedInterestedHackathons,
                personToEdit.getParticipatingHackathons()
        );

        model.setPerson(personToEdit, editedPerson);
        // If no new hackathons were added, inform user succinctly
        if (updatedInterestedHackathons.equals(existingInterestedHackathons)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_INTERESTED, editedPerson.getName()));
        }

        return new CommandResult(String.format(MESSAGE_ADD_HACKATHON_SUCCESS, editedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddHackathonCommand)) {
            return false;
        }
        AddHackathonCommand otherCommand = (AddHackathonCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && hackathonsToAdd.equals(otherCommand.hackathonsToAdd);
    }

    @Override
    public String toString() {
        return "AddHackathonCommand{targetIndex=" + targetIndex + ", hackathonsToAdd=" + hackathonsToAdd + "}";
    }
}
