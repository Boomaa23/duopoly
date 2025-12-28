package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.Main;
import com.boomaa.duopoly.RandomCard;
import com.boomaa.duopoly.players.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class RandomCardSpace extends Space {
    private final List<RandomCard> shuffledCards;
    private Iterator<RandomCard> shuffledCardsIter;

    public RandomCardSpace(String name, RandomCard... cards) {
        super(name);
        this.shuffledCards = Arrays.asList(cards);
        Collections.shuffle(shuffledCards);
        this.shuffledCardsIter = null;
    }

    public RandomCard pickCard() {
        /* Card iterator should be circular */
        if (!shuffledCardsIter.hasNext()) {
            this.shuffledCardsIter = shuffledCards.iterator();
        }
        return shuffledCardsIter.next();
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        RandomCard pickedCard = pickCard();
        switch (pickedCard.getActionType()) {
            case CHANGE_MONEY -> p.changeMoney(pickedCard.getAmount());
            case JAIL_FREE -> p.addJailFreeCard(); //TODO remove from stack after acquire
            case GOTO_JAIL -> {
                p.setPosition(JailSpace.JAIL_POS);
                p.incrementJailTurns();
            }
            case CHANGE_MONEY_PER_PLAYER -> {
                for (Player targetPlayer : Main.PLAYERS) {
                    targetPlayer.changeMoney(-pickedCard.getAmount());
                }
                p.changeMoney(Main.PLAYERS.length * pickedCard.getAmount());
            }
            case CHANGE_MONEY_PER_BLDG -> {
                for (OwnableSpace<?> s : p.getOwnedSpaces()) {
                    if (s instanceof PropertySpace ps) {
                        int payment = ps.hasHotel()
                                ? pickedCard.getSecondAmount()
                                : (pickedCard.getAmount() * ps.numHouses());
                        p.changeMoney(payment);
                    }
                }
            }
            case GOTO_SPACE -> {
                int gotoPos = pickedCard.getAmount();
                if (gotoPos < p.getPosition()) {
                    Main.spaceNameToSpace("GO").doAction(p, roll);
                }
                p.setPosition(gotoPos);
                Main.positionToSpace(gotoPos).doAction(p, roll);
            }
            case GOTO_REL -> {
                p.changePosition(pickedCard.getAmount());
                Main.positionToSpace(p.getPosition()).doAction(p, roll);
            }
            case GOTO_NEAR_RR -> gotoNearPos(p, roll, RailroadSpace.class, 2);
            case GOTO_NEAR_UTIL -> gotoNearPos(p, roll, UtilitySpace.class, 10);
            case null, default ->
                    throw new IllegalArgumentException(
                            "Picked card action type was not handled: " + pickedCard.getActionType());
        }
    }

    public void gotoNearPos(Player p, DiceRoll roll, Class<? extends Space> spaceFilter, int rentMultiplier) {
        int dstPos = -1;
        int passedGoPos = -1;
        for (int i = 0; i < Main.BOARD.length; i++) {
            int pos = (i + p.getPosition()) % Main.BOARD.length;
            Space spc = Main.BOARD[pos];
            if (spc.getClass().equals(spaceFilter)) {
                dstPos = pos;
                break;
            } else if (spc.getName().equals("GO")) {
                passedGoPos = pos;
            }
        }
        if (dstPos == -1) {
            throw new NoSuchElementException("No " + spaceFilter.getSimpleName() + " was found on the board");
        }
        if (passedGoPos != -1) {
            Main.BOARD[passedGoPos].doAction(p, roll);
        }
        p.setPosition(dstPos);
        int prevMoney = p.getMoney();
        Main.BOARD[dstPos].doAction(p, roll);
        int chargedRent = p.getMoney() - prevMoney;
        if (chargedRent != 0) {
            p.changeMoney(chargedRent * (rentMultiplier - 1));
        }
    }
}
