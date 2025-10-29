package seedu.address.model.hackathon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class HackathonNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new HackathonName(null));
    }

    @Test
    public void constructor_invalidHackathonName_throwsIllegalArgumentException() {
        String invalidHackathonName = "";
        assertThrows(IllegalArgumentException.class, () -> new HackathonName(invalidHackathonName));
    }

    @Test
    public void isValidHackathonName() {
        // null hackathon name
        assertThrows(NullPointerException.class, () -> HackathonName.isValidHackathonName(null));

        // invalid hackathon names
        assertFalse(HackathonName.isValidHackathonName("")); // empty string
        assertFalse(HackathonName.isValidHackathonName(" ")); // spaces only
        assertFalse(HackathonName.isValidHackathonName("@Hackathon")); // contains special characters

        // valid hackathon names
        assertTrue(HackathonName.isValidHackathonName("NUSHack")); // alphabets only
        assertTrue(HackathonName.isValidHackathonName("Tech Challenge 2024")); // alphanumeric with spaces
        assertTrue(HackathonName.isValidHackathonName("AI Innovation Contest")); // multiple words
        assertTrue(HackathonName.isValidHackathonName("h")); // single character
    }

    @Test
    public void equals_sameHackathonName_returnsTrue() {
        HackathonName hackathon = new HackathonName("NUSHack");

        // same object -> returns true
        assertTrue(hackathon.equals(hackathon));

        // same name, different object -> returns true
        HackathonName hackathonCopy = new HackathonName("NUSHack");
        assertTrue(hackathon.equals(hackathonCopy));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        HackathonName hackathon1 = new HackathonName("NUSHack");
        HackathonName hackathon2 = new HackathonName("nushack");
        HackathonName hackathon3 = new HackathonName("NUSHACK");
        HackathonName hackathon4 = new HackathonName("NuShAcK");

        // different case variations -> returns true
        assertTrue(hackathon1.equals(hackathon2));
        assertTrue(hackathon1.equals(hackathon3));
        assertTrue(hackathon1.equals(hackathon4));
        assertTrue(hackathon2.equals(hackathon3));
        assertTrue(hackathon3.equals(hackathon4));
    }

    @Test
    public void equals_differentHackathonName_returnsFalse() {
        HackathonName hackathon1 = new HackathonName("NUSHack");
        HackathonName hackathon2 = new HackathonName("TechChallenge");

        // different hackathon names -> returns false
        assertFalse(hackathon1.equals(hackathon2));

        // null -> returns false
        assertFalse(hackathon1.equals(null));

        // different type -> returns false
        assertFalse(hackathon1.equals("NUSHack"));
    }

    @Test
    public void hashCode_sameHackathonName_returnsSameHashCode() {
        HackathonName hackathon1 = new HackathonName("NUSHack");
        HackathonName hackathon2 = new HackathonName("NUSHack");

        assertEquals(hackathon1.hashCode(), hackathon2.hashCode());
    }

    @Test
    public void hashCode_caseInsensitive_returnsSameHashCode() {
        HackathonName hackathon1 = new HackathonName("NUSHack");
        HackathonName hackathon2 = new HackathonName("nushack");
        HackathonName hackathon3 = new HackathonName("NUSHACK");
        HackathonName hackathon4 = new HackathonName("NuShAcK");

        // different case variations should have same hash code
        assertEquals(hackathon1.hashCode(), hackathon2.hashCode());
        assertEquals(hackathon1.hashCode(), hackathon3.hashCode());
        assertEquals(hackathon1.hashCode(), hackathon4.hashCode());
    }

    @Test
    public void toString_returnsOriginalValue() {
        // toString should preserve the original case
        HackathonName hackathon1 = new HackathonName("NUSHack");
        assertEquals("NUSHack", hackathon1.toString());

        HackathonName hackathon2 = new HackathonName("nushack");
        assertEquals("nushack", hackathon2.toString());

        HackathonName hackathon3 = new HackathonName("NUSHACK");
        assertEquals("NUSHACK", hackathon3.toString());
    }
}

