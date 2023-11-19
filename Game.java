import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class contains all the logic and GUI setup for the card game.
 */
public class Game {
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private int currentBet;
    private boolean roundOver;

    // GUI components
    private JFrame frame;
    private JLabel playerBalanceLabel, importantMessageLabel, betGuideLabel;
    private JTextField betField;
    private JButton startButton, evaluateButton;
    private JButton[] replaceCardButtons;
    private JLabel[] playerCardLabels, dealerCardLabels;

    public Game() {
        player = new Player(100); // Starting balance is $100
        dealer = new Dealer();
        deck = new Deck();
        playerCardLabels = new JLabel[3];
        dealerCardLabels = new JLabel[3];
        setupGUI();
    }

    /**
     * Starts a new round of the game where the player and dealer both get three
     * cards.
     * Validates and deducts the player's bet from their balance.
     */
    public void startRound() {
        try {
            int bet = Integer.parseInt(betField.getText());
            if (bet > player.getBalance()) {
                JOptionPane.showMessageDialog(frame, "You can't bet more than your balance!");
                return;
            } else {
                currentBet = bet;
                player.setBalance(player.getBalance() - bet);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid bet.");
            return;
        }

        roundOver = false;
        deck.shuffle();
        for (int i = 0; i < 3; i++) {
            player.addCardToHand(deck.drawCard());
            dealer.addCardToHand(deck.drawCard());
        }
        evaluateButton.setEnabled(true);
        startButton.setEnabled(false);

        // Enable the replace card buttons and show the player's cards
        for (int i = 0; i < replaceCardButtons.length; i++) {
            replaceCardButtons[i].setEnabled(true);
            Card card = player.getHand().get(i);
            String fileName = card.getFileName();
            playerCardLabels[i].setIcon(new ImageIcon("Images/" + fileName));
        }

        updateGUI();
    }

    /**
     * Allows the player to replace a card in their hand. Also updates the card's
     * image on the GUI.
     * 
     * @param cardIndex The index of the card in the player's hand to be replaced.
     */
    public void playerReplaceCard(int cardIndex) {
        player.replaceCardInHand(cardIndex, deck.drawCard());

        // Update the replaced card's image
        Card card = player.getHand().get(cardIndex);
        String fileName = card.getFileName();
        playerCardLabels[cardIndex].setIcon(new ImageIcon("Images/" + fileName));
        replaceCardButtons[cardIndex].setEnabled(false);

        updateGUI();
    }

    /**
     * Evaluates the result of the round based on the count of special cards
     * and the remainder of the sum of the ranks of non-special cards.
     */
    public void evaluateRound() {
        roundOver = true;
        int playerSpecialCards = countSpecialCards(player.getHand());
        int dealerSpecialCards = countSpecialCards(dealer.getHand());

        // Show the dealer's cards
        for (int i = 0; i < dealerCardLabels.length; i++) {
            Card card = dealer.getHand().get(i);
            String fileName = card.getFileName();
            dealerCardLabels[i].setIcon(new ImageIcon("Images/" + fileName));
        }

        if (playerSpecialCards > dealerSpecialCards) {
            player.setBalance(player.getBalance() + currentBet * 2); // Win $10
            JOptionPane.showMessageDialog(frame, "Player wins the round!");
        } else if (playerSpecialCards < dealerSpecialCards) {
            JOptionPane.showMessageDialog(frame, "Dealer wins the round!");
        } else { // If number of special cards is the same
            int playerRemainder = calculateRemainder(player.getHand());
            int dealerRemainder = calculateRemainder(dealer.getHand());

            if (playerRemainder > dealerRemainder) {
                player.setBalance(player.getBalance() + currentBet * 2); // Win $10
                JOptionPane.showMessageDialog(frame, "Player wins the round!");
            } else { // If remainders are the same, or dealer's remainder is larger
                JOptionPane.showMessageDialog(frame, "Dealer wins the round!");
            }
        }

        // Clear hands for the next round
        player.getHand().clear();
        dealer.getHand().clear();

        currentBet = 0;
        startButton.setEnabled(true);
        evaluateButton.setEnabled(false);

        updateGUI();
    }

    /**
     * Counts the special cards in a hand.
     * 
     * @param hand The hand to count the special cards in.
     * @return The count of special cards in the hand.
     */
    private int countSpecialCards(ArrayList<Card> hand) {
        int count = 0;
        for (Card card : hand) {
            if (card.isSpecial()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the remainder of the sum of the ranks of non-special cards.
     * 
     * @param hand The hand to calculate the remainder for.
     * @return The remainder of the sum of the ranks of non-special cards.
     */
    private int calculateRemainder(ArrayList<Card> hand) {
        int sum = 0;
        for (Card card : hand) {
            if (!card.isSpecial()) { // Exclude special cards
                if (card.getRank().equals("A")) {
                    sum += 1; // Ace = 1
                } else {
                    sum += Integer.parseInt(card.getRank());
                }
            }
        }
        return sum % 10;
    }

    /**
     * Sets up the GUI for the game, including card labels, buttons, and action
     * listeners.
     */
    public void setupGUI() {
        frame = new JFrame("Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        JMenu controlMenu = new JMenu("Control");
        JMenuItem quitMenuItem = new JMenuItem("Exit");
        quitMenuItem.addActionListener(e -> System.exit(0)); // Quit the game when clicked
        controlMenu.add(quitMenuItem);
        menuBar.add(controlMenu);
        frame.setJMenuBar(menuBar);

        playerBalanceLabel = new JLabel("Amount of money you have: $" + player.getBalance());
        importantMessageLabel = new JLabel("Please place your bet!");

        betGuideLabel = new JLabel("Bet: $");
        betField = new JTextField();
        betField.setColumns(10);
        betField.setMaximumSize(betField.getPreferredSize());

        startButton = new JButton("Start");
        startButton.addActionListener(e -> startRound());
        startButton.setEnabled(true);

        evaluateButton = new JButton("Evaluate");
        evaluateButton.addActionListener(e -> evaluateRound());
        evaluateButton.setEnabled(false);

        replaceCardButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            replaceCardButtons[i] = new JButton("Replace Card " + (i + 1));
            int cardIndex = i;
            replaceCardButtons[i].addActionListener(e -> playerReplaceCard(cardIndex));
            replaceCardButtons[i].setEnabled(false);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(Color.GREEN);

        Box dealerBox = Box.createHorizontalBox();
        Box playerBox = Box.createHorizontalBox();
        Box replacementBox = Box.createHorizontalBox();
        Box actionBox = Box.createHorizontalBox();
        Box messageBox = Box.createHorizontalBox();

        ImageIcon backsideImage = new ImageIcon("Images/back.gif");

        // Initialize dealer's card labels
        for (int i = 0; i < dealerCardLabels.length; i++) {
            dealerCardLabels[i] = new JLabel(backsideImage);
            dealerBox.add(dealerCardLabels[i]);
            dealerBox.add(Box.createHorizontalStrut(10));
        }

        // Initialize player's card labels
        for (int i = 0; i < playerCardLabels.length; i++) {
            playerCardLabels[i] = new JLabel(backsideImage);
            playerBox.add(playerCardLabels[i]);
            playerBox.add(Box.createHorizontalStrut(10));
        }

        // Add replace card buttons
        for (JButton replaceCardButton : replaceCardButtons) {
            replacementBox.add(replaceCardButton);
        }

        actionBox.add(betGuideLabel);
        actionBox.add(Box.createHorizontalStrut(5));
        actionBox.add(betField);
        actionBox.add(Box.createHorizontalStrut(10));
        actionBox.add(startButton);
        actionBox.add(Box.createHorizontalStrut(10));
        actionBox.add(evaluateButton);

        messageBox.add(importantMessageLabel);
        messageBox.add(Box.createHorizontalStrut(10));
        messageBox.add(playerBalanceLabel);
        messageBox.add(Box.createHorizontalStrut(10));

        // Add all boxes to the panel
        panel.add(Box.createVerticalStrut(20));
        panel.add(dealerBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(playerBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(replacementBox);
        panel.add(Box.createVerticalStrut(20));
        panel.add(actionBox);
        panel.add(Box.createVerticalStrut(10));
        panel.add(messageBox);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Updates the GUI based on the state of the game (e.g., current bet, player's
     * balance).
     */
    public void updateGUI() {
        if (player.getBalance() <= 0 && roundOver) {
            importantMessageLabel.setText("You have no more money! Please start a new game.");
            JOptionPane.showMessageDialog(frame, "Game over! You have no more money. Please start a new game.");
            startButton.setEnabled(false);
            evaluateButton.setEnabled(false);
            for (int i = 0; i < 3; i++) {
                replaceCardButtons[i].setEnabled(false);
            }
        } else if (startButton.isEnabled()) {
            importantMessageLabel.setText("Please place your bet!");
        } else {
            importantMessageLabel.setText("Your current bet is: $" + currentBet);
        }

        playerBalanceLabel.setText("Money you have: $" + player.getBalance());

        int replacedEnabledCount = 0;
        for (int i = 0; i < 3; i++) {
            if (replaceCardButtons[i].isEnabled()) {
                replacedEnabledCount++;
            }
        }
        if (replacedEnabledCount == 1) {
            for (int i = 0; i < 3; i++) {
                if (replaceCardButtons[i].isEnabled()) {
                    replaceCardButtons[i].setEnabled(false);
                }
            }
        }

        if (roundOver) {
            for (int i = 0; i < dealerCardLabels.length; i++) {
                dealerCardLabels[i].setIcon(new ImageIcon("Images/" + "back.gif"));
                playerCardLabels[i].setIcon(new ImageIcon("Images/" + "back.gif"));
            }
            for (int i = 0; i < 3; i++) {
                replaceCardButtons[i].setEnabled(false);
            }
        }
    }

    /**
     * Main method to start the game.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Game();
    }
}