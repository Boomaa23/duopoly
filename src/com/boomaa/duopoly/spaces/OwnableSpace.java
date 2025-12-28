package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

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

    public enum SingleGroupIdentifier implements GroupIdentifier {
        UTILITY,
        RAILROAD
    }
}
