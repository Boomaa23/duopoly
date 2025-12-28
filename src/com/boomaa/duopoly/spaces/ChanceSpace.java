package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.ActionType;
import com.boomaa.duopoly.Main;
import com.boomaa.duopoly.RandomCard;

public class ChanceSpace extends RandomCardSpace {
    private static final ChanceSpace INSTANCE = new ChanceSpace();
    public static final RandomCard[] ALL_CARDS = new RandomCard[] {
            new RandomCard("Advance to \"Go\" (Collect $200)", ActionType.CHANGE_MONEY, 200),
            new RandomCard("Advance to Illinois Avenue. If you pass Go, collect $200.",
                    ActionType.GOTO_SPACE, Main.spaceNameToPosition("Illinois Avenue")),
            new RandomCard("Advance to St. Charles Place. If you pass Go, collect $200.",
                    ActionType.GOTO_SPACE, Main.spaceNameToPosition("St. Charles Place")),
            new RandomCard("Advance token to the nearest Utility. " +
                    "If unowned, you may buy it from the Bank. " +
                    "If owned, throw dice and pay owner a total 10 (ten) times the amount thrown.",
                    ActionType.GOTO_NEAR_UTIL),
            new RandomCard("Advance to the nearest Railroad. " +
                    "If unowned, you may buy it from the Bank. " +
                    "If owned, pay owner twice the rent to which they are otherwise entitled.",
                    ActionType.GOTO_NEAR_RR),
            new RandomCard("Advance to the nearest Railroad. " +
                    "If unowned, you may buy it from the Bank. " +
                    "If owned, pay owner twice the rent to which they are otherwise entitled.",
                    ActionType.GOTO_NEAR_RR),
            new RandomCard("Bank pays you dividend of $50.", ActionType.CHANGE_MONEY, 50),
            new RandomCard("Get Out of Jail Free - This card may be kept until needed or sold/traded.", ActionType.JAIL_FREE),
            new RandomCard("Go Back Three (3) Spaces", ActionType.GOTO_REL, -3),
            new RandomCard("Go to Jail. Go directly to jail. Do not pass Go, Do not collect $200.", ActionType.GOTO_JAIL),
            new RandomCard("Make general repairs on all your property: For each house pay $25, For each hotel $100.",
                    ActionType.CHANGE_MONEY_PER_BLDG, -25, -100),
            new RandomCard("Take a trip to Reading Railroad. If you pass Go, collect $200.",
                    ActionType.GOTO_SPACE, Main.spaceNameToPosition("Reading Railroad")),
            new RandomCard("Advance to Boardwalk.", ActionType.GOTO_SPACE, Main.spaceNameToPosition("Boardwalk")),
            new RandomCard("You have been elected Chairman of the Board. Pay each player $50.",
                    ActionType.CHANGE_MONEY_PER_PLAYER, -50),
            new RandomCard("Your building loan matures. Receive $150.", ActionType.CHANGE_MONEY, 150)
    };

    private ChanceSpace() {
        super("Chance", ALL_CARDS);
    }

    public static ChanceSpace getInstance() {
        return INSTANCE;
    }
}
