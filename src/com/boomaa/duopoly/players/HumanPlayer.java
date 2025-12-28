package com.boomaa.duopoly.players;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.spaces.OwnableSpace;

public class HumanPlayer extends Player {
    @Override
    public boolean choosePurchase(OwnableSpace ownSpc) {
        return false;
    }

    @Override
    public boolean mortgageSingleProperty() {
        return false;
    }
}
