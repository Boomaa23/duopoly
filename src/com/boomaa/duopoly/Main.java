package com.boomaa.duopoly;

import com.boomaa.duopoly.players.ComputerPlayer;
import com.boomaa.duopoly.players.HumanPlayer;
import com.boomaa.duopoly.players.Player;
import com.boomaa.duopoly.spaces.ChanceSpace;
import com.boomaa.duopoly.spaces.CommunityChestSpace;
import com.boomaa.duopoly.spaces.GoToJailSpace;
import com.boomaa.duopoly.spaces.JailSpace;
import com.boomaa.duopoly.spaces.PropertySpace;
import com.boomaa.duopoly.spaces.PropertySpace.Color;
import com.boomaa.duopoly.spaces.RailroadSpace;
import com.boomaa.duopoly.spaces.Space;
import com.boomaa.duopoly.spaces.PaymentSpace;
import com.boomaa.duopoly.spaces.UtilitySpace;

public class Main {
    public final static Player[] PLAYERS = new Player[]{
            new HumanPlayer(),
            new ComputerPlayer(),
            new ComputerPlayer(),
            new ComputerPlayer()
    };
    public final static Space[] BOARD = new Space[]{
            new PaymentSpace("GO", 200),
            new PropertySpace("Mediterranean Avenue", 60, Color.BROWN, 2, 10, 30, 90, 160, 250),
            CommunityChestSpace.getInstance(),
            new PropertySpace("Baltic Avenue", 60, Color.BROWN, 4, 20, 60, 180, 320, 450),
            new PaymentSpace("Income Tax", -200),
            new RailroadSpace("Reading Railroad", 200),
            new PropertySpace("Oriental Avenue", 100, Color.LIGHT_BLUE, 6, 30, 90, 270, 400, 550),
            ChanceSpace.getInstance(),
            new PropertySpace("Vermont Avenue", 100, Color.LIGHT_BLUE, 6, 30, 90, 270, 400, 550),
            new PropertySpace("Connecticut Avenue", 120, Color.LIGHT_BLUE, 8, 40, 100, 300, 450, 600),
            new JailSpace(),
            new PropertySpace("St. Charles Place", 140, Color.PURPLE, 10, 50, 150, 450, 625, 750),
            new UtilitySpace("Electric Company", 150),
            new PropertySpace("States Avenue", 140, Color.PURPLE, 10, 50, 150, 450, 625, 750),
            new PropertySpace("Virginia Avenue", 160, Color.PURPLE, 12, 60, 180, 500, 700, 900),
            new RailroadSpace("Pennsylvania Railroad", 200),
            new PropertySpace("St. James Place", 180, Color.ORANGE, 14, 70, 200, 550, 750, 950),
            CommunityChestSpace.getInstance(),
            new PropertySpace("Tennessee Avenue", 180, Color.ORANGE, 14, 70, 200, 550, 750, 950),
            new PropertySpace("New York Avenue", 200, Color.ORANGE, 16, 80, 220, 600, 800, 1000),
            new PaymentSpace("Free Parking", 0),
            new PropertySpace("Kentucky Avenue", 220, Color.RED, 18, 90, 250, 700, 875, 1050),
            ChanceSpace.getInstance(),
            new PropertySpace("Indiana Avenue", 220, Color.RED, 18, 90, 250, 700, 875, 1050),
            new PropertySpace("Illinois Avenue", 240, Color.RED, 20, 100, 300, 750, 925, 1100),
            new RailroadSpace("B. & O. Railroad", 200),
            new PropertySpace("Atlantic Avenue", 260, Color.YELLOW, 22, 110, 330, 800, 975, 1150),
            new PropertySpace("Ventnor Avenue", 260, Color.YELLOW, 22, 110, 330, 800, 975, 1150),
            new UtilitySpace("Water Works", 150),
            new PropertySpace("Marvin Gardens", 280, Color.YELLOW, 24, 120, 360, 850, 1025, 1200),
            new GoToJailSpace(),
            new PropertySpace("Pacific Avenue", 300, Color.GREEN, 26, 130, 390, 900, 1100, 1275),
            new PropertySpace("North Carolina Avenue", 300, Color.GREEN, 26, 130, 390, 900, 1100, 1275),
            CommunityChestSpace.getInstance(),
            new PropertySpace("Pennsylvania Avenue", 320, Color.GREEN, 28, 150, 450, 1000, 1200, 1400),
            new RailroadSpace("Short Line", 200),
            ChanceSpace.getInstance(),
            new PropertySpace("Park Place", 350, Color.BLUE, 35, 175, 500, 1100, 1300, 1500),
            new PaymentSpace("Luxury Tax", -100),
            new PropertySpace("Boardwalk", 400, Color.BLUE, 50, 200, 600, 1400, 1700, 2000)
    };

    public static void main(String[] args) {
        int numBankruptPlayers = 0;
        while (numBankruptPlayers < PLAYERS.length - 1) {
            /* Game ends when there is only one non-bankrupt player left */
            numBankruptPlayers = 0;
            for (Player p : PLAYERS) {
                if (p.isBankrupt()) {
                    numBankruptPlayers++;
                    continue;
                }
                DiceRoll roll = new DiceRoll();
                Space oldSpc = positionToSpace(p.getPosition());
                if (oldSpc instanceof JailSpace jailSpc) {
                    //TODO improve this logic, should not have to recheck wasInJail
                    boolean wasInJail = p.getJailTurns() != -1;
                    jailSpc.doAction(p, roll);
                    if (wasInJail && !roll.isDoubles() && p.getJailTurns() == -1) {
                        /* Was in jail, did not roll doubles, and now out of jail */
                        continue;
                    }
                }
                Space newSpc = positionToSpace(p.changePosition(roll.getTotal()));
                newSpc.doAction(p, roll);
            }
        }
    }

    public static Space positionToSpace(int position) {
        return BOARD[position % BOARD.length];
    }
}