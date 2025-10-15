package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Skill}.
 */
class JsonAdaptedSkill {

    private final String skillName;
    private final String experienceLevel;

    /**
     * Constructs a {@code JsonAdaptedSkill} with the given skill details.
     */
    @JsonCreator
    public JsonAdaptedSkill(@JsonProperty("skillName") String skillName,
                            @JsonProperty("experienceLevel") String experienceLevel) {
        this.skillName = skillName;
        this.experienceLevel = experienceLevel;
    }

    /**
     * Converts a given {@code Skill} into this class for Jackson use.
     */
    public JsonAdaptedSkill(Skill source) {
        skillName = source.skillName;
        experienceLevel = source.experienceLevel.name();
    }

    @JsonProperty("skillName")
    public String getSkillName() {
        return skillName;
    }

    @JsonProperty("experienceLevel")
    public String getExperienceLevel() {
        return experienceLevel;
    }

    /**
     * Converts this Jackson-friendly adapted skill object into the model's {@code Skill} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted skill.
     */
    public Skill toModelType() throws IllegalValueException {
        if (!Skill.isValidSkillName(skillName)) {
            throw new IllegalValueException(Skill.MESSAGE_CONSTRAINTS);
        }

        // Handle backward compatibility - if no experience level is specified, default to BEGINNER
        ExperienceLevel level = ExperienceLevel.BEGINNER;
        if (experienceLevel != null && !experienceLevel.isEmpty()) {
            if (!ExperienceLevel.isValidExperienceLevel(experienceLevel)) {
                throw new IllegalValueException(ExperienceLevel.MESSAGE_CONSTRAINTS);
            }
            level = ExperienceLevel.fromString(experienceLevel);
        }

        return new Skill(skillName, level);
    }

}
