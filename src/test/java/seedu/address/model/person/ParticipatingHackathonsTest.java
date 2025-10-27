package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.hackathon.HackathonName;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for the participatingHackathons field in Person.
 * This field stores hackathons that a person is currently participating in (via teams).
 */
public class ParticipatingHackathonsTest {

    /**
     * Tests that a newly created person has an empty participatingHackathons set by default.
     */
    @Test
    public void constructor_noCurrentHackathons_emptySet() {
        Person person = new PersonBuilder().build();
        assertTrue(person.getParticipatingHackathons().isEmpty());
        assertEquals(0, person.getParticipatingHackathons().size());
        assert person.getParticipatingHackathons().isEmpty() : "New person should have empty participatingHackathons";
    }

    /**
     * Tests that participatingHackathons can be added during person creation.
     */
    @Test
    public void constructor_withCurrentHackathons_correctSet() {
        Person person = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge")
                .build();
        assertEquals(2, person.getParticipatingHackathons().size());
        assert person.getParticipatingHackathons().size() == 2 : "Person should have 2 participating hackathons";
    }

    /**
     * Tests that the returned participatingHackathons set is immutable.
     */
    @Test
    public void getParticipatingHackathons_modifyReturnedSet_throwsException() {
        Person person = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024")
                .build();
        Set<HackathonName> hackathons = person.getParticipatingHackathons();
        assertThrows(UnsupportedOperationException.class, () -> hackathons.add(new HackathonName("NewHack")));
        assertThrows(UnsupportedOperationException.class, () -> hackathons.clear());
    }

    /**
     * Tests that persons with different participatingHackathons are not equal.
     */
    @Test
    public void equals_differentCurrentHackathons_returnsFalse() {
        Person person1 = new PersonBuilder().withParticipatingHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withParticipatingHackathons("AI Challenge").build();
        assertFalse(person1.equals(person2));
        assertNotEquals(person1, person2);
        assert !person1.equals(person2) : "Persons with different participating hackathons should not be equal";
    }

    /**
     * Tests that persons with same participatingHackathons are equal.
     */
    @Test
    public void equals_sameCurrentHackathons_returnsTrue() {
        Person person1 = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge")
                .build();
        assertTrue(person1.equals(person2));
        assertEquals(person1, person2);
        assert person1.equals(person2) : "Persons with same participating hackathons should be equal";
    }

    /**
     * Tests that participatingHackathons are properly included in hashCode calculation.
     */
    @Test
    public void hashCode_differentCurrentHackathons_differentHashCodes() {
        Person person1 = new PersonBuilder().withParticipatingHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withParticipatingHackathons("AI Challenge").build();
        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    /**
     * Tests that participatingHackathons are properly included in hashCode calculation for equal persons.
     */
    @Test
    public void hashCode_sameCurrentHackathons_sameHashCodes() {
        Person person1 = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge")
                .build();
        assertEquals(person1.hashCode(), person2.hashCode());
        assert person1.hashCode() == person2.hashCode() : "Equal persons should have equal hash codes";
    }

    /**
     * Tests that participatingHackathons are correctly included in toString output.
     */
    @Test
    public void toString_withCurrentHackathons_containsCurrentHackathons() {
        Person person = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024")
                .build();
        String personString = person.toString();
        assertTrue(personString.contains("participatingHackathons"));
        assertTrue(personString.contains("HackNUS2024"));
    }

    /**
     * Tests that multiple participatingHackathons can be stored and retrieved correctly.
     */
    @Test
    public void getParticipatingHackathons_multipleHackathons_allPresent() {
        Person person = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024", "AI Challenge", "Web Dev Contest", "Innovation Day")
                .build();
        Set<HackathonName> hackathons = person.getParticipatingHackathons();
        assertEquals(4, hackathons.size());
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Web Dev Contest")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Innovation Day")));
        assert hackathons.size() == 4 : "Person should have all 4 participating hackathons";
    }

    /**
     * Tests that participatingHackathon names with spaces are handled correctly.
     */
    @Test
    public void getParticipatingHackathons_hackathonWithSpaces_storedCorrectly() {
        Person person = new PersonBuilder()
                .withParticipatingHackathons("AI Challenge 2024", "Web Development Hackathon")
                .build();
        assertEquals(2, person.getParticipatingHackathons().size());
        assertTrue(person.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
        assertTrue(person.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Development Hackathon")));
    }

    /**
     * Tests that empty participatingHackathons set is different from participatingHackathons set with values.
     */
    @Test
    public void equals_emptyVsPopulatedCurrentHackathons_returnsFalse() {
        Person personWithoutHackathons = new PersonBuilder().build();
        Person personWithHackathons = new PersonBuilder()
                .withParticipatingHackathons("HackNUS2024")
                .build();
        assertFalse(personWithoutHackathons.equals(personWithHackathons));
        assertFalse(personWithHackathons.equals(personWithoutHackathons));
        assertNotEquals(personWithoutHackathons, personWithHackathons);
    }

    /**
     * Tests that PersonBuilder correctly copies participatingHackathons from an existing person.
     */
    @Test
    public void personBuilder_copyConstructor_preservesCurrentHackathons() {
        Person original = new PersonBuilder()
                .withName("John Doe")
                .withParticipatingHackathons("HackNUS2024", "AI Challenge", "Innovation Day")
                .build();
        Person copy = new PersonBuilder(original).build();
        assertEquals(original.getParticipatingHackathons(), copy.getParticipatingHackathons());
        assertEquals(3, copy.getParticipatingHackathons().size());
        // Verify all hackathons are copied
        assertTrue(copy.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(copy.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(copy.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("Innovation Day")));
        assert copy.getParticipatingHackathons().equals(original.getParticipatingHackathons())
                : "Copied person should have same participating hackathons as original";
    }

    /**
     * Tests that persons with same data except participatingHackathons are not equal.
     */
    @Test
    public void equals_sameDataExceptCurrentHackathons_returnsFalse() {
        Person person1 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withParticipatingHackathons("HackNUS2024")
                .build();
        Person person2 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withParticipatingHackathons("AI Challenge")
                .build();
        assertFalse(person1.equals(person2));
        assert !person1.equals(person2) : "Same person data but different participating hackathons should not be equal";
    }

    /**
     * Tests that participatingHackathons and interestedHackathons are independent fields.
     */
    @Test
    public void currentHackathons_independentFromInterestedHackathons() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .withParticipatingHackathons("Web Dev Contest")
                .build();
        assertEquals(2, person.getInterestedHackathons().size());
        assertEquals(1, person.getParticipatingHackathons().size());
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(person.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Dev Contest")));
        // Participating hackathons should not contain interested hackathons
        assertFalse(person.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
    }

    /**
     * Tests that a person can be interested in and currently participating in the same hackathon.
     */
    @Test
    public void hackathons_canBeInBothInterestedAndCurrent() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024")
                .withParticipatingHackathons("HackNUS2024")
                .build();
        assertEquals(1, person.getInterestedHackathons().size());
        assertEquals(1, person.getParticipatingHackathons().size());
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(person.getParticipatingHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
    }
}
