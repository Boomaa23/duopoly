package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

public class UtilitySpace extends OwnableSpace<OwnableSpace.SingleGroupIdentifier> {
    private static final int BOTH_UTILITY_MULT = 10;
    private static final int SINGLE_UTILITY_MULT = 4;

    public UtilitySpace(SpaceName name, int cost) {
        super(name, cost);
    }

    @Override
    public int getRent(DiceRoll roll, boolean ownsGroup, Player p) {
        return (ownsGroup ? BOTH_UTILITY_MULT : SINGLE_UTILITY_MULT) * roll.getTotal();
    }

    @Override
    public SingleGroupIdentifier getGroupIdentifier() {
        return SingleGroupIdentifier.UTILITY;
    }
}
