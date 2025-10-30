package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s fields match all of the keywords given.
 * Supports partial matching across name, email, GitHub, Telegram, skills, and hackathons.
 * All keywords must match for the person to be included (AND logic).
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsSubstringIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsSubstringIgnoreCase(person.getEmail().value, keyword)
                        || StringUtil.containsSubstringIgnoreCase(person.getGitHub().value, keyword)
                        || StringUtil.containsSubstringIgnoreCase(person.getTelegram().value, keyword)
                        || person.getSkills().stream()
                            .anyMatch(skill -> StringUtil.containsSubstringIgnoreCase(skill.skillName, keyword))
                        || person.getInterestedHackathons().stream()
                            .anyMatch(hackathon -> StringUtil.containsSubstringIgnoreCase(hackathon.value, keyword))
                        || person.getParticipatingHackathons().stream()
                            .anyMatch(hackathon -> StringUtil.containsSubstringIgnoreCase(hackathon.value, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
