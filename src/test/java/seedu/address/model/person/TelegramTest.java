package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Telegram(null));
    }

    @Test
    public void constructor_invalidTelegram_throwsIllegalArgumentException() {
        String invalidTelegram = "";
        assertThrows(IllegalArgumentException.class, () -> new Telegram(invalidTelegram));
    }

    @Test
    public void isValidTelegram() {
        // null telegram handle
        assertThrows(NullPointerException.class, () -> Telegram.isValidTelegram(null));

        // invalid telegram handles
        assertFalse(Telegram.isValidTelegram("")); // empty string
        assertFalse(Telegram.isValidTelegram(" ")); // spaces only
        assertFalse(Telegram.isValidTelegram("@john")); // contains '@'
        assertFalse(Telegram.isValidTelegram("john doe")); // spaces
        assertFalse(Telegram.isValidTelegram("jo")); // too short
        assertFalse(Telegram.isValidTelegram("john!doe")); // invalid symbol
        assertFalse(Telegram.isValidTelegram("john_doe_")); // ends with underscore

        // valid telegram handles
        assertTrue(Telegram.isValidTelegram("johndoe"));
        assertTrue(Telegram.isValidTelegram("john_doe"));
        assertTrue(Telegram.isValidTelegram("john123"));
        assertTrue(Telegram.isValidTelegram("john_doe123"));
    }

    @Test
    public void equals() {
        Telegram telegram = new Telegram("johndoe");

        // same values -> returns true
        assertTrue(telegram.equals(new Telegram("johndoe")));

        // same object -> returns true
        assertTrue(telegram.equals(telegram));

        // null -> returns false
        assertFalse(telegram.equals(null));

        // different types -> returns false
        assertFalse(telegram.equals(5.0f));

        // different values -> returns false
        assertFalse(telegram.equals(new Telegram("janedoe")));
    }
}

