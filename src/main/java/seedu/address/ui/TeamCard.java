package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * An UI component that displays information of a {@code Team}.
 */
public class TeamCard extends UiPart<Region> {

    private static final String FXML = "TeamListCard.fxml";

    public final Team team;

    @FXML
    private HBox cardPane;
    @FXML
    private Label teamName;
    @FXML
    private Label id;
    @FXML
    private Label hackathonName;
    @FXML
    private Label memberCount;
    @FXML
    private FlowPane members;

    /**
     * Creates a {@code TeamCard} with the given {@code Team} and index to display.
     */
    public TeamCard(Team team, int displayedIndex) {
        super(FXML);
        this.team = team;
        id.setText(displayedIndex + ". ");
        teamName.setText(team.getTeamName().fullTeamName);

        if (team.getHackathonName() != null) {
            hackathonName.setText("Hackathon: " + team.getHackathonName().value);
            hackathonName.setStyle("-fx-background-color: #fed7aa; "
                    + "-fx-text-fill: #c2410c; "
                    + "-fx-padding: 3 7 3 7; "
                    + "-fx-background-radius: 35; "
                    + "-fx-border-color: #fb923c; "
                    + "-fx-border-width: 1.25; "
                    + "-fx-border-radius: 20; "
                    + "-fx-font-size: 12px; "
                    + "-fx-font-weight: bold; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 1);");
        } else {
            hackathonName.setText("Hackathon: No hackathon");
            hackathonName.setStyle("-fx-text-fill: #888888; "
                    + "-fx-font-size: 12px; "
                    + "-fx-font-style: italic;");
        }

        memberCount.setText("Members: " + team.getSize());
        memberCount.setStyle("-fx-font-size: 13px; "
                + "-fx-text-fill: #b8b8b8;");

        // Set spacing for the FlowPane to add space between member labels
        members.setHgap(6);
        members.setVgap(6);

        // Display member names with modern tag styling
        for (Person member : team.getMembers()) {
            Label memberLabel = new Label(member.getName().fullName);
            memberLabel.setStyle("-fx-background-color: #e8d4f7; "
                    + "-fx-text-fill: #6a1b9a; "
                    + "-fx-padding: 3 7 3 7; "
                    + "-fx-background-radius: 35; "
                    + "-fx-border-color: #ba68c8; "
                    + "-fx-border-width: 1.25; "
                    + "-fx-border-radius: 20; "
                    + "-fx-font-size: 12px; "
                    + "-fx-font-weight: bold; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0, 0, 1);");
            members.getChildren().add(memberLabel);
        }
    }
}
