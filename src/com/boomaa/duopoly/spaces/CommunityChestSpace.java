package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.RandomCard;

public class CommunityChestSpace extends RandomCardSpace {
    private static final CommunityChestSpace INSTANCE = new CommunityChestSpace();
    public static final RandomCard[] ALL_CARDS = new RandomCard[] {
            //TODO fill in all cards
    };

    private CommunityChestSpace() {
        super("Community Chest", ALL_CARDS);
    }

    public static CommunityChestSpace getInstance() {
        return INSTANCE;
    }
}
