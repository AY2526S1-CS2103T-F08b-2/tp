package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HACKATHON_FILTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
                        PREFIX_TELEGRAM, PREFIX_GITHUB, PREFIX_HACKATHON_FILTER);

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
                PREFIX_TELEGRAM, PREFIX_GITHUB);

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


        // Parse interested hackathons
        parseHackathonsForEdit(argMultimap.getAllValues(PREFIX_HACKATHON_FILTER))
                .ifPresent(editPersonDescriptor::setInterestedHackathons);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
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
