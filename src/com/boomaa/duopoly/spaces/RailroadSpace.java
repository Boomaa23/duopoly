package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

public class RailroadSpace extends OwnableSpace<OwnableSpace.SingleGroupIdentifier> {
    public static final int[] DEFAULT_RAILROAD_RENTS = new int[]{ 0, 25, 50, 100, 200 };

    public RailroadSpace(SpaceName name, int cost) {
        super(name, cost);
    }

    @Override
    public int getRent(DiceRoll roll, boolean ownsGroup, Player p) {
        int numRailroadsOwned = (int) p.getOwnedSpaces().stream()
            .filter(spc -> spc.getGroupIdentifier().equals(getGroupIdentifier()))
            .count();
        return DEFAULT_RAILROAD_RENTS[numRailroadsOwned];
    }

    @Override
    public SingleGroupIdentifier getGroupIdentifier() {
        return SingleGroupIdentifier.RAILROAD;
    }
}
