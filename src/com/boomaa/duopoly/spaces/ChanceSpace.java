package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.RandomCard;

public class ChanceSpace extends RandomCardSpace {
    private static final ChanceSpace INSTANCE = new ChanceSpace();
    public static final RandomCard[] ALL_CARDS = new RandomCard[] {
            //TODO fill in all cards
    };

    private ChanceSpace() {
        super("Chance", ALL_CARDS);
    }

    public static ChanceSpace getInstance() {
        return INSTANCE;
    }
}
