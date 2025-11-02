package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Telegram;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.Team;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: " + PREFIX_PERSON + "INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_TELEGRAM + "TELEGRAM] "
            + "[" + PREFIX_GITHUB + "GITHUB]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TELEGRAM + "johndoe_tg";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        try {
            model.setPerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException e) {
            // Find which contact field conflicts to provide clearer message
            for (Person existing : model.getAddressBook().getPersonList()) {
                if (!existing.isSamePerson(editedPerson)) {
                    if (existing.getEmail().equals(editedPerson.getEmail())) {
                        throw new CommandException("A person with the same email already exists");
                    }
                    if (existing.getTelegram().equals(editedPerson.getTelegram())) {
                        throw new CommandException("A person with the same telegram handle already exists");
                    }
                    if (existing.getGitHub().equals(editedPerson.getGitHub())) {
                        throw new CommandException("A person with the same GitHub handle already exists");
                    }
                }
            }
            // fallback
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        // Succinct success message: only person's name and success verb.
        return new CommandResult(String.format("%1$s successfully edited.", editedPerson.getName()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Telegram updatedTelegram = editPersonDescriptor.getTelegram().orElse(personToEdit.getTelegram());
        GitHub updatedGitHub = editPersonDescriptor.getGitHub().orElse(personToEdit.getGitHub());

        // Skills are not affected by edit command - preserve existing skills
        Set<Skill> updatedSkills = new HashSet<>(personToEdit.getSkills());

        Set<Team> updatedTeams = editPersonDescriptor.getTeams().isPresent()
                ? editPersonDescriptor.getTeams().get()
                : new HashSet<>(personToEdit.getTeams());

        // Interested and participating hackathons are not affected by edit command - preserve them
        Set<HackathonName> updatedInterestedHackathons = new HashSet<>(personToEdit.getInterestedHackathons());
        Set<HackathonName> updatedParticipatingHackathons = new HashSet<>(personToEdit.getParticipatingHackathons());


        return new Person(updatedName, updatedEmail, updatedTelegram, updatedGitHub, updatedSkills,
                updatedTeams, updatedInterestedHackathons, updatedParticipatingHackathons);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Email email;
        private Telegram telegram;
        private GitHub github;
        private Set<Team> teams;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code teams} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setTelegram(toCopy.telegram);
            setGitHub(toCopy.github);
            setTeams(toCopy.teams);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, telegram, github, teams);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setTelegram(Telegram telegram) {
            this.telegram = telegram;
        }

        public Optional<Telegram> getTelegram() {
            return Optional.ofNullable(telegram);
        }

        public void setGitHub(GitHub github) {
            this.github = github;
        }

        public Optional<GitHub> getGitHub() {
            return Optional.ofNullable(github);
        }


        /**
         * Sets {@code teams} to this object's {@code teams}.
         * A defensive copy of {@code teams} is used internally.
         */
        public void setTeams(Set<Team> teams) {
            this.teams = (teams != null) ? new HashSet<>(teams) : null;
        }

        /**
         * Returns an unmodifiable teams set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code teams} is null.
         */
        public Optional<Set<Team>> getTeams() {
            return (teams != null) ? Optional.of(Collections.unmodifiableSet(teams)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(telegram, otherEditPersonDescriptor.telegram)
                    && Objects.equals(github, otherEditPersonDescriptor.github)
                    && Objects.equals(teams, otherEditPersonDescriptor.teams);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("email", email)
                    .toString();
        }
    }
}
