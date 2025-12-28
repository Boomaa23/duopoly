package com.boomaa.duopoly;

import com.boomaa.duopoly.players.Player;
import com.boomaa.duopoly.spaces.GoToJailSpace;
import com.boomaa.duopoly.spaces.JailSpace;
import com.boomaa.duopoly.spaces.OwnableSpace;
import com.boomaa.duopoly.spaces.RandomCardSpace;
import com.boomaa.duopoly.spaces.Space;
import com.boomaa.duopoly.spaces.PaymentSpace;

public class Main {
    public static Player[] PLAYERS;
    public static Space[] BOARD;

    public static void main(String[] args) {
        for (Player p : PLAYERS) {
            if (p.isBankrupt()) {
                continue;
            }
            DiceRoll roll = new DiceRoll();
            Space oldSpc = positionToSpace(p.getPosition());
            if (oldSpc instanceof JailSpace jailSpc) {
                //TODO improve this logic, should not have to recheck wasInJail
                boolean wasInJail = p.getJailTurns() != -1;
                jailSpc.doAction(p, roll);
                if (wasInJail && !roll.isDoubles() && p.getJailTurns() == -1) {
                    /* Was in jail, did not roll doubles, and now out of jail */
                    continue;
                }
            }
            Space newSpc = positionToSpace(p.changePosition(roll.getTotal()));

            if (newSpc instanceof OwnableSpace<?> ownSpc) {
                ownSpc.doAction(p, roll);
            } else if (newSpc instanceof RandomCardSpace cardSpc) {
                cardSpc.doAction(p, roll);
            } else if (newSpc instanceof GoToJailSpace gotoJailSpc) {
                gotoJailSpc.doAction(p, roll);
            } else if (newSpc instanceof PaymentSpace paySpc) {
                paySpc.doAction(p, roll);
            }
        }
    }

    public static Space positionToSpace(int position) {
        return BOARD[position % BOARD.length];
    }

    public static boolean mortgagePropertyUntil(Player p, int cost) {
        while (cost > p.getMoney() && p.mortgageSingleProperty()) {}
        return cost < p.getMoney();
    }
}