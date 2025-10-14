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
        } else {
            hackathonName.setText("No hackathon assigned");
        }

        memberCount.setText("Members: " + team.getSize());

        // Set spacing for the FlowPane to add space between member labels
        members.setHgap(8); // Horizontal gap between items
        members.setVgap(4); // Vertical gap between rows

        // Display member names
        for (Person member : team.getMembers()) {
            Label memberLabel = new Label(member.getName().fullName);
            memberLabel.getStyleClass().add("cell_small_label");
            members.getChildren().add(memberLabel);
        }
    }
}
