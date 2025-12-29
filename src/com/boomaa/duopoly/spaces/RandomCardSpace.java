package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.BoardManager;
import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.PlayerManager;
import com.boomaa.duopoly.RandomCard;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RandomCardSpace extends Space {
    private final List<RandomCard> shuffledCards;
    private Iterator<RandomCard> shuffledCardsIter;

    public RandomCardSpace(SpaceName name, RandomCard... cards) {
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
                p.setPosition(BoardManager.spaceNameToPosition(SpaceName.GO));
                p.incrementJailTurns();
            }
            case CHANGE_MONEY_PER_PLAYER -> {
                for (Player targetPlayer : PlayerManager.getAllPlayers()) {
                    targetPlayer.changeMoney(-pickedCard.getAmount());
                }
                p.changeMoney(PlayerManager.getNumPlayers() * pickedCard.getAmount());
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
                    BoardManager.spaceNameToSpace(SpaceName.GO).doAction(p, roll);
                }
                p.setPosition(gotoPos);
                BoardManager.positionToSpace(gotoPos).doAction(p, roll);
            }
            case GOTO_REL -> {
                p.changePosition(pickedCard.getAmount());
                BoardManager.positionToSpace(p.getPosition()).doAction(p, roll);
            }
            case GOTO_NEAR_RR -> gotoNearPos(p, roll, OwnableSpace.SingleGroupIdentifier.RAILROAD, 2);
            case GOTO_NEAR_UTIL -> gotoNearPos(p, roll, OwnableSpace.SingleGroupIdentifier.UTILITY, 10);
            case null, default ->
                    throw new IllegalArgumentException(
                            "Picked card action type was not handled: " + pickedCard.getActionType());
        }
    }

    public void gotoNearPos(Player p, DiceRoll roll, GroupIdentifier gid, int rentMultiplier) {
        int playerPos = p.getPosition();
        Optional<Integer> nearestPosOpt = BoardManager.groupToPositions(gid)
            .stream()
            .filter(i -> (i - playerPos) > 0)
            .min(Integer::compare);
        if (nearestPosOpt.isEmpty()) {
            nearestPosOpt = BoardManager.groupToPositions(gid)
                    .stream()
                    .filter(i -> i > 0)
                    .min(Integer::compare);
        }
        if (nearestPosOpt.isEmpty()) {
            throw new NoSuchElementException("No group " + gid + " was found on the board");
        }
        int nearestPos = nearestPosOpt.get();

        int goPos = BoardManager.spaceNameToPosition(SpaceName.GO);
        if (nearestPos > goPos && (playerPos < goPos || playerPos > nearestPos)) {
            BoardManager.positionToSpace(goPos).doAction(p, roll);
        }
        p.setPosition(nearestPos);
        int prevMoney = p.getMoney();
        BoardManager.positionToSpace(nearestPos).doAction(p, roll);
        int chargedRent = p.getMoney() - prevMoney;
        if (chargedRent != 0) {
            p.changeMoney(chargedRent * (rentMultiplier - 1));
        }
    }
}
