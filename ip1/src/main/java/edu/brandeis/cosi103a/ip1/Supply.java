package edu.brandeis.cosi103a.ip1;

import java.util.HashMap;
import java.util.Map;

public class Supply {
    
    private final Map<String, CardPile> piles;

    public Supply() {
        piles = new HashMap<>();
        initializeSupply();
    }

    private void initializeSupply() {
        // Cryptocurrency cards
        piles.put("Bitcoin", new CardPile(new CryptoCurrencyCard("Bitcoin", 0, 1), 60));
        piles.put("Ethereum", new CardPile(new CryptoCurrencyCard("Ethereum", 3, 2), 40));
        piles.put("Dogecoin", new CardPile(new CryptoCurrencyCard("Dogecoin", 6, 3), 30));

        // Automation cards
        piles.put("Method", new CardPile(new AutomationCard("Method", 2, 1), 14));
        piles.put("Module", new CardPile(new AutomationCard("Module", 5, 3), 8));
        piles.put("Framework", new CardPile(new AutomationCard("Framework", 8, 6), 8));
    }

    /**
     * Takes a card from the supply if available.
     */
    public Card takeCard(String cardName) {
        CardPile pile = piles.get(cardName);
        if (pile == null || pile.isEmpty()) {
            return null;
        }
        return pile.takeCard();
    }

    /**
     * Checks if a card is available in the supply.
     */
    public boolean isAvailable(String cardName) {
        CardPile pile = piles.get(cardName);
        return pile != null && !pile.isEmpty();
    }

    /**
     * Gets the cost of a card.
     */
    public int getCardCost(String cardName) {
        CardPile pile = piles.get(cardName);
        return pile != null ? pile.getCardCost() : -1;
    }

    /**
     * Checks if all Framework cards have been purchased.
     */
    public boolean areFrameworksEmpty() {
        CardPile frameworkPile = piles.get("Framework");
        return frameworkPile.isEmpty();
    }

    /**
     * Returns the number of cards remaining in a pile.
     */
    public int getRemainingCount(String cardName) {
        CardPile pile = piles.get(cardName);
        return pile != null ? pile.getCount() : 0;
    }

    /**
     * Inner class representing a pile of identical cards.
     */
    private static class CardPile {
        private final Card template;
        private int count;

        public CardPile(Card template, int count) {
            this.template = template;
            this.count = count;
        }

        public Card takeCard() {
            if (count > 0) {
                count--;
                // Create a new instance based on the template
                if (template.isAutomationCard()) {
                    AutomationCard ac = (AutomationCard) template;
                    return new AutomationCard(ac.getName(), ac.getCost(), ac.getValue());
                } else {
                    CryptoCurrencyCard cc = (CryptoCurrencyCard) template;
                    return new CryptoCurrencyCard(cc.getName(), cc.getCost(), cc.getValue());
                }
            }
            return null;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public int getCardCost() {
            return template.getCost();
        }

        public int getCount() {
            return count;
        }
    }
}
