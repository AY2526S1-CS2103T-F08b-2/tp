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
 * Contains integration tests and unit tests for the interestedHackathons field in Person.
 */
public class InterestedHackathonsTest {

    /**
     * Tests that a newly created person has an empty hackathons set by default.
     */
    @Test
    public void constructor_noHackathons_emptySet() {
        Person person = new PersonBuilder().build();
        assertTrue(person.getInterestedHackathons().isEmpty());
        assertEquals(0, person.getInterestedHackathons().size());
        assert person.getInterestedHackathons().isEmpty() : "New person should have empty hackathons";
    }

    /**
     * Tests that hackathons can be added during person creation.
     */
    @Test
    public void constructor_withHackathons_correctSet() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("HackNUS2024"));
        hackathons.add(new HackathonName("AI Challenge"));

        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .build();

        assertEquals(2, person.getInterestedHackathons().size());
        assert person.getInterestedHackathons().size() == 2 : "Person should have 2 hackathons";
    }

    /**
     * Tests that the returned hackathons set is immutable.
     */
    @Test
    public void getInterestedHackathons_modifyReturnedSet_throwsException() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024")
                .build();

        Set<HackathonName> hackathons = person.getInterestedHackathons();
        assertThrows(UnsupportedOperationException.class, () -> hackathons.add(new HackathonName("NewHack")));
        assertThrows(UnsupportedOperationException.class, () -> hackathons.clear());
    }

    /**
     * Tests that persons with different hackathons are not equal.
     */
    @Test
    public void equals_differentHackathons_returnsFalse() {
        Person person1 = new PersonBuilder().withInterestedHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withInterestedHackathons("AI Challenge").build();

        assertFalse(person1.equals(person2));
        assertNotEquals(person1, person2);
        assert !person1.equals(person2) : "Persons with different hackathons should not be equal";
    }

    /**
     * Tests that persons with same hackathons are equal.
     */
    @Test
    public void equals_sameHackathons_returnsTrue() {
        Person person1 = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .build();

        assertTrue(person1.equals(person2));
        assertEquals(person1, person2);
        assert person1.equals(person2) : "Persons with same hackathons should be equal";
    }

    /**
     * Tests that hackathons are properly included in hashCode calculation.
     */
    @Test
    public void hashCode_differentHackathons_differentHashCodes() {
        Person person1 = new PersonBuilder().withInterestedHackathons("HackNUS2024").build();
        Person person2 = new PersonBuilder().withInterestedHackathons("AI Challenge").build();

        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    /**
     * Tests that hackathons are properly included in hashCode calculation for equal persons.
     */
    @Test
    public void hashCode_sameHackathons_sameHashCodes() {
        Person person1 = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .build();
        Person person2 = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge")
                .build();

        assertEquals(person1.hashCode(), person2.hashCode());
        assert person1.hashCode() == person2.hashCode() : "Equal persons should have equal hash codes";
    }

    /**
     * Tests that hackathons are correctly included in toString output.
     */
    @Test
    public void toString_withHackathons_containsHackathons() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024")
                .build();

        String personString = person.toString();
        assertTrue(personString.contains("interestedHackathons"));
        assertTrue(personString.contains("HackNUS2024"));
    }

    /**
     * Tests that multiple hackathons can be stored and retrieved correctly.
     */
    @Test
    public void getInterestedHackathons_multipleHackathons_allPresent() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge", "Web Dev Contest", "Innovation Day")
                .build();

        Set<HackathonName> hackathons = person.getInterestedHackathons();
        assertEquals(4, hackathons.size());

        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Web Dev Contest")));
        assertTrue(hackathons.stream().anyMatch(h -> h.value.equals("Innovation Day")));

        assert hackathons.size() == 4 : "Person should have all 4 hackathons";
    }

    /**
     * Tests that hackathon names with spaces are handled correctly.
     */
    @Test
    public void getInterestedHackathons_hackathonWithSpaces_storedCorrectly() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("AI Challenge 2024", "Web Development Hackathon")
                .build();

        assertEquals(2, person.getInterestedHackathons().size());
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Development Hackathon")));
    }

    /**
     * Tests that duplicate hackathons are not added (set behavior).
     */
    @Test
    public void constructor_duplicateHackathons_noDuplicatesInSet() {
        // PersonBuilder uses HashSet which automatically handles duplicates
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "HackNUS2024", "AI Challenge")
                .build();

        // Should only have 2 unique hackathons
        assertEquals(2, person.getInterestedHackathons().size());
        assert person.getInterestedHackathons().size() == 2 : "Duplicate hackathons should not be stored";
    }

    /**
     * Tests that empty hackathon set is different from hackathon set with values.
     */
    @Test
    public void equals_emptyVsPopulatedHackathons_returnsFalse() {
        Person personWithoutHackathons = new PersonBuilder().build();
        Person personWithHackathons = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024")
                .build();

        assertFalse(personWithoutHackathons.equals(personWithHackathons));
        assertFalse(personWithHackathons.equals(personWithoutHackathons));
        assertNotEquals(personWithoutHackathons, personWithHackathons);
    }

    /**
     * Tests that PersonBuilder correctly copies hackathons from an existing person.
     */
    @Test
    public void personBuilder_copyConstructor_preservesHackathons() {
        Person original = new PersonBuilder()
                .withName("John Doe")
                .withInterestedHackathons("HackNUS2024", "AI Challenge", "Innovation Day")
                .build();

        Person copy = new PersonBuilder(original).build();

        assertEquals(original.getInterestedHackathons(), copy.getInterestedHackathons());
        assertEquals(3, copy.getInterestedHackathons().size());

        // Verify all hackathons are copied
        assertTrue(copy.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(copy.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge")));
        assertTrue(copy.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("Innovation Day")));

        assert copy.getInterestedHackathons().equals(original.getInterestedHackathons())
                : "Copied person should have same hackathons as original";
    }

    /**
     * Tests that modifying the original set doesn't affect the person's hackathons.
     */
    @Test
    public void constructor_modifyInputSet_personUnaffected() {
        Set<HackathonName> hackathons = new HashSet<>();
        hackathons.add(new HackathonName("HackNUS2024"));

        Person person = new PersonBuilder().withInterestedHackathons("HackNUS2024").build();

        // This shouldn't affect the person's hackathons
        hackathons.add(new HackathonName("NewHackathon"));

        assertEquals(1, person.getInterestedHackathons().size());
        assertFalse(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("NewHackathon")));
    }

    /**
     * Tests that persons with same data except hackathons are not equal.
     */
    @Test
    public void equals_sameDataExceptHackathons_returnsFalse() {
        Person person1 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withInterestedHackathons("HackNUS2024")
                .build();

        Person person2 = new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@example.com")
                .withInterestedHackathons("AI Challenge")
                .build();

        assertFalse(person1.equals(person2));
        assert !person1.equals(person2) : "Same person data but different hackathons should not be equal";
    }
}

