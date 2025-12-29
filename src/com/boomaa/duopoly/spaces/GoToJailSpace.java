package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.BoardManager;
import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

public class GoToJailSpace extends Space {
    public GoToJailSpace(SpaceName name) {
        super(name);
    }

    public GoToJailSpace() {
        this(SpaceName.GO_TO_JAIL);
    }

    @Override
    public void doAction(Player p, DiceRoll roll) {
        p.setPosition(BoardManager.spaceNameToPosition(SpaceName.JAIL));
        p.incrementJailTurns();
    }
}
