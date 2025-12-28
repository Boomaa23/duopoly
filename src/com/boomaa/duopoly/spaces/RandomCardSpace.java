package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.Main;
import com.boomaa.duopoly.RandomCard;
import com.boomaa.duopoly.players.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
            case GAIN_MONEY -> p.changeMoney(pickedCard.getAmount());
            case LOSE_MONEY -> p.changeMoney(-pickedCard.getAmount());
            case JAIL_FREE -> p.addJailFreeCard(); //TODO remove from stack after acquire
            case TO_JAIL -> {
                p.setPosition(JailSpace.JAIL_POS);
                p.incrementJailTurns();
            }
            case GAIN_MONEY_PER_PLAYER -> {
                for (Player targetPlayer : Main.PLAYERS) {
                    targetPlayer.changeMoney(-pickedCard.getAmount());
                }
                p.changeMoney(Main.PLAYERS.length * pickedCard.getAmount());
            }
            case LOSE_MONEY_PER_BLDG -> {
                for (OwnableSpace<?> s : p.getOwnedSpaces()) {
                    if (s instanceof PropertySpace ps) {
                        int payment = ps.hasHotel()
                                ? -pickedCard.getSecondAmount()
                                : (-pickedCard.getAmount() * ps.numHouses());
                        p.changeMoney(payment);
                    }
                }
            }
        }
    }
}
