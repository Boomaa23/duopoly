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
    public static Player[] players;
    public static Space[] board;

    public static void main(String[] args) {
        int jailPos = getJailPos();

        for (Player p : players) {
            if (p.isBankrupt()) {
                continue;
            }
            DiceRoll roll = new DiceRoll();
            Space spc = positionToSpace(p.changePosition(roll.getTotal()));

            if (spc instanceof OwnableSpace<?> ownSpc) {
                if (ownSpc.isOwned()) {
                    if (p.getOwnedSpaces().contains(ownSpc)) {
                        /* Property is owned by current player */
                        //TODO
                    } else {
                        /* Property is not owned by current player, pay owner player */
                        Player propOwner = ownSpc.getOwner();
                        boolean ownsSet = propOwner.getOwnedGroups().contains(ownSpc.getGroupIdentifier());
                        int rent = ownSpc.getRent(roll, ownsSet, p);
                        p.changeMoney(-rent);
                        propOwner.changeMoney(rent);
                        //TODO support bankruptcy here
                    }
                } else if (p.choosePurchase(ownSpc)) {
                    /* Property is not yet owned and current player chose to buy the property */
                    int cost = ownSpc.getCost();
                    p.changeMoney(-cost);
                    p.getOwnedSpaces().add(ownSpc);
                    ownSpc.setOwner(p);
                    if (playerOwnsGroup(p, ownSpc.getGroupIdentifier())) {
                        p.getOwnedGroups().add(ownSpc.getGroupIdentifier());
                    }
                }
            } else if (spc instanceof RandomCardSpace cardSpc) {
                RandomCard pickedCard = cardSpc.pickCard();
                switch (pickedCard.getActionType()) {
                    case GAIN_MONEY -> p.changeMoney(pickedCard.getAmount());
                    case LOSE_MONEY -> p.changeMoney(-pickedCard.getAmount());
                    case JAIL_FREE -> p.addJailFreeCard(); //TODO remove from stack after acquire
                    case TO_JAIL -> p.setPosition(jailPos);
                    case GAIN_MONEY_PER_PLAYER -> {
                        for (Player targetPlayer : players) {
                            targetPlayer.changeMoney(-pickedCard.getAmount());
                            //TODO support bankruptcy here
                        }
                        p.changeMoney(players.length * pickedCard.getAmount());
                    }
                    case LOSE_MONEY_PER_BLDG -> {
                        for (OwnableSpace<?> s : p.getOwnedSpaces()) {
                            if (s instanceof PropertySpace ps) {
                                int payment = ps.hasHotel()
                                        ? -pickedCard.getSecondAmount()
                                        : (-pickedCard.getAmount() * ps.numHouses());
                                p.changeMoney(payment);
                                //TODO support bankruptcy here
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getJailPos() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] instanceof JailSpace) {
                return i;
            }
        }
        throw new NoSuchElementException("No jail found in board");
    }

    public static Space positionToSpace(int position) {
        return board[position % board.length];
    }

    public static Stream<? extends OwnableSpace<?>> spacesInGroupStream(Space.GroupIdentifier gid) {
        return Arrays.stream(board)
                .filter(spc -> spc instanceof OwnableSpace)
                .map(spc -> (OwnableSpace<?>)spc)
                .filter(spc -> gid.equals(spc.getGroupIdentifier()));
    }

    public static boolean playerOwnsGroup(Player p, Space.GroupIdentifier gid) {
        return spacesInGroupStream(gid)
                .allMatch(spc -> p.equals(spc.getOwner()));
    }

    public static boolean mortgagePropertyUntil(Player p, int cost) {
        while (cost > p.getMoney() && p.mortgageSingleProperty()) {}
        return cost < p.getMoney();
    }
}