package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.players.Player;

public abstract class Space {
    private final String name;

    public Space(String name) {
        this.name = name;
    }

    public abstract void doAction(Player p, DiceRoll roll);

    public String getName() {
        return name;
    }

    public interface GroupIdentifier {
    }
}
