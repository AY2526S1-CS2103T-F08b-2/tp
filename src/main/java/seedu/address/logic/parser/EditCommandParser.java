package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOOKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.skill.Skill;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Logger logger = LogsCenter.getLogger(EditCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("=============================[ Parsing Edit Command ]=============================");
        logger.info("Raw arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_EMAIL,
                        PREFIX_TELEGRAM, PREFIX_GITHUB, PREFIX_SKILL, PREFIX_LOOKING, PREFIX_HACKATHON_FILTER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            logger.fine("Successfully parsed index: " + index.getOneBased());
        } catch (ParseException pe) {
            logger.warning("Failed to parse index from preamble: " + argMultimap.getPreamble());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_EMAIL,
                PREFIX_TELEGRAM, PREFIX_GITHUB, PREFIX_LOOKING);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_TELEGRAM).isPresent()) {
            editPersonDescriptor.setTelegram(ParserUtil.parseTelegram(argMultimap.getValue(PREFIX_TELEGRAM).get()));
        }
        if (argMultimap.getValue(PREFIX_GITHUB).isPresent()) {
            editPersonDescriptor.setGitHub(ParserUtil.parseGitHub(argMultimap.getValue(PREFIX_GITHUB).get()));
        }

        // Parse skills with detailed logging
        if (!argMultimap.getAllValues(PREFIX_SKILL).isEmpty()) {
            logger.info("Attempting to parse skills: " + argMultimap.getAllValues(PREFIX_SKILL));
        }
        parseSkillsForEdit(argMultimap.getAllValues(PREFIX_SKILL)).ifPresent(editPersonDescriptor::setSkills);

        // Parse looking for team status - only accept "true" or "false"
        if (argMultimap.getValue(PREFIX_LOOKING).isPresent()) {
            String lookingValue = argMultimap.getValue(PREFIX_LOOKING).get().trim();
            if (lookingValue.equals("true")) {
                editPersonDescriptor.setIsLookingForTeam(true);
            } else if (lookingValue.equals("false")) {
                editPersonDescriptor.setIsLookingForTeam(false);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }

        // Parse interested hackathons
        parseHackathonsForEdit(argMultimap.getAllValues(PREFIX_HACKATHON_FILTER))
                .ifPresent(editPersonDescriptor::setInterestedHackathons);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>} if {@code skills} is non-empty.
     * If {@code skills} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Skill>} containing zero skills.
     */
    private Optional<Set<Skill>> parseSkillsForEdit(Collection<String> skills) throws ParseException {
        assert skills != null;

        if (skills.isEmpty()) {
            logger.fine("No skills provided for editing");
            return Optional.empty();
        }
        Collection<String> skillSet = skills.size() == 1 && skills.contains("") ? Collections.emptySet() : skills;

        try {
            Set<Skill> parsedSkills = ParserUtil.parseSkills(skillSet);
            logger.info("Successfully parsed " + parsedSkills.size() + " skill(s)");
            return Optional.of(parsedSkills);
        } catch (ParseException e) {
            logger.warning("=============================[ Skill Parsing Error ]=============================");
            logger.warning("Error parsing skills: " + skillSet);
            logger.warning("Error message: " + e.getMessage());
            logger.warning("Possible cause: Skill names must be lowercase alphanumeric, "
                    + "may include '+' or '#' symbols, but cannot start with '#'");
            logger.warning("=================================================================================");
            throw e;
        }
    }

    /**
     * Parses {@code Collection<String> hackathons} into {@code Set<HackathonName>} if {@code hackathons} is non-empty.
     * If {@code hackathons} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<HackathonName>} containing zero hackathons.
     */
    private Optional<Set<HackathonName>> parseHackathonsForEdit(Collection<String> hackathons) throws ParseException {
        assert hackathons != null;

        if (hackathons.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> hackathonSet = hackathons.size() == 1 && hackathons.contains("")
                ? Collections.emptySet() : hackathons;
        return Optional.of(ParserUtil.parseHackathonNames(hackathonSet));
    }
}
