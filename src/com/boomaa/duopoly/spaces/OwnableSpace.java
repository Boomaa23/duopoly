package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.Main;
import com.boomaa.duopoly.players.Player;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class OwnableSpace<T extends Space.GroupIdentifier> extends Space implements GroupableSpace<T> {
    private final int cost;
    protected boolean mortgaged;
    private Player owner;

    public OwnableSpace(String name, int cost) {
        super(name);
        this.cost = cost;
        this.owner = null;
        this.mortgaged = false;
    }

    public abstract int getRent(DiceRoll roll, boolean ownsGroup, Player p);

    public int getCost() {
        return cost;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOwned() {
        return this.owner != null;
    }

    public boolean isMortgaged() {
        return mortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        if (isOwned()) {
            if (p.getOwnedSpaces().contains(this)) {
                /* Property is owned by current player */
                //TODO
            } else {
                /* Property is not owned by current player, pay owner player */
                Player propOwner = getOwner();
                boolean ownsSet = propOwner.getOwnedGroups().contains(getGroupIdentifier());
                int rent = getRent(roll, ownsSet, p);
                p.changeMoney(-rent);
                propOwner.changeMoney(rent);
            }
        } else if (p.choosePurchase(this)) {
            /* Property is not yet owned and current player chose to buy the property */
            p.changeMoney(-getCost());
            p.getOwnedSpaces().add(this);
            setOwner(p);
            if (playerOwnsGroup(p)) {
                p.getOwnedGroups().add(getGroupIdentifier());
            }
        }
    }

    public enum SingleGroupIdentifier implements GroupIdentifier {
        UTILITY,
        RAILROAD
    }
}
