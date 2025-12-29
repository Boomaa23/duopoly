package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.BoardManager;
import com.boomaa.duopoly.players.Player;

public interface GroupableSpace<T extends Space.GroupIdentifier> {
    T getGroupIdentifier();

    default boolean playerOwnsGroup(Player p) {
        return BoardManager.groupToSpaces(getGroupIdentifier()).stream()
                .map(spc -> (OwnableSpace<?>) spc)
                .allMatch(spc -> p.equals(spc.getOwner()));
    }
}
