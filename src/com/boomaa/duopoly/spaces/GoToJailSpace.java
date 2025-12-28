package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

public class GoToJailSpace extends Space {
    public GoToJailSpace() {
        super("Go to Jail");
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        p.setPosition(JailSpace.JAIL_POS);
        p.incrementJailTurns();
    }
}
