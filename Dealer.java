import java.util.ArrayList;

/**
 * Represents a dealer in a card game.
 */
public class Dealer {
    private ArrayList<Card> hand;

    /**
     * Constructs a new Dealer instance.
     */
    public Dealer() {
        this.hand = new ArrayList<Card>();
    }

    /**
     * Adds a card to the dealer's hand.
     *
     * @param card the Card to add
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /**
     * Returns the dealer's hand.
     *
     * @return an ArrayList of Cards representing the dealer's hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }
}