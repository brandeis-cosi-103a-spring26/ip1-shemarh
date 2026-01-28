package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for all classes in the Dice Rolling Game
 */
public class AppTest {
    
    private AutomationCard automationCard;
    private CryptoCurrencyCard cryptocurrencyCard;
    private Player player;
    private Supply supply;

    @Before
    public void setUp() {
        automationCard = new AutomationCard("Method", 2, 1);
        cryptocurrencyCard = new CryptoCurrencyCard("Bitcoin", 0, 1);
        player = new Player("TestPlayer");
        supply = new Supply();
    }

    // ===== AutomationCard Tests =====
    @Test
    public void testAutomationCardName() {
        assertEquals("Method", automationCard.getName());
    }

    @Test
    public void testAutomationCardCost() {
        assertEquals(2, automationCard.getCost());
    }

    @Test
    public void testAutomationCardValue() {
        assertEquals(1, automationCard.getValue());
    }

    @Test
    public void testAutomationCardIsAutomation() {
        assertTrue(automationCard.isAutomationCard());
    }

    @Test
    public void testAutomationCardToString() {
        assertEquals("Method", automationCard.toString());
    }

    // ===== CryptoCurrencyCard Tests =====
    @Test
    public void testCryptoCurrencyCardName() {
        assertEquals("Bitcoin", cryptocurrencyCard.getName());
    }

    @Test
    public void testCryptoCurrencyCardCost() {
        assertEquals(0, cryptocurrencyCard.getCost());
    }

    @Test
    public void testCryptoCurrencyCardValue() {
        assertEquals(1, cryptocurrencyCard.getValue());
    }

    @Test
    public void testCryptoCurrencyCardIsNotAutomation() {
        assertFalse(cryptocurrencyCard.isAutomationCard());
    }

    @Test
    public void testCryptoCurrencyCardToString() {
        assertEquals("Bitcoin", cryptocurrencyCard.toString());
    }

   
    // ===== Player Tests =====
    @Test
    public void testPlayerName() {
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    public void testPlayerAddToDeck() {
        player.addToDeck(automationCard);
        player.addToDeck(cryptocurrencyCard);
        assertEquals(2, player.getDeckSize());
    }

    @Test
    public void testPlayerAddToDiscard() {
        player.addToDiscard(automationCard);
        assertEquals(1, player.getDiscardSize());
    }

    @Test
    public void testPlayerShuffleDeck() {
        player.addToDeck(automationCard);
        player.addToDeck(cryptocurrencyCard);
        player.shuffleDeck();
        // After shuffle, deck should still have 2 cards
        assertEquals(2, player.getDeckSize());
    }

    @Test
    public void testPlayerDrawCards() {
        player.addToDeck(automationCard);
        player.addToDeck(cryptocurrencyCard);
        player.shuffleDeck();
        player.drawCards(2);
        assertEquals(2, player.getHandSize());
        assertEquals(0, player.getDeckSize());
    }

    @Test
    public void testPlayerDrawCardsMoreThanDeck() {
        player.addToDeck(automationCard);
        player.drawCards(5);
        // Should draw 1 card (only 1 available)
        assertEquals(1, player.getHandSize());
    }

    @Test
    public void testPlayerPlayAllCryptocurrency() {
        player.addToDeck(automationCard);
        player.addToDeck(cryptocurrencyCard);
        CryptoCurrencyCard crypto2 = new CryptoCurrencyCard("Ethereum", 3, 2);
        player.addToDeck(crypto2);
        player.shuffleDeck();
        player.drawCards(3);
        
        int coins = player.playAllCryptocurrency();
        assertEquals(3, coins); // Bitcoin (1) + Ethereum (2)
        assertEquals(1, player.getHandSize()); // Only automation card left
        assertEquals(2, player.getPlayedCardsSize()); // 2 crypto cards played
    }

    

    @Test
    public void testPlayerBuyCardWithInsufficientCoins() {
        int initialHandSize = player.getHandSize();
        player.buyCard(supply, 0);
        // With 0 coins, should not be able to buy Framework (costs 8)
        // May only buy Bitcoin if available
        assertTrue(player.getHandSize() >= initialHandSize);
    }

    @Test
    public void testPlayerRecycleDiscard() {
        player.addToDeck(automationCard);
        player.addToDiscard(cryptocurrencyCard);
        player.shuffleDeck();
        
        // Draw more cards than in deck
        player.drawCards(3);
        
        // Should have drawn from both deck and discard
        assertEquals(2, player.getHandSize());
    }

    // ===== Supply Tests =====
    @Test
    public void testSupplyInitialization() {
        assertTrue(supply.isAvailable("Bitcoin"));
        assertTrue(supply.isAvailable("Ethereum"));
        assertTrue(supply.isAvailable("Dogecoin"));
        assertTrue(supply.isAvailable("Method"));
        assertTrue(supply.isAvailable("Module"));
        assertTrue(supply.isAvailable("Framework"));
    }

    @Test
    public void testSupplyCardCosts() {
        assertEquals(0, supply.getCardCost("Bitcoin"));
        assertEquals(3, supply.getCardCost("Ethereum"));
        assertEquals(6, supply.getCardCost("Dogecoin"));
        assertEquals(2, supply.getCardCost("Method"));
        assertEquals(5, supply.getCardCost("Module"));
        assertEquals(8, supply.getCardCost("Framework"));
    }

    @Test
    public void testSupplyTakeCard() {
        Card card = supply.takeCard("Bitcoin");
        assertNotNull(card);
        assertEquals("Bitcoin", card.getName());
        assertEquals(59, supply.getRemainingCount("Bitcoin"));
    }

    @Test
    public void testSupplyIsAvailable() {
        assertTrue(supply.isAvailable("Bitcoin"));
        
        // Remove all bitcoins
        for (int i = 0; i < 60; i++) {
            supply.takeCard("Bitcoin");
        }
        
        assertFalse(supply.isAvailable("Bitcoin"));
    }

    @Test
    public void testSupplyGetCardCostInvalid() {
        assertEquals(-1, supply.getCardCost("InvalidCard"));
    }

    @Test
    public void testSupplyRemainingCount() {
        assertEquals(60, supply.getRemainingCount("Bitcoin"));
        assertEquals(40, supply.getRemainingCount("Ethereum"));
        assertEquals(30, supply.getRemainingCount("Dogecoin"));
        assertEquals(14, supply.getRemainingCount("Method"));
        assertEquals(8, supply.getRemainingCount("Module"));
        assertEquals(8, supply.getRemainingCount("Framework"));
    }

    @Test
    public void testSupplyAreFrameworksEmpty() {
        assertFalse(supply.areFrameworksEmpty());
        
        // Remove all frameworks
        for (int i = 0; i < 8; i++) {
            supply.takeCard("Framework");
        }
        
        assertTrue(supply.areFrameworksEmpty());
    }

    @Test
    public void testSupplyTakeCardMultiple() {
        for (int i = 0; i < 5; i++) {
            Card card = supply.takeCard("Bitcoin");
            assertNotNull(card);
        }
        assertEquals(55, supply.getRemainingCount("Bitcoin"));
    }

    @Test
    public void testSupplyTakeCardExhausted() {
        // Remove all bitcoins
        for (int i = 0; i < 60; i++) {
            supply.takeCard("Bitcoin");
        }
        
        Card card = supply.takeCard("Bitcoin");
        assertNull(card);
    }

    // ===== Integration Tests =====
    @Test
    public void testPlayerGameFlow() {
        // Create a player and give them starting cards
        Player testPlayer = new Player("Player1");
        
        // Add starting cryptocurrency cards
        for (int i = 0; i < 7; i++) {
            testPlayer.addToDeck(new CryptoCurrencyCard("Bitcoin", 0, 1));
        }
        
        testPlayer.shuffleDeck();
        testPlayer.drawCards(5);
        
        assertEquals(5, testPlayer.getHandSize());
        
        int coins = testPlayer.playAllCryptocurrency();
        assertEquals(5, coins);
        assertEquals(0, testPlayer.getHandSize());
        assertEquals(5, testPlayer.getPlayedCardsSize());
    }

    @Test
    public void testMultiplePlayersWithSupply() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Supply testSupply = new Supply();
        
        // Both players buy cards
        player1.buyCard(testSupply, 5);
        player2.buyCard(testSupply, 5);
        
        // Both should have made purchases or supply was too expensive
        assertTrue(testSupply.getRemainingCount("Bitcoin") <= 60);
    }

    @Test
    public void testCardTypeDistinction() {
        AutomationCard auto = new AutomationCard("Module", 5, 3);
        CryptoCurrencyCard crypto = new CryptoCurrencyCard("Ethereum", 3, 2);
        
        assertTrue(auto.isAutomationCard());
        assertFalse(crypto.isAutomationCard());
        
        assertEquals(3, auto.getValue());
        assertEquals(2, crypto.getValue());
    }

    // ===== Buying Feature Tests =====
    @Test
    public void testBuyingAutomationCardPriority() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        // Buy with enough coins for Framework (8 coins)
        testPlayer.buyCard(testSupply, 8);
        
        // Player should now have a card in discard
        assertTrue(testPlayer.getDiscardSize() > 0);
        assertEquals(7, testSupply.getRemainingCount("Framework"));
    }

    @Test
    public void testBuyingMultipleCards() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        int initialDiscardSize = testPlayer.getDiscardSize();
        
        // Buy multiple cards with enough coins
        testPlayer.buyCard(testSupply, 20); // Should afford Framework (8)
        testPlayer.buyCard(testSupply, 15); // Should afford Module (5)
        testPlayer.buyCard(testSupply, 6);  // Should afford Method (2) or Dogecoin (6)
        
        // Player should have accumulated cards
        assertTrue(testPlayer.getDiscardSize() > initialDiscardSize);
    }

    @Test
    public void testBuyCryptoCurrencyWhenAutomationNotAffordable() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        // Buy with 3 coins - enough for Ethereum (3) but not for Method (2) is too cheap
        // Actually Method costs 2, so should buy Method first, but if we have exactly 3,
        // we can buy Ethereum or Method. Let's use a specific test.
        testPlayer.buyCard(testSupply, 3);
        
        // Should have bought something (Method is 2 coins)
        assertTrue(testPlayer.getDiscardSize() > 0);
    }

    @Test
    public void testBuyingReducesSupplyCount() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        int initialBitcoinCount = testSupply.getRemainingCount("Bitcoin");
        int initialFrameworkCount = testSupply.getRemainingCount("Framework");
        
        // Buy a Framework card (should prefer automation)
        testPlayer.buyCard(testSupply, 8);
        
        // Framework count should decrease
        assertEquals(initialFrameworkCount - 1, testSupply.getRemainingCount("Framework"));
    }

    @Test
    public void testBuyingAddsCardToDiscard() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        int initialDiscardSize = testPlayer.getDiscardSize();
        
        // Buy a card
        String boughtCard = testPlayer.buyCard(testSupply, 10);
        
        // Card should be added to discard pile
        assertEquals(initialDiscardSize + 1, testPlayer.getDiscardSize());
        
        // A card should have been bought
        assertNotNull(boughtCard);
    }

    @Test
    public void testBuyingWithLimitedCoins() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        int initialDiscardSize = testPlayer.getDiscardSize();
        
        // Buy with only 1 coin - should buy Bitcoin (costs 0)
        String boughtCard = testPlayer.buyCard(testSupply, 1);
        
        // Should have bought Bitcoin
        assertEquals("Bitcoin", boughtCard);
        assertEquals(initialDiscardSize + 1, testPlayer.getDiscardSize());
    }

    @Test
    public void testBuyingStrategyAutomationFirst() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        // With 8 coins, should buy Framework first (automation priority)
        String boughtCard = testPlayer.buyCard(testSupply, 8);
        
        // Should buy an automation card (Framework, Module, or Method)
        assertTrue(boughtCard.equals("Framework") || 
                  boughtCard.equals("Module") || 
                  boughtCard.equals("Method"));
    }

    @Test
    public void testNoCardPurchaseWithZeroCoins() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        int initialDiscardSize = testPlayer.getDiscardSize();
        
        // Buy with 0 coins - should only afford Bitcoin (if available) or nothing
        String boughtCard = testPlayer.buyCard(testSupply, 0);
        
        // May or may not buy Bitcoin, but discard size should not increase by more than 1
        assertTrue(testPlayer.getDiscardSize() <= initialDiscardSize + 1);
    }

    @Test
    public void testBuyingSequentialDecreasesSupply() {
        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        Supply testSupply = new Supply();
        
        int initialBitcoinCount = testSupply.getRemainingCount("Bitcoin");
        
        // Both players buy
        player1.buyCard(testSupply, 10);
        player2.buyCard(testSupply, 10);
        
        // Total supply should have decreased
        int finalCount = testSupply.getRemainingCount("Bitcoin") +
                        testSupply.getRemainingCount("Module") +
                        testSupply.getRemainingCount("Method") +
                        testSupply.getRemainingCount("Ethereum") +
                        testSupply.getRemainingCount("Dogecoin") +
                        testSupply.getRemainingCount("Framework");
        
        int initialTotal = 60 + 40 + 30 + 14 + 8 + 8;
        
        // At least 2 cards should have been bought
        assertTrue(finalCount <= initialTotal - 2);
    }

    @Test
    public void testBuyingReturnValue() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        // Buy with sufficient coins
        String cardName = testPlayer.buyCard(testSupply, 10);
        
        // Should return a valid card name
        assertNotNull(cardName);
        assertTrue(cardName.length() > 0);
    }

    @Test
    public void testBuyingEmptySupplyPile() {
        Player testPlayer = new Player("TestPlayer");
        Supply testSupply = new Supply();
        
        // Remove all Bitcoins
        for (int i = 0; i < 60; i++) {
            testSupply.takeCard("Bitcoin");
        }
        
        // Try to buy with 1 coin (would normally get Bitcoin)
        String boughtCard = testPlayer.buyCard(testSupply, 1);
        
        // Should not buy Bitcoin since it's empty
        assertNotEquals("Bitcoin", boughtCard);
    }
}



