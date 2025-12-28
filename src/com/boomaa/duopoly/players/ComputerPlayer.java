package com.boomaa.duopoly.players;

import com.boomaa.duopoly.spaces.OwnableSpace;

public class ComputerPlayer extends Player {
    @Override
    public boolean choosePurchase(OwnableSpace<?> ownSpc) {
        return false;
    }

    @Override
    public boolean chooseUseJailFreeCard() {
        return false;
    }

    @Override
    public boolean choosePayForJail() {
        return false;
    }

    @Override
    public boolean mortgageSingleProperty() {
        return false;
    }
}
