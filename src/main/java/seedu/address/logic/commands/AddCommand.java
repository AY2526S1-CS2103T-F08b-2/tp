package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_TELEGRAM + "TELEGRAM "
            + PREFIX_GITHUB + "GITHUB "
            + "[" + PREFIX_SKILL + "SKILL[:LEVEL]]... "
            + "[" + PREFIX_HACKATHON_FILTER + "HACKATHON]...\n"
            + "LEVEL can be: Beginner, Intermediate, or Advanced (default: Beginner)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TELEGRAM + "johndoe "
            + PREFIX_GITHUB + "johndoe123 "
            + PREFIX_SKILL + "java:Advanced "
            + PREFIX_SKILL + "python:Intermediate "
            + PREFIX_HACKATHON_FILTER + "NUSHack "
            + PREFIX_HACKATHON_FILTER + "iNTUition";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        try {
            model.addPerson(toAdd);
        } catch (DuplicatePersonException e) {
            // Determine which contact field conflicts to provide a clearer error message
            for (Person existing : model.getAddressBook().getPersonList()) {
                if (!existing.isSamePerson(toAdd)) {
                    if (existing.getEmail().equals(toAdd.getEmail())) {
                        throw new CommandException("A person with the same email already exists");
                    }
                    if (existing.getTelegram().equals(toAdd.getTelegram())) {
                        throw new CommandException("A person with the same telegram handle already exists");
                    }
                    if (existing.getGitHub().equals(toAdd.getGitHub())) {
                        throw new CommandException("A person with the same GitHub handle already exists");
                    }
                }
            }
            // Fallback generic message if we couldn't determine the conflicting field
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format((Person) toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
