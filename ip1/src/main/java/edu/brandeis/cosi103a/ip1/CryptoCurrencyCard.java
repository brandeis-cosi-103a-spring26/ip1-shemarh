package edu.brandeis.cosi103a.ip1;

public class CryptoCurrencyCard extends Card {
    private final int coinValue;

    public CryptoCurrencyCard(String name, int cost, int coinValue) {
        super(name, cost);
        this.coinValue = coinValue;
    }

    @Override
    public int getValue() {
        return coinValue;
    }

    @Override
    public boolean isAutomationCard() {
        return false;
    }
    
}
