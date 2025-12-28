package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.ActionType;
import com.boomaa.duopoly.RandomCard;

public class CommunityChestSpace extends RandomCardSpace {
    private static final CommunityChestSpace INSTANCE = new CommunityChestSpace();
    public static final RandomCard[] ALL_CARDS = new RandomCard[] {
            new RandomCard("Advance to \"Go\" (Collect $200)", ActionType.CHANGE_MONEY, 200),
            new RandomCard("Bank error in your favor. Collect $200.", ActionType.CHANGE_MONEY, 200),
            new RandomCard("Doctor's fees. Pay $50.", ActionType.CHANGE_MONEY, -50),
            new RandomCard("From sale of stock you get $50", ActionType.CHANGE_MONEY, 50),
            new RandomCard("Get Out of Jail Free - This card may be kept until needed or sold/traded.", ActionType.JAIL_FREE),
            new RandomCard("Go to Jail. Go directly to jail. Do not pass Go, Do not collect $200.", ActionType.GOTO_JAIL),
            new RandomCard("Grand Opera Night. Collect $50 from every player for opening night seats.",
                    ActionType.CHANGE_MONEY_PER_PLAYER, 50),
            new RandomCard("Holiday Fund matures. Collect $100", ActionType.CHANGE_MONEY, 100),
            new RandomCard("Income tax refund. Collect $20.", ActionType.CHANGE_MONEY, 20),
            new RandomCard("It is your birthday. Collect $10 from every player.", ActionType.CHANGE_MONEY_PER_PLAYER, 10),
            new RandomCard("Life insurance matures – Collect $100", ActionType.CHANGE_MONEY, 100),
            new RandomCard("Hospital Fees. Pay $50.", ActionType.CHANGE_MONEY, -50),
            new RandomCard("School Fees. Pay $50.", ActionType.CHANGE_MONEY, -50),
            new RandomCard("Receive $25 consultancy fee.", ActionType.CHANGE_MONEY, 25),
            new RandomCard("You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.",
                    ActionType.CHANGE_MONEY_PER_BLDG, -40, -115),
            new RandomCard("You have won second prize in a beauty contest. Collect $10.", ActionType.CHANGE_MONEY, 10),
            new RandomCard("You inherit $100.", ActionType.CHANGE_MONEY, 100)
    };

    private CommunityChestSpace() {
        super("Community Chest", ALL_CARDS);
    }

    public static CommunityChestSpace getInstance() {
        return INSTANCE;
    }
}
