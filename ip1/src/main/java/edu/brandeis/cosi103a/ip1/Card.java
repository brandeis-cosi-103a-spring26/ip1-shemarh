package edu.brandeis.cosi103a.ip1;

public abstract class Card {
    private final String name;
    private final int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    /**
     * Returns the value of this card.
     * For automation cards, this is the AP value.
     * For cryptocurrency cards, this is the coin value.
     */
    public abstract int getValue();

    /**
     * Returns true if this is an automation card.
     */
    public abstract boolean isAutomationCard();

    @Override
    public String toString() {
        return name;
    }
}
