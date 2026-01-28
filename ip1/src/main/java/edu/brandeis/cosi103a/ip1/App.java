package edu.brandeis.cosi103a.ip1;


import java.util.Random;

/**
 * Dice Rolling Game
 * Two players take turns rolling a 6-sided die, with the option to re-roll up to 2 times.
 * After 10 turns each, the player with the highest score wins.
 */
public class App 
{
      private final Player player1;
    private final Player player2;
    private final Supply supply;
    private Player currentPlayer;
    private Player otherPlayer;
    private int turnCount;
    private final boolean verbose;

    public App(boolean verbose) {
        this.player1 = new Player("Player 1");
        this.player2 = new Player("Player 2");
        this.supply = new Supply();
        this.turnCount = 0;
        this.verbose = verbose;
    }

    public App() {
        this(false);
    }

    /**
     * Sets up the game with starting decks for both players.
     */
    public void setup() {
        setupPlayer(player1);
        setupPlayer(player2);

        // Randomly choose starting player
        Random rand = new Random();
        if (rand.nextBoolean()) {
            currentPlayer = player1;
            otherPlayer = player2;
        } else {
            currentPlayer = player2;
            otherPlayer = player1;
        }

        if (verbose) {
            System.out.println("=== Game Setup Complete ===");
            System.out.println(currentPlayer.getName() + " goes first");
            System.out.println();
        }
    }

    /**
     * Sets up a player's starting deck: 7 Bitcoins and 3 Methods.
     */
    private void setupPlayer(Player player) {
        // Add 7 Bitcoins
        for (int i = 0; i < 7; i++) {
            Card bitcoin = supply.takeCard("Bitcoin");
            if (bitcoin != null) {
                player.addToDeck(bitcoin);
            }
        }

        // Add 3 Methods
        for (int i = 0; i < 3; i++) {
            Card method = supply.takeCard("Method");
            if (method != null) {
                player.addToDeck(method);
            }
        }

        // Shuffle and draw initial hand
        player.shuffleDeck();
        player.drawCards(5);
    }

    /**
     * Plays the entire game until completion.
     */
    public void play() {
        while (!isGameOver()) {
            playTurn();
        }

        if (verbose) {
            System.out.println("\n=== Game Over ===");
            announceWinner();
        }
    }

    /**
     * Plays a single turn for the current player.
     */
    private void playTurn() {
        turnCount++;

        if (verbose) {
            System.out.println("--- Turn " + turnCount + ": " + currentPlayer.getName() + " ---");
        }

        // Buy phase: play all cryptocurrency and buy a card
        int coins = currentPlayer.playAllCryptocurrency();

        if (verbose) {
            System.out.println("Coins available: " + coins);
        }

        String boughtCard = currentPlayer.buyCard(supply, coins);

        if (verbose) {
            if (boughtCard != null) {
                System.out.println("Bought: " + boughtCard);
            } else {
                System.out.println("No purchase made");
            }
        }

        // Cleanup phase: discard and draw new hand
        currentPlayer.cleanup();

        // Switch players
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;

        if (verbose) {
            System.out.println();
        }
    }

    /**
     * Checks if the game is over (all Frameworks purchased).
     */
    private boolean isGameOver() {
        return supply.areFrameworksEmpty();
    }

    /**
     * Determines and announces the winner.
     */
    public Player getWinner() {
        int p1Score = player1.calculateAutomationPoints();
        int p2Score = player2.calculateAutomationPoints();

        if (p1Score > p2Score) {
            return player1;
        } else if (p2Score > p1Score) {
            return player2;
        } else {
            // Tie - no winner
            return null;
        }
    }

    private void announceWinner() {
        int p1Score = player1.calculateAutomationPoints();
        int p2Score = player2.calculateAutomationPoints();

        System.out.println(player1.getName() + " Score: " + p1Score + " APs");
        System.out.println(player2.getName() + " Score: " + p2Score + " APs");

        Player winner = getWinner();
        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
        } else {
            System.out.println("Game ended in a tie!");
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Supply getSupply() {
        return supply;
    }

    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Main method to run a game.
     */
    public static void main(String[] args) {
         App game = new App(true);
        game.setup();
        game.play();
    }
}
