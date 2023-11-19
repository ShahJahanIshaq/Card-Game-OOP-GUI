import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of playing cards.
 */
public class Deck {
    private ArrayList<Card> cards;

    /**
     * Constructs a new Deck instance and initializes it with 52 standard playing
     * cards,
     * then shuffles the deck.
     */
    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit, rank.equals("J") || rank.equals("Q") || rank.equals("K")));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck and removes it from the deck.
     *
     * @return the drawn Card
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}