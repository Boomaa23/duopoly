package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.DiceRoll;
import com.boomaa.duopoly.SpaceName;
import com.boomaa.duopoly.players.Player;

public abstract class Space {
    private final SpaceName name;

    public Space(SpaceName name) {
        this.name = name;
    }

    public abstract void doAction(Player p, DiceRoll roll);

    public SpaceName getName() {
        return name;
    }

    public interface GroupIdentifier {
    }
}
