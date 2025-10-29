package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.skill.ExperienceLevel;
import seedu.address.model.skill.Skill;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label email;
    @FXML
    private Label telegram;
    @FXML
    private Label github;
    @FXML
    private ImageView emailIcon;
    @FXML
    private ImageView telegramIcon;
    @FXML
    private ImageView githubIcon;
    @FXML
    private FlowPane skills;
    @FXML
    private HBox interestedHackathonsContainer;
    @FXML
    private FlowPane hackathons;
    @FXML
    private HBox participatingHackathonsContainer;
    @FXML
    private FlowPane participatingHackathons;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        email.setText(person.getEmail().value);
        telegram.setText(person.getTelegram().value);
        github.setText(person.getGitHub().value);

        // Load and set icons with white color effect
        Image emailImage = new Image(getClass().getResourceAsStream("/images/mail.png"));
        emailIcon.setImage(emailImage);
        applyWhiteColorEffect(emailIcon);

        Image telegramImage = new Image(getClass().getResourceAsStream("/images/at-sign.png"));
        telegramIcon.setImage(telegramImage);
        applyWhiteColorEffect(telegramIcon);

        Image githubImage = new Image(getClass().getResourceAsStream("/images/github.png"));
        githubIcon.setImage(githubImage);
        applyWhiteColorEffect(githubIcon);

        // Set spacing for skills FlowPane
        skills.setHgap(5);
        skills.setVgap(5);

        // Display skills sorted by experience level (Advanced -> Intermediate -> Beginner)
        person.getSkills().stream()
                .sorted(Comparator.comparing(Skill::getExperienceLevel)
                        .reversed()
                        .thenComparing(skill -> skill.skillName))
                .forEach(skill -> {
                    Label skillLabel = new Label(skill.skillName);
                    skillLabel.getStyleClass().add("skill-label");

                    // Set styling based on experience level
                    String[] colors = getColorsForExperienceLevel(skill.getExperienceLevel());
                    skillLabel.setStyle("-fx-background-color: " + colors[0] + "; "
                            + "-fx-text-fill: " + colors[1] + "; "
                            + "-fx-padding: 3 7 3 7; "
                            + "-fx-background-radius: 35; "
                            + "-fx-border-color: " + colors[2] + "; "
                            + "-fx-border-width: 1.25; "
                            + "-fx-border-radius: 20; "
                            + "-fx-font-size: 12px; "
                            + "-fx-font-weight: bold; "
                            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 1);");

                    skills.getChildren().add(skillLabel);
                });


        // Set spacing for hackathons FlowPane
        hackathons.setHgap(5);
        hackathons.setVgap(5);

        // Display interested hackathons
        if (person.getInterestedHackathons().isEmpty()) {
            // Hide the entire interested hackathons section if empty
            interestedHackathonsContainer.setVisible(false);
            interestedHackathonsContainer.setManaged(false);
        } else {
            person.getInterestedHackathons().stream()
                    .sorted(Comparator.comparing(hackathon -> hackathon.value))
                    .forEach(hackathon -> {
                        Label hackathonLabel = new Label(hackathon.value);
                        hackathonLabel.setStyle("-fx-background-color: #dbeafe; "
                                + "-fx-text-fill: #1e40af; "
                                + "-fx-padding: 3 7 3 7; "
                                + "-fx-background-radius: 35; "
                                + "-fx-border-color: #60a5fa; "
                                + "-fx-border-width: 1.25; "
                                + "-fx-border-radius: 20; "
                                + "-fx-font-size: 12px; "
                                + "-fx-font-weight: bold; "
                                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 1);");
                        hackathons.getChildren().add(hackathonLabel);
                    });
        }

        // Set spacing for participatingHackathons FlowPane
        participatingHackathons.setHgap(5);
        participatingHackathons.setVgap(5);

        // Display current hackathons (participating) with light orange
        if (person.getParticipatingHackathons().isEmpty()) {
            // Hide the entire participating hackathons section if empty
            participatingHackathonsContainer.setVisible(false);
            participatingHackathonsContainer.setManaged(false);
        } else {
            person.getParticipatingHackathons().stream()
                    .sorted(Comparator.comparing(hackathon -> hackathon.value))
                    .forEach(hackathon -> {
                        Label hackathonLabel = new Label(hackathon.value);
                        hackathonLabel.setStyle("-fx-background-color: #fed7aa; "
                                + "-fx-text-fill: #c2410c; "
                                + "-fx-padding: 3 7 3 7; "
                                + "-fx-background-radius: 35; "
                                + "-fx-border-color: #fb923c; "
                                + "-fx-border-width: 1.25; "
                                + "-fx-border-radius: 20; "
                                + "-fx-font-size: 12px; "
                                + "-fx-font-weight: bold; "
                                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 1);");
                        participatingHackathons.getChildren().add(hackathonLabel);
                    });
        }
    }

    /**
     * Returns the background color, text color, and border color for a skill based on its experience level.
     * Returns array: [backgroundColor, textColor, borderColor]
     */
    private String[] getColorsForExperienceLevel(ExperienceLevel level) {
        switch (level) {
        case BEGINNER:
            // Soft green theme - calm and fresh
            return new String[]{"#d4f4dd", "#1e7a3e", "#7bc96f"};
        case INTERMEDIATE:
            // Vibrant blue theme - confident and professional
            return new String[]{"#d4e6f9", "#1565C0", "#64b5f6"};
        case ADVANCED:
            // Bold purple theme - expert and premium
            return new String[]{"#e8d4f7", "#6a1b9a", "#ba68c8"};
        default:
            // Neutral gray
            return new String[]{"#e8e8e8", "#424242", "#9e9e9e"};
        }
    }

    /**
     * Applies a white color effect to the given ImageView.
     */
    private void applyWhiteColorEffect(ImageView imageView) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(1.0);
        imageView.setEffect(colorAdjust);
    }
}
