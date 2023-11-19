import java.util.ArrayList;

/**
 * Represents a player in a card game.
 */
public class Player {
    private ArrayList<Card> hand;
    private int balance;

    /**
     * Constructs a new Player instance with a specified balance.
     *
     * @param balance the initial balance of the player
     */
    public Player(int balance) {
        this.hand = new ArrayList<Card>();
        this.balance = balance;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the Card to add
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /**
     * Replaces a card at the specified index in the player's hand with a new card.
     *
     * @param index   the index at which to replace the card
     * @param newCard the new Card to place at the specified index
     */
    public void replaceCardInHand(int index, Card newCard) {
        hand.set(index, newCard);
    }

    /**
     * Returns the player's current balance.
     *
     * @return the player's balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the player's balance.
     *
     * @param balance the new balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Returns the player's hand.
     *
     * @return an ArrayList of Cards representing the player's hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }
}