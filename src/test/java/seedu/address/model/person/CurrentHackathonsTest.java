package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.hackathon.HackathonName;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for the currentHackathons field in Person.
 * This field stores hackathons that a person is currently participating in (via teams).
 */
public class CurrentHackathonsTest {

    /**
     * Tests that a newly created person has an empty currentHackathons set by default.
     */
    @Test
    public void constructor_noCurrentHackathons_emptySet() {
        Person person = new PersonBuilder().build();
        assertTrue(person.getCurrentHackathons().isEmpty());
        assertEquals(0, person.getCurrentHackathons().size());
        assert person.getCurrentHackathons().isEmpty() : "New person should have empty currentHackathons";
    }

    /**
     * Tests that currentHackathons can be added during person creation.
     */
    @Test
    public void constructor_withCurrentHackathons_correctSet() {
        Person person = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge")
                .build();
        
        assertEquals(2, person.getCurrentHackathons().size());
        assert person.getCurrentHackathons().size() == 2 : "Person should have 2 current hackathons";
    }

    /**
     * Tests that the returned currentHackathons set is immutable.
     */
    @Test
    public void getCurrentHackathons_modifyReturnedSet_throwsException() {
        Person person = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024")
                .build();
        
        Set<HackathonName> hackathons = person.getCurrentHackathons();
        assertThrows(UnsupportedOperationException.class, () -> hackathons.add(new HackathonName("NewHack")));
        assertThrows(UnsupportedOperationException.class, () -> hackathons.clear());
    }

    /**
     * Tests that persons with different currentHackathons are not equal.
     */
    @Test
    public void equals_differentCurrentHackathons_returnsFalse() {
        Person person1 = new PersonBuilder().withCurrentHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withCurrentHackathons("AI Challenge").build();
        
        assertFalse(person1.equals(person2));
        assertNotEquals(person1, person2);
        assert !person1.equals(person2) : "Persons with different current hackathons should not be equal";
    }

    /**
     * Tests that persons with same currentHackathons are equal.
     */
    @Test
    public void equals_sameCurrentHackathons_returnsTrue() {
        Person person1 = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge")
                .build();
        
        assertTrue(person1.equals(person2));
        assertEquals(person1, person2);
        assert person1.equals(person2) : "Persons with same current hackathons should be equal";
    }

    /**
     * Tests that currentHackathons are properly included in hashCode calculation.
     */
    @Test
    public void hashCode_differentCurrentHackathons_differentHashCodes() {
        Person person1 = new PersonBuilder().withCurrentHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withCurrentHackathons("AI Challenge").build();
        
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    /**
     * Tests that currentHackathons are properly included in hashCode calculation for equal persons.
     */
    @Test
    public void hashCode_sameCurrentHackathons_sameHashCodes() {
        Person person1 = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge")
                .build();
        
        assertEquals(person1.hashCode(), person2.hashCode());
        assert person1.hashCode() == person2.hashCode() : "Equal persons should have equal hash codes";
    }

    /**
     * Tests that currentHackathons are correctly included in toString output.
     */
    @Test
    public void toString_withCurrentHackathons_containsCurrentHackathons() {
        Person person = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024")
                .build();
        
        String personString = person.toString();
        assertTrue(personString.contains("currentHackathons"));
        assertTrue(personString.contains("HackNUS2024"));
    }

    /**
     * Tests that multiple currentHackathons can be stored and retrieved correctly.
     */
    @Test
    public void getCurrentHackathons_multipleHackathons_allPresent() {
        Person person = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024", "AI Challenge", "Web Dev Contest", "Innovation Day")
                .build();
        
        Set<HackathonName> hackathons = person.getCurrentHackathons();
        assertEquals(4, hackathons.size());
        
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Web Dev Contest")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Innovation Day")));
        
        assert hackathons.size() == 4 : "Person should have all 4 current hackathons";
    }

    /**
     * Tests that currentHackathon names with spaces are handled correctly.
     */
    @Test
    public void getCurrentHackathons_hackathonWithSpaces_storedCorrectly() {
        Person person = new PersonBuilder()
                .withCurrentHackathons("AI Challenge 2024", "Web Development Hackathon")
                .build();
        
        assertEquals(2, person.getCurrentHackathons().size());
        assertTrue(person.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
        assertTrue(person.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Development Hackathon")));
    }

    /**
     * Tests that empty currentHackathons set is different from currentHackathons set with values.
     */
    @Test
    public void equals_emptyVsPopulatedCurrentHackathons_returnsFalse() {
        Person personWithoutHackathons = new PersonBuilder().build();
        Person personWithHackathons = new PersonBuilder()
                .withCurrentHackathons("HackNUS2024")
                .build();
        
        assertFalse(personWithoutHackathons.equals(personWithHackathons));
        assertFalse(personWithHackathons.equals(personWithoutHackathons));
        assertNotEquals(personWithoutHackathons, personWithHackathons);
    }

    /**
     * Tests that PersonBuilder correctly copies currentHackathons from an existing person.
     */
    @Test
    public void personBuilder_copyConstructor_preservesCurrentHackathons() {
        Person original = new PersonBuilder()
                .withName("John Doe")
                .withCurrentHackathons("HackNUS2024", "AI Challenge", "Innovation Day")
                .build();
        
        Person copy = new PersonBuilder(original).build();
        
        assertEquals(original.getCurrentHackathons(), copy.getCurrentHackathons());
        assertEquals(3, copy.getCurrentHackathons().size());
        
        // Verify all hackathons are copied
        assertTrue(copy.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(copy.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(copy.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("Innovation Day")));
        
        assert copy.getCurrentHackathons().equals(original.getCurrentHackathons())
                : "Copied person should have same current hackathons as original";
    }

    /**
     * Tests that persons with same data except currentHackathons are not equal.
     */
    @Test
    public void equals_sameDataExceptCurrentHackathons_returnsFalse() {
        Person person1 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withCurrentHackathons("HackNUS2024")
                .build();
        
        Person person2 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withCurrentHackathons("AI Challenge")
                .build();
        
        assertFalse(person1.equals(person2));
        assert !person1.equals(person2) : "Same person data but different current hackathons should not be equal";
    }

    /**
     * Tests that currentHackathons and interestedHackathons are independent fields.
     */
    @Test
    public void currentHackathons_independentFromInterestedHackathons() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .withCurrentHackathons("Web Dev Contest")
                .build();
        
        assertEquals(2, person.getInterestedHackathons().size());
        assertEquals(1, person.getCurrentHackathons().size());
        
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(person.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Dev Contest")));
        
        // Current hackathons should not contain interested hackathons
        assertFalse(person.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
    }

    /**
     * Tests that a person can be interested in and currently participating in the same hackathon.
     */
    @Test
    public void hackathons_canBeInBothInterestedAndCurrent() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024")
                .withCurrentHackathons("HackNUS2024")
                .build();
        
        assertEquals(1, person.getInterestedHackathons().size());
        assertEquals(1, person.getCurrentHackathons().size());
        
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(person.getCurrentHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
    }
}

