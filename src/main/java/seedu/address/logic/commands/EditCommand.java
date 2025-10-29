package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
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
            + "[" + PREFIX_GITHUB + "GITHUB] "
            + "[" + PREFIX_SKILL + "SKILL[:LEVEL]]... "
            + "[" + PREFIX_HACKATHON + "HACKATHON]...\n"
            + "LEVEL can be: Beginner, Intermediate, or Advanced (default: Beginner)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PERSON + "1 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_SKILL + "Java:Advanced "
            + PREFIX_HACKATHON + "NUSHack";

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

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format((Person) editedPerson)));
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
        Set<Skill> updatedSkills = new HashSet<>(personToEdit.getSkills());

        // Remove existing skills with the same name and add new ones with updated levels
        if (editPersonDescriptor.getSkills().isPresent()) {
            Set<Skill> newSkills = editPersonDescriptor.getSkills().get();
            // Remove old skills that have the same name as new skills
            updatedSkills.removeAll(newSkills);
            // Add all new skills with their updated experience levels
            updatedSkills.addAll(newSkills);
        }

        Set<Team> updatedTeams = editPersonDescriptor.getTeams().isPresent()
                ? editPersonDescriptor.getTeams().get()
                : new HashSet<>(personToEdit.getTeams());

        // Preserve participating hackathons - they should not be affected by edit command
        Set<HackathonName> updatedParticipatingHackathons = new HashSet<>(personToEdit.getParticipatingHackathons());

        Set<HackathonName> updatedInterestedHackathons;
        if (editPersonDescriptor.getInterestedHackathons().isPresent()) {
            updatedInterestedHackathons = editPersonDescriptor.getInterestedHackathons().get();

            // Check if any interested hackathon is already in participating hackathons
            for (HackathonName hackathon : updatedInterestedHackathons) {
                if (updatedParticipatingHackathons.contains(hackathon)) {
                    throw new CommandException("Cannot add hackathon '" + hackathon.value
                            + "' to interested list. You are already participating in this hackathon.");
                }
            }
        } else {
            updatedInterestedHackathons = new HashSet<>(personToEdit.getInterestedHackathons());
        }

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
        private Set<Skill> skills;
        private Set<Team> teams;
        private Set<HackathonName> interestedHackathons;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code skills} and {@code teams} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setTelegram(toCopy.telegram);
            setGitHub(toCopy.github);
            setSkills(toCopy.skills);
            setTeams(toCopy.teams);
            setInterestedHackathons(toCopy.interestedHackathons);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, telegram, github, skills, teams,
                    interestedHackathons);
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
         * Sets {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
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


        /**
         * Sets {@code interestedHackathons} to this object's {@code interestedHackathons}.
         * A defensive copy of {@code interestedHackathons} is used internally.
         */
        public void setInterestedHackathons(Set<HackathonName> interestedHackathons) {
            this.interestedHackathons = (interestedHackathons != null)
                    ? new HashSet<>(interestedHackathons) : null;
        }

        /**
         * Returns an unmodifiable hackathon set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code interestedHackathons} is null.
         */
        public Optional<Set<HackathonName>> getInterestedHackathons() {
            return (interestedHackathons != null)
                    ? Optional.of(Collections.unmodifiableSet(interestedHackathons)) : Optional.empty();
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
                    && Objects.equals(skills, otherEditPersonDescriptor.skills)
                    && Objects.equals(teams, otherEditPersonDescriptor.teams)
                    && Objects.equals(interestedHackathons, otherEditPersonDescriptor.interestedHackathons);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("email", email)
                    .add("skills", skills)
                    .add("interestedHackathons", interestedHackathons)
                    .toString();
        }
    }
}
