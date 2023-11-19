/**
 * Represents a playing card with a rank, suit, and a special attribute.
 */
public class Card {
    private String rank;
    private String suit;
    private boolean isSpecial;

    /**
     * Constructs a new Card instance.
     *
     * @param rank      the rank of the card (e.g., "A", "2", ..., "J", "Q", "K")
     * @param suit      the suit of the card (e.g., "Clubs", "Spades", "Diamonds",
     *                  "Hearts")
     * @param isSpecial whether the card is special
     */
    public Card(String rank, String suit, boolean isSpecial) {
        this.rank = rank;
        this.suit = suit;
        this.isSpecial = isSpecial;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns whether the card is special.
     *
     * @return true if the card is special, false otherwise
     */
    public boolean isSpecial() {
        return isSpecial;
    }

    /**
     * Returns the filename of the card's image based on its rank and suit.
     * The filename is in the format "card_<suit><rank>.gif".
     * The suit is represented as 1 for Clubs, 2 for Spades, 3 for Diamonds, 4 for
     * Hearts.
     * The rank is represented as 1 for Ace, 11 for Jack, 12 for Queen, 13 for King,
     * and the card number for other ranks.
     *
     * @return the filename of the card's image
     * @throws IllegalArgumentException if the suit is not one of the expected
     *                                  values
     */
    public String getFileName() {
        String name = "card_";
        name += switch (getSuit()) {
            case "Clubs" -> "1";
            case "Spades" -> "2";
            case "Diamonds" -> "3";
            case "Hearts" -> "4";
            default -> throw new IllegalArgumentException("Invalid suit: " + getSuit());
        };
        name += switch (getRank()) {
            case "A" -> "1";
            case "J" -> "11";
            case "Q" -> "12";
            case "K" -> "13";
            default -> getRank();
        };
        return name + ".gif";
    }
}