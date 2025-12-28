package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

public class PropertySpace extends OwnableSpace<PropertySpace.Color> {
    private static final int MAX_BUILDINGS = 5;
    private final Color color;
    private final int[] rents;
    private int buildings;

    public PropertySpace(String name, int cost, Color color, int... rents) {
        super(name, cost);
        this.color = color;
        this.rents = rents;
        this.buildings = 0;
    }

    public int numHouses() {
        return buildings % MAX_BUILDINGS;
    }

    public boolean hasHotel() {
        return buildings == MAX_BUILDINGS;
    }

    public boolean canAddBuilding() {
        return (buildings < MAX_BUILDINGS) && isOwned() && !isMortgaged();
    }

    public void addBuilding() {
        if (canAddBuilding()) {
            this.buildings++;
        }
    }

    public boolean canRemoveBuilding() {
        return buildings > 0 && isOwned() && !isMortgaged();
    }

    public void removeBuilding() {
        if (canRemoveBuilding()) {
            this.buildings--;
        }
    }

    public int getBuildingCost() {
        return color.getBuildingCost();
    }

    @Override
    public int getRent(DiceRoll roll, boolean ownsGroup, Player p) {
        int rent = rents[buildings];
        if (ownsGroup && (buildings == 0)) {
            rent *= 2;
        }
        return rent;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Color getGroupIdentifier() {
        return getColor();
    }

    public enum Color implements GroupIdentifier {
        BROWN(50),
        LIGHT_BLUE(50),
        PURPLE(100),
        ORANGE(100),
        RED(150),
        YELLOW(150),
        GREEN(200),
        BLUE(200);

        private final int buildingCost;

        Color(int buildingCost) {
            this.buildingCost = buildingCost;
        }

        public int getBuildingCost() {
            return buildingCost;
        }
    }
}
