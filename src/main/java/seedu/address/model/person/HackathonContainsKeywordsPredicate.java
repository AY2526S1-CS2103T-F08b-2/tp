package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s interested hackathons contain any of the keywords given.
 */
public class HackathonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public HackathonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (person.getInterestedHackathons().isEmpty()) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> person.getInterestedHackathons().stream()
                        .anyMatch(hackathon -> StringUtil.containsWordIgnoreCase(hackathon.value, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HackathonContainsKeywordsPredicate)) {
            return false;
        }

        HackathonContainsKeywordsPredicate otherPredicate = (HackathonContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

