package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSkills().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withSkills(VALID_SKILL_PYTHON).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true (case-insensitive)
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different skills -> returns false
        editedAlice = new PersonBuilder(ALICE).withSkills(VALID_SKILL_PYTHON).build();
        assertFalse(ALICE.equals(editedAlice));

        // different telegram -> returns false
        editedAlice = new PersonBuilder(ALICE).withTelegram("differentTelegram").build();
        assertFalse(ALICE.equals(editedAlice));

        // different github -> returns false
        editedAlice = new PersonBuilder(ALICE).withGitHub("differentGithub").build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", email=" + ALICE.getEmail()
                + ", telegram=" + ALICE.getTelegram() + ", github=" + ALICE.getGitHub()
                + ", skills=" + ALICE.getSkills() + ", teams="
                + ALICE.getTeams()
                + ", interestedHackathons=" + ALICE.getInterestedHackathons()
                + ", participatingHackathons=" + ALICE.getParticipatingHackathons() + "}";
        assertEquals(expected, ALICE.toString());
    }

    /**
     * Tests that a person with no interested hackathons returns an empty set.
     */
    @Test
    public void getInterestedHackathons_noHackathons_returnsEmptySet() {
        Person person = new PersonBuilder().build();
        assertTrue(person.getInterestedHackathons().isEmpty());
        assert person.getInterestedHackathons().isEmpty() : "Expected empty hackathons set";
    }

    /**
     * Tests that a person with single interested hackathon returns correct set.
     */
    @Test
    public void getInterestedHackathons_singleHackathon_returnsCorrectSet() {
        Person person = new PersonBuilder().withInterestedHackathons("HackNUS2024").build();
        assertEquals(1, person.getInterestedHackathons().size());
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assert person.getInterestedHackathons().size() == 1 : "Expected exactly one hackathon";
    }

    /**
     * Tests that a person with multiple interested hackathons returns correct set.
     */
    @Test
    public void getInterestedHackathons_multipleHackathons_returnsCorrectSet() {
        Person person = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge 2024", "Web Dev Contest").build();
        assertEquals(3, person.getInterestedHackathons().size());
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
        assertTrue(person.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("Web Dev Contest")));
        assert person.getInterestedHackathons().size() == 3 : "Expected exactly three hackathons";
    }

    /**
     * Tests that the interested hackathons set is immutable.
     */
    @Test
    public void getInterestedHackathons_modifySet_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().withInterestedHackathons("HackNUS2024").build();
        assertThrows(UnsupportedOperationException.class, () ->
                person.getInterestedHackathons().clear());
    }

    /**
     * Tests that two persons with different interested hackathons are not equal.
     */
    @Test
    public void equals_differentInterestedHackathons_returnsFalse() {
        Person alice = new PersonBuilder(ALICE).withInterestedHackathons("HackNUS2024").build();
        Person aliceWithDifferentHackathons = new PersonBuilder(ALICE)
                .withInterestedHackathons("AI Challenge 2024").build();
        assertFalse(alice.equals(aliceWithDifferentHackathons));
        assert !alice.equals(aliceWithDifferentHackathons) : "Persons with different hackathons should not be equal";
    }

    /**
     * Tests that two persons with same interested hackathons are equal.
     */
    @Test
    public void equals_sameInterestedHackathons_returnsTrue() {
        Person alice = new PersonBuilder(ALICE).withInterestedHackathons("HackNUS2024").build();
        Person aliceCopy = new PersonBuilder(ALICE).withInterestedHackathons("HackNUS2024").build();
        assertTrue(alice.equals(aliceCopy));
        assert alice.equals(aliceCopy) : "Persons with same hackathons should be equal";
    }

    /**
     * Tests that hashCode is consistent with equals for interested hackathons.
     */
    @Test
    public void hashCode_sameInterestedHackathons_returnsSameHashCode() {
        Person alice = new PersonBuilder(ALICE).withInterestedHackathons("HackNUS2024").build();
        Person aliceCopy = new PersonBuilder(ALICE).withInterestedHackathons("HackNUS2024").build();
        assertEquals(alice.hashCode(), aliceCopy.hashCode());
        assert alice.hashCode() == aliceCopy.hashCode() : "Equal persons should have same hash code";
    }

    /**
     * Tests that persons with empty vs non-empty hackathons are not equal.
     */
    @Test
    public void equals_emptyVsNonEmptyHackathons_returnsFalse() {
        Person personWithNoHackathons = new PersonBuilder(ALICE).build();
        Person personWithHackathons = new PersonBuilder(ALICE)
                .withInterestedHackathons("HackNUS2024").build();
        assertFalse(personWithNoHackathons.equals(personWithHackathons));
        assertFalse(personWithHackathons.equals(personWithNoHackathons));
    }

    /**
     * Tests that interested hackathons are correctly copied in PersonBuilder copy constructor.
     */
    @Test
    public void personBuilder_copyConstructor_copiesInterestedHackathons() {
        Person original = new PersonBuilder()
                .withInterestedHackathons("HackNUS2024", "AI Challenge 2024").build();
        Person copy = new PersonBuilder(original).build();
        assertEquals(original.getInterestedHackathons(), copy.getInterestedHackathons());
        assertTrue(copy.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("HackNUS2024")));
        assertTrue(copy.getInterestedHackathons().stream()
                .anyMatch(h -> h.value.equals("AI Challenge 2024")));
    }
}
