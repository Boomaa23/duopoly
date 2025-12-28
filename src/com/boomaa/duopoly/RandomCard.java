package com.boomaa.duopoly;

public class RandomCard {
    private final String text;
    private final ActionType actionType;
    private final int amount;
    private final int secondAmount;

    public RandomCard(String text, ActionType actionType, int amount, int secondAmount) {
        this.text = text;
        this.actionType = actionType;
        this.amount = amount;
        this.secondAmount = secondAmount;
    }

    public RandomCard(String text, ActionType actionType, int amount) {
        this(text, actionType, amount, -1);
    }

    public RandomCard(String text, ActionType actionType) {
        this(text, actionType, -1, -1);
    }

    public String getText() {
        return text;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getAmount() {
        return amount;
    }

    public int getSecondAmount() {
        return secondAmount;
    }
}
