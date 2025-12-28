package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

public class JailSpace extends Space {
    public JailSpace() {
        super("Jail");
    }

    public boolean doAction(Player p, DiceRoll roll) {
        if (p.getJailTurns() >= 0) {
            /* Player is in jail (not just visiting) */
            p.incrementJailTurns();
            if (roll.isDoubles()) {
                /* If doubles rolled, exit jail and move as usual */
                p.resetJailTurns();
            } else if (p.hasJailFreeCard() && p.chooseUseJailFreeCard()) {
                /* If get-out-of-jail-free card used, exit jail but do not move */
                p.resetJailTurns();
                return false;
            } else if (p.getJailTurns() == 3 || p.choosePayForJail()) {
                /* If 3 turns in jail OR choosing to pay for jail, exit jail and pay but do not move */
                p.changeMoney(-50);
                //TODO process bankruptcy here
                p.resetJailTurns();
                return false;
            }
        }
        return true;
    }
}
