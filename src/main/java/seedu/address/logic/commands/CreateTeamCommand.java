package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Creates a team in the address book.
 */
public class CreateTeamCommand extends Command {

    public static final String COMMAND_WORD = "createteam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a team in Mate. "
            + "Parameters: "
            + PREFIX_TEAM_NAME + "TEAM_NAME "
            + PREFIX_HACKATHON + "HACKATHON_NAME "
            + PREFIX_PERSON + "INDEX "
            + "[" + PREFIX_PERSON + "INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TEAM_NAME + "Development Team "
            + PREFIX_HACKATHON + "Hackathon 2023 "
            + PREFIX_PERSON + "1 "
            + PREFIX_PERSON + "3";

    public static final String MESSAGE_SUCCESS = "New team created: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the address book";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid";

    private final TeamName teamName;
    private final HackathonName hackathonName;
    private final Set<Index> personIndices;

    /**
     * Creates a CreateTeamCommand to create the specified team with hackathon and members
     */
    public CreateTeamCommand(TeamName teamName, HackathonName hackathonName, Set<Index> personIndices) {
        requireNonNull(teamName);
        requireNonNull(hackathonName);
        requireNonNull(personIndices);
        this.teamName = teamName;
        this.hackathonName = hackathonName;
        this.personIndices = personIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        // Validate all person indices
        for (Index index : personIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_INDEX);
            }
        }

        // Get persons from indices
        Set<Person> members = new java.util.HashSet<>();
        for (Index index : personIndices) {
            Person person = lastShownList.get(index.getZeroBased());
            members.add(person);
        }

        // Create team with empty members initially
        Team toCreate = new Team(teamName, hackathonName, new HashSet<>());

        if (model.hasTeam(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        // Check if any person is already in a team for the same hackathon
        for (Person person : members) {
            if (model.isPersonInHackathon(person, hackathonName)) {
                throw new CommandException(String.format("Person %s is already in a team for hackathon %s",
                        person.getName(), hackathonName));
            }
        }

        // Add the empty team to the model first
        model.addTeam(toCreate);

        // Use the model's relationship management methods to add each member
        // This automatically handles all bidirectional relationship updates
        for (Person member : members) {
            toCreate = model.addToTeam(toCreate, member);
        }

        // Update the filtered team list to show all teams
        model.updateFilteredTeamList(Model.PREDICATE_SHOW_ALL_TEAMS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toCreate)),
                false, false, true); // showTeams = true to display teams list
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
        return teamName.equals(otherCreateTeamCommand.teamName)
                && hackathonName.equals(otherCreateTeamCommand.hackathonName)
                && personIndices.equals(otherCreateTeamCommand.personIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("teamName", teamName)
                .add("hackathonName", hackathonName)
                .add("personIndices", personIndices)
                .toString();
    }
}
