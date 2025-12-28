package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

public class PaymentSpace extends Space {
    private final int amount;

    public PaymentSpace(String name, int amount) {
        super(name);
        this.amount = amount;
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        p.changeMoney(amount);
    }
}
