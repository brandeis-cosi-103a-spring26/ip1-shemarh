package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Dice Rolling Game
 * Two players take turns rolling a 6-sided die, with the option to re-roll up to 2 times.
 * After 10 turns each, the player with the highest score wins.
 */
public class App 
{
    public static final int NUM_TURNS = 10;
    public static final int MAX_REROLLS = 2;
    public static final int DIE_SIDES = 6;
    
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        System.out.println("========================================");
        System.out.println("     Welcome to the Dice Rolling Game!");
        System.out.println("========================================\n");
        
        // Get player names
        System.out.print("Enter Player 1's name: ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }
        
        System.out.print("Enter Player 2's name: ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }
        
        System.out.println("\n----------------------------------------");
        System.out.println("Game Rules:");
        System.out.println("- Each player gets " + NUM_TURNS + " turns");
        System.out.println("- On each turn, roll a " + DIE_SIDES + "-sided die");
        System.out.println("- You can re-roll up to " + MAX_REROLLS + " times per turn");
        System.out.println("- The final die value is added to your score");
        System.out.println("- Highest score wins!");
        System.out.println("----------------------------------------\n");
        
        int player1Score = 0;
        int player2Score = 0;
        
        // Play 10 turns for each player
        for (int turn = 1; turn <= NUM_TURNS; turn++) {
            System.out.println("===== TURN " + turn + " =====");
            
            // Player 1's turn
            System.out.println("\n" + player1Name + "'s turn:");
            int player1TurnScore = playTurn(player1Name);
            player1Score += player1TurnScore;
            System.out.println(player1Name + " scores: " + player1TurnScore + " | Total: " + player1Score);
            
            // Player 2's turn
            System.out.println("\n" + player2Name + "'s turn:");
            int player2TurnScore = playTurn(player2Name);
            player2Score += player2TurnScore;
            System.out.println(player2Name + " scores: " + player2TurnScore + " | Total: " + player2Score);
            
            System.out.println();
        }
        
        // Display final results
        System.out.println("========================================");
        System.out.println("           GAME OVER - FINAL RESULTS");
        System.out.println("========================================");
        System.out.println(player1Name + " - Total Score: " + player1Score);
        System.out.println(player2Name + " - Total Score: " + player2Score);
        System.out.println("========================================");
        
        if (player1Score > player2Score) {
            System.out.println("\nCongratulations! " + player1Name + " WINS!");
        } else if (player2Score > player1Score) {
            System.out.println("\nCongratulations! " + player2Name + " WINS!");
        } else {
            System.out.println("\nIt's a TIE! Both players scored " + player1Score + " points!");
        }
        
        scanner.close();
    }
    
    /**
     * Handles a single player's turn
     * @param playerName the name of the player taking their turn
     * @return the final score for this turn
     */
    private static int playTurn(String playerName) {
        int currentValue = rollDie();
        System.out.println("First roll: " + currentValue);
        
        int rerollsUsed = 0;
        
        // Allow up to 2 re-rolls
        while (rerollsUsed < MAX_REROLLS) {
            System.out.print("Reroll? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("y") || response.equals("yes")) {
                currentValue = rollDie();
                System.out.println("You rolled: " + currentValue);
                rerollsUsed++;
            } else {
                break;
            }
        }
        
        System.out.println("Final roll: " + currentValue);
        return currentValue;
    }
    
    /**
     * Rolls a 6-sided die
     * @return a random number between 1 and 6
     */
    public static int rollDie() {
        return random.nextInt(DIE_SIDES) + 1;
    }
}
