package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.hackathon.HackathonName;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHub;
import seedu.address.model.person.Name;
import seedu.address.model.person.Telegram;
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;
import seedu.address.model.team.TeamName;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final Logger logger = LogsCenter.getLogger(ParserUtil.class);

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String skill} into a {@code Skill}.
     * Leading and trailing whitespaces will be trimmed.
     * Format: "SkillName" or "SkillName:ExperienceLevel"
     * If no experience level is specified, defaults to BEGINNER.
     *
     * @throws ParseException if the given {@code skill} is invalid.
     */
    public static Skill parseSkill(String skill) throws ParseException {
        requireNonNull(skill);
        String trimmedSkill = skill.trim();
        logger.fine("Parsing skill: '" + trimmedSkill + "'");

        // Check if skill contains experience level (format: skillName:level)
        String[] parts = trimmedSkill.split(":", 2);
        String skillName = parts[0].trim();

        if (!Skill.isValidSkillName(skillName)) {
            logger.warning("Invalid skill name detected: '" + skillName + "'");
            logger.warning("Skill validation failed. Reason: " + Skill.MESSAGE_CONSTRAINTS);

            // Additional detailed logging for common mistakes
            if (skillName.matches(".*[A-Z].*")) {
                logger.warning("Detected uppercase letters in skill name. Skill names must be lowercase.");
            }
            if (skillName.startsWith("#")) {
                logger.warning("Detected skill name starting with '#'. This is not allowed.");
            }
            if (skillName.matches(".*[^a-z0-9+#].*")) {
                logger.warning("Detected invalid characters in skill name. Only lowercase letters, "
                        + "numbers, '+', and '#' are allowed.");
            }

            throw new ParseException(Skill.MESSAGE_CONSTRAINTS);
        }

        // Parse experience level if provided
        if (parts.length == 2) {
            String levelStr = parts[1].trim();
            logger.fine("Parsing experience level: '" + levelStr + "'");
            if (!ExperienceLevel.isValidExperienceLevel(levelStr)) {
                logger.warning("Invalid experience level: '" + levelStr + "'. " + ExperienceLevel.MESSAGE_CONSTRAINTS);
                throw new ParseException(ExperienceLevel.MESSAGE_CONSTRAINTS);
            }
            ExperienceLevel level = ExperienceLevel.fromString(levelStr);
            logger.info("Successfully parsed skill: '" + skillName + "' with level: " + level);
            return new Skill(skillName, level);
        }

        logger.info("Successfully parsed skill: '" + skillName + "' with default level: BEGINNER");
        return new Skill(skillName);
    }

    /**
     * Parses a {@code String teamName} into a {@code TeamName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code teamName} is invalid.
     */
    public static TeamName parseTeamName(String teamName) throws ParseException {
        requireNonNull(teamName);
        String trimmedTeamName = teamName.trim();
        if (!TeamName.isValidTeamName(trimmedTeamName)) {
            throw new ParseException(TeamName.MESSAGE_CONSTRAINTS);
        }
        return new TeamName(trimmedTeamName);
    }

    /**
     * Parses a {@code String telegram} into a {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegram} is invalid.
     */
    public static Telegram parseTelegram(String telegram) throws ParseException {
        requireNonNull(telegram);
        String trimmedTelegram = telegram.trim();
        if (!Telegram.isValidTelegram(trimmedTelegram)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedTelegram);
    }

    /**
     * Parses a {@code String github} into a {@code GitHub}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code github} is invalid.
     */
    public static GitHub parseGitHub(String github) throws ParseException {
        requireNonNull(github);
        String trimmedGitHub = github.trim();
        if (!GitHub.isValidGitHub(trimmedGitHub)) {
            throw new ParseException(GitHub.MESSAGE_CONSTRAINTS);
        }
        return new GitHub(trimmedGitHub);
    }

    /**
     * Parses {@code Collection<String> skills} into a {@code Set<Skill>}.
     */
    public static Set<Skill> parseSkills(Collection<String> skills) throws ParseException {
        requireNonNull(skills);
        final Set<Skill> skillSet = new HashSet<>();
        for (String skillName : skills) {
            skillSet.add(parseSkill(skillName));
        }
        return skillSet;
    }

    /**
     * Parses a {@code String hackathonName} into a {@code HackathonName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hackathonName} is invalid.
     */
    public static HackathonName parseHackathonName(String hackathonName) throws ParseException {
        requireNonNull(hackathonName);
        String trimmedHackathonName = hackathonName.trim();
        if (!HackathonName.isValidHackathonName(trimmedHackathonName)) {
            throw new ParseException(HackathonName.MESSAGE_CONSTRAINTS);
        }
        return new HackathonName(trimmedHackathonName);
    }

    /**
     * Parses {@code Collection<String> hackathonNames} into a {@code Set<HackathonName>}.
     */
    public static Set<HackathonName> parseHackathonNames(Collection<String> hackathonNames) throws ParseException {
        requireNonNull(hackathonNames);
        final Set<HackathonName> hackathonSet = new HashSet<>();
        for (String hackathonName : hackathonNames) {
            hackathonSet.add(parseHackathonName(hackathonName));
        }
        return hackathonSet;
    }

    /**
     * Parses {@code Collection<String> personIndices} into a {@code Set<Index>}.
     */
    public static Set<Index> parsePersonIndices(Collection<String> personIndices) throws ParseException {
        requireNonNull(personIndices);
        final Set<Index> indexSet = new HashSet<>();
        for (String indexString : personIndices) {
            indexSet.add(parseIndex(indexString));
        }
        return indexSet;
    }
}
