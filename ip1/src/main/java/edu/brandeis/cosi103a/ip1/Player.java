package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {  private final String name;
    private final List<Card> deck;
    private final List<Card> hand;
    private final List<Card> discardPile;
    private final List<Card> playedCards;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.playedCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    /**
     * Adds a card to the player's discard pile.
     */
    public void addToDiscard(Card card) {
        discardPile.add(card);
    }

    /**
     * Adds a card to the player's deck (used during setup).
     */
    public void addToDeck(Card card) {
        deck.add(card);
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Draws a specified number of cards from the deck.
     * If the deck is empty, shuffles the discard pile into the deck.
     */
    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            if (deck.isEmpty()) {
                // Shuffle discard pile into deck
                deck.addAll(discardPile);
                discardPile.clear();
                shuffleDeck();
            }

            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
    }

    /**
     * Plays all cryptocurrency cards from hand and returns total coin value.
     */
    public int playAllCryptocurrency() {
        int totalCoins = 0;
        List<Card> toPlay = new ArrayList<>();

        for (Card card : hand) {
            if (!card.isAutomationCard()) {
                toPlay.add(card);
                totalCoins += card.getValue();
            }
        }

        // Move played cards from hand to played cards
        for (Card card : toPlay) {
            hand.remove(card);
            playedCards.add(card);
        }

        return totalCoins;
    }

    /**
     * Attempts to buy a card with the given coins.
     * Returns the name of the card bought, or null if no purchase made.
     */
    public String buyCard(Supply supply, int coins) {
        // Simple buying strategy: buy the most expensive automation card possible,
        // or if no automation cards are affordable, buy the most expensive cryptocurrency
        String[] autoPriority = {"Framework", "Module", "Method"};
        String[] cryptoPriority = {"Dogecoin", "Ethereum", "Bitcoin"};

        // Try to buy automation cards first
        for (String cardName : autoPriority) {
            if (supply.isAvailable(cardName) && supply.getCardCost(cardName) <= coins) {
                Card bought = supply.takeCard(cardName);
                if (bought != null) {
                    addToDiscard(bought);
                    return cardName;
                }
            }
        }

        // If no automation card affordable, buy cryptocurrency
        for (String cardName : cryptoPriority) {
            if (supply.isAvailable(cardName) && supply.getCardCost(cardName) <= coins) {
                Card bought = supply.takeCard(cardName);
                if (bought != null) {
                    addToDiscard(bought);
                    return cardName;
                }
            }
        }

        return null;
    }

    /**
     * Performs the cleanup phase: discards hand and played cards, draws new hand.
     */
    public void cleanup() {
        // Discard all cards in hand
        discardPile.addAll(hand);
        hand.clear();

        // Discard all played cards
        discardPile.addAll(playedCards);
        playedCards.clear();

        // Draw 5 new cards
        drawCards(5);
    }

    /**
     * Calculates total automation points in player's deck, hand, and discard pile.
     */
    public int calculateAutomationPoints() {
        int total = 0;

        for (Card card : deck) {
            if (card.isAutomationCard()) {
                total += card.getValue();
            }
        }

        for (Card card : hand) {
            if (card.isAutomationCard()) {
                total += card.getValue();
            }
        }

        for (Card card : discardPile) {
            if (card.isAutomationCard()) {
                total += card.getValue();
            }
        }

        for (Card card : playedCards) {
            if (card.isAutomationCard()) {
                total += card.getValue();
            }
        }

        return total;
    }

    /**
     * Returns total number of cards owned by this player.
     */
    public int getTotalCardCount() {
        return deck.size() + hand.size() + discardPile.size() + playedCards.size();
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getDiscardSize() {
        return discardPile.size();
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getPlayedCardsSize() {
        return playedCards.size();
    }
    
}
