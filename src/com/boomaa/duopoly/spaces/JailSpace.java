package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

public class JailSpace extends Space {
    private static final int MAX_JAIL_TURNS = 3;
    private static final int JAIL_PAYMENT = 50;

    public JailSpace(SpaceName name) {
        super(name);
    }

    public JailSpace() {
        this(SpaceName.JAIL);
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        if (p.getJailTurns() >= 0) {
            /* Player is in jail (not just visiting) */
            p.incrementJailTurns();
            if (roll.isDoubles()) {
                /* If doubles rolled, exit jail and move as usual */
                p.resetJailTurns();
            } else if (p.hasJailFreeCard() && p.chooseUseJailFreeCard()) {
                /* If get-out-of-jail-free card used, exit jail but do not move */
                p.resetJailTurns();
            } else if (p.getJailTurns() == MAX_JAIL_TURNS || p.choosePayForJail()) {
                /* If 3 turns in jail OR choosing to pay for jail, exit jail and pay but do not move */
                p.changeMoney(-JAIL_PAYMENT);
                p.resetJailTurns();
            }
        }
    }
}
