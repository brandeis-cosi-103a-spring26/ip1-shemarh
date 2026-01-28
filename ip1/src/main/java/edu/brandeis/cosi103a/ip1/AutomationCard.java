package edu.brandeis.cosi103a.ip1;

public class AutomationCard extends Card {


    private final int apValue;

    public AutomationCard(String name, int cost, int apValue) {
        super(name, cost);
        this.apValue = apValue;
    }

    @Override
    public int getValue() {
        return apValue;
    }

    @Override
    public boolean isAutomationCard() {
        return true;
}   
}
