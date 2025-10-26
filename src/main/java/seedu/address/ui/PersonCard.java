package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.skill.ExperienceLevel;

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
    private HBox currentHackathonsContainer;
    @FXML
    private FlowPane currentHackathons;

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

        // Load and set icons
        Image emailImage = new Image(getClass().getResourceAsStream("/images/Email.png"));
        emailIcon.setImage(emailImage);

        Image telegramImage = new Image(getClass().getResourceAsStream("/images/Telegram.png"));
        telegramIcon.setImage(telegramImage);

        Image githubImage = new Image(getClass().getResourceAsStream("/images/Github.png"));
        githubIcon.setImage(githubImage);

        // Set spacing for skills FlowPane
        skills.setHgap(5);
        skills.setVgap(5);

        // Display skills
        person.getSkills().stream()
                .sorted(Comparator.comparing(skill -> skill.skillName))
                .forEach(skill -> {
                    Label skillLabel = new Label(skill.skillName);
                    skillLabel.getStyleClass().add("skill-label");

                    // Set background color based on experience level
                    String backgroundColor = getColorForExperienceLevel(skill.getExperienceLevel());
                    skillLabel.setStyle("-fx-background-color: " + backgroundColor + "; "
                            + "-fx-padding: 3 7 3 7; "
                            + "-fx-background-radius: 3; "
                            + "-fx-text-fill: #000000;");

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
                        hackathonLabel.setStyle("-fx-background-color: #d4e6f1; "
                                + "-fx-padding: 3 7 3 7; "
                                + "-fx-background-radius: 3; "
                                + "-fx-text-fill: #1565C0;");
                        hackathons.getChildren().add(hackathonLabel);
                    });
        }

        // Set spacing for currentHackathons FlowPane
        currentHackathons.setHgap(5);
        currentHackathons.setVgap(5);

        // Display current hackathons (participating) with light orange
        if (person.getCurrentHackathons().isEmpty()) {
            // Hide the entire participating hackathons section if empty
            currentHackathonsContainer.setVisible(false);
            currentHackathonsContainer.setManaged(false);
        } else {
            person.getCurrentHackathons().stream()
                    .sorted(Comparator.comparing(hackathon -> hackathon.value))
                    .forEach(hackathon -> {
                        Label hackathonLabel = new Label(hackathon.value);
                        hackathonLabel.setStyle("-fx-background-color: #ffe4b3; "
                                + "-fx-padding: 3 7 3 7; "
                                + "-fx-background-radius: 3; "
                                + "-fx-text-fill: #E65100;");
                        currentHackathons.getChildren().add(hackathonLabel);
                    });
        }
    }

    /**
     * Returns the background color for a skill based on its experience level.
     */
    private String getColorForExperienceLevel(ExperienceLevel level) {
        switch (level) {
        case BEGINNER:
            return "#b8f5b8"; // Light green
        case INTERMEDIATE:
            return "#fff4b8"; // Light yellow
        case ADVANCED:
            return "#ffb8b8"; // Light red
        default:
            return "#e0e0e0"; // Default gray
        }
    }
}
