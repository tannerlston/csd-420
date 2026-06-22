// Tanner Elston, CSD 420, 6/13/26

package TannerCardGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {

    private final int cardNumber;

    public Card(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * Loads this card's image from the resources/cards/ directory
     * and returns a sized ImageView ready to add to the UI.
     */
    public ImageView getImageView() {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(120);
        imageView.setFitHeight(168);
        imageView.setPreserveRatio(true);

        String path = "/cards/" + cardNumber + ".png";
        var stream = getClass().getResourceAsStream(path);

        if (stream != null) {
            imageView.setImage(new Image(stream));
        } else {
            System.err.println("Image not found: " + path);
        }

        return imageView;
    }

    @Override
    public String toString() {
        return "Card{" + cardNumber + "}";
    }
}