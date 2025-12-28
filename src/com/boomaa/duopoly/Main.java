package com.boomaa.duopoly;

import com.boomaa.duopoly.players.Player;
import com.boomaa.duopoly.spaces.JailSpace;
import com.boomaa.duopoly.spaces.OwnableSpace;
import com.boomaa.duopoly.spaces.PropertySpace;
import com.boomaa.duopoly.spaces.RandomCardSpace;
import com.boomaa.duopoly.spaces.Space;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class Main {
    public static Player[] PLAYERS;
    public static Space[] BOARD;

    public static void main(String[] args) {
        int jailPos = getJailPos();

        for (Player p : PLAYERS) {
            if (p.isBankrupt()) {
                continue;
            }
            DiceRoll roll = new DiceRoll();
            Space oldSpc = positionToSpace(p.getPosition());
            if (oldSpc instanceof JailSpace jailSpc && !jailSpc.doAction(p, roll)) {
                continue;
            }
            Space spc = positionToSpace(p.changePosition(roll.getTotal()));

            if (spc instanceof OwnableSpace<?> ownSpc) {
                ownSpc.doAction(p, roll);
            } else if (spc instanceof RandomCardSpace cardSpc) {
                RandomCard pickedCard = cardSpc.pickCard();
                cardSpc.doAction(p, jailPos, pickedCard);
            }
        }
    }

    public static int getJailPos() {
        for (int i = 0; i < BOARD.length; i++) {
            if (BOARD[i] instanceof JailSpace) {
                return i;
            }
        }
        throw new NoSuchElementException("No jail found in board");
    }

    public static Space positionToSpace(int position) {
        return BOARD[position % BOARD.length];
    }

    public static boolean mortgagePropertyUntil(Player p, int cost) {
        while (cost > p.getMoney() && p.mortgageSingleProperty()) {}
        return cost < p.getMoney();
    }
}