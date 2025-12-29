package com.boomaa.duopoly;

public class DiceRoll {
    private final int dieOne;
    private final int dieTwo;
    private final int total;
    private final boolean doubles;

    public DiceRoll() {
        this.dieOne = rollSingleDie();
        this.dieTwo = rollSingleDie();
        this.total = dieOne + dieTwo;
        this.doubles = dieOne == dieTwo;
    }

    private int rollSingleDie() {
        return (int) (Math.random() * 6) + 1;
    }

    public int getDieOne() {
        return dieOne;
    }

    public int getDieTwo() {
        return dieTwo;
    }

    public int getTotal() {
        return total;
    }

    public boolean isDoubles() {
        return doubles;
    }
}
