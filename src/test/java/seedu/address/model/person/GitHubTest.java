package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GitHubTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GitHub(null));
    }

    @Test
    public void constructor_invalidGitHub_throwsIllegalArgumentException() {
        String invalidGitHub = "";
        assertThrows(IllegalArgumentException.class, () -> new GitHub(invalidGitHub));
    }

    @Test
    public void isValidGitHub() {
        // null github
        assertThrows(NullPointerException.class, () -> GitHub.isValidGitHub(null));

        // invalid github usernames
        assertFalse(GitHub.isValidGitHub("")); // empty string
        assertFalse(GitHub.isValidGitHub(" ")); // spaces only
        assertFalse(GitHub.isValidGitHub("john@doe")); // invalid symbol
        assertFalse(GitHub.isValidGitHub("john doe")); // spaces
        assertFalse(GitHub.isValidGitHub("jo")); // too short
        assertFalse(GitHub.isValidGitHub("-johndoe")); // starts with invalid char
        assertFalse(GitHub.isValidGitHub("john_doe_")); // ends with underscore (invalid)

        // valid github usernames
        assertTrue(GitHub.isValidGitHub("johndoe"));
        assertTrue(GitHub.isValidGitHub("john-doe"));
        assertTrue(GitHub.isValidGitHub("john123"));
        assertTrue(GitHub.isValidGitHub("john-doe123"));
    }

    @Test
    public void equals() {
        GitHub github = new GitHub("john-doe");

        // same values -> returns true
        assertTrue(github.equals(new GitHub("john-doe")));

        // same object -> returns true
        assertTrue(github.equals(github));

        // null -> returns false
        assertFalse(github.equals(null));

        // different types -> returns false
        assertFalse(github.equals(5.0f));

        // different values -> returns false
        assertFalse(github.equals(new GitHub("janedoe")));
    }
}
