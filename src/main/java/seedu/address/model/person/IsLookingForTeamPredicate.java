package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code isLookingForTeam} status matches the specified value.
 */
public class IsLookingForTeamPredicate implements Predicate<Person> {

    private final boolean isLookingForTeam;

    public IsLookingForTeamPredicate(boolean isLookingForTeam) {
        this.isLookingForTeam = isLookingForTeam;
    }

    @Override
    public boolean test(Person person) {
        return person.isLookingForTeam() == isLookingForTeam;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IsLookingForTeamPredicate)) {
            return false;
        }

        IsLookingForTeamPredicate otherPredicate = (IsLookingForTeamPredicate) other;
        return isLookingForTeam == otherPredicate.isLookingForTeam;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isLookingForTeam", isLookingForTeam)
                .toString();
    }
}
