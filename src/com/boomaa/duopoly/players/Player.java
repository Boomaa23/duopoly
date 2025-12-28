package com.boomaa.duopoly.players;

import com.boomaa.duopoly.spaces.OwnableSpace;
import com.boomaa.duopoly.spaces.Space;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private final List<OwnableSpace<?>> ownedSpaces;
    private final List<Space.GroupIdentifier> ownedGroups;
    private int position;
    private int money;
    private int jailFreeCards;
    private int jailTurns;

    public Player(int startPosition, int startMoney) {
        this.ownedSpaces = new ArrayList<>();
        this.ownedGroups = new ArrayList<>();
        this.position = startPosition;
        this.money = startMoney;
        this.jailFreeCards = 0;
        resetJailTurns();
    }

    public Player() {
        this(0, 1500);
    }

    public abstract boolean choosePurchase(OwnableSpace<?> ownSpc);

    public abstract boolean chooseUseJailFreeCard();

    public abstract boolean choosePayForJail();

    public abstract boolean mortgageSingleProperty();

    public boolean isBankrupt() {
        return money <= 0 && ownedSpaces.stream().allMatch(OwnableSpace::isMortgaged);
    }

    public List<OwnableSpace<?>> getOwnedSpaces() {
        return ownedSpaces;
    }

    public List<Space.GroupIdentifier> getOwnedGroups() {
        return ownedGroups;
    }

    public int getPosition() {
        return position;
    }

    public int setPosition(int position) {
        this.position = position;
        return position;
    }

    public int changePosition(int diff) {
        this.position += diff;
        return position;
    }

    public int getMoney() {
        return money;
    }

    public int changeMoney(int diff) {
        this.money += diff;
        return money;
    }

    public boolean hasJailFreeCard() {
        return jailFreeCards > 0;
    }

    public void addJailFreeCard() {
        this.jailFreeCards++;
    }

    public void useJailFreeCard() {
        if (hasJailFreeCard()) {
            this.jailFreeCards--;
        }
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public int incrementJailTurns() {
        this.jailTurns++;
        return jailTurns;
    }

    public void resetJailTurns() {
        this.jailTurns = -1;
    }

    public boolean inJail() {
        return jailTurns != -1;
    }
}
