// Tanner Elston, CSD 420, 6/13/26

package TannerCardGame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

// Executable with Maven

public class CardDisplay extends Application {

    private static final int CARDS_TO_SHOW = 4;

    private final Deck deck = new Deck();
    private HBox cardBox;

    @Override
    public void start(Stage primaryStage) {
        // Card display area
        cardBox = new HBox(15);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(20));

        // Show initial four cards
        displayRandomCards();

        // Refresh button with lambda expression
        Button refreshButton = new Button("Refresh Cards");
        refreshButton.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-padding: 10 24 10 24;" +
                        "-fx-background-color: #2c6fad;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 6;"
        );
        refreshButton.setOnAction(e -> displayRandomCards()); // Lambda expression

        // Root layout
        VBox root = new VBox(20, cardBox, refreshButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1a6b3c;");

        primaryStage.setTitle("Random Card Display");
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Asks the Deck for 4 random cards and renders their ImageViews.
     * Lambda expression used in forEach to add each card to the UI.
     */
    private void displayRandomCards() {
        List<Card> hand = deck.dealCards(CARDS_TO_SHOW);
        cardBox.getChildren().clear();
        hand.forEach(card -> cardBox.getChildren().add(card.getImageView())); // Lambda
    }

    public static void main(String[] args) {
        launch(args);
    }
}