package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TELEGRAM = new Prefix("tg/");
    public static final Prefix PREFIX_GITHUB = new Prefix("gh/");
    public static final Prefix PREFIX_TEAM_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_HACKATHON_NAME = new Prefix("hn/");
    public static final Prefix PREFIX_PERSON = new Prefix("p/");
    public static final Prefix PREFIX_SKILL = new Prefix("s/");
    public static final Prefix PREFIX_LOOKING = new Prefix("l/");
    public static final Prefix PREFIX_HACKATHON_FILTER = new Prefix("h/");

}
