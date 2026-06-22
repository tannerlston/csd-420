// Tanner Elston, CSD 420, 6/13/26

package TannerCardGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private static final int DECK_SIZE = 52;

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // Lambda expression: populate the deck with cards 1–52
        for (int i = 1; i <= DECK_SIZE; i++) {
            cards.add(new Card(i));
        }
    }

    /**
     * Shuffles the deck in place.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Shuffles and returns the requested number of cards from the top.
     *
     * @param count how many cards to deal
     * @return a list of randomly selected Card objects
     */
    public List<Card> dealCards(int count) {
        shuffle();
        // Lambda expression: stream the first 'count' cards into a new list
        return cards.stream()
                .limit(count)
                .collect(java.util.stream.Collectors.toList());
    }

    public int size() {
        return cards.size();
    }
}