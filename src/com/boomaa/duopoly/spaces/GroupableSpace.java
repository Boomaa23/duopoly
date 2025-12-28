package com.boomaa.duopoly.spaces;

import com.boomaa.duopoly.Main;
import com.boomaa.duopoly.players.Player;

import java.util.Arrays;
import java.util.stream.Stream;

public interface GroupableSpace<T extends Space.GroupIdentifier> {
    T getGroupIdentifier();

    default Stream<? extends OwnableSpace<?>> spacesInGroupStream() {
        return Arrays.stream(Main.BOARD)
                .filter(spc -> spc instanceof OwnableSpace)
                .map(spc -> (OwnableSpace<?>) spc)
                .filter(spc -> getGroupIdentifier().equals(spc.getGroupIdentifier()));
    }

    default boolean playerOwnsGroup(Player p) {
        return spacesInGroupStream()
                .allMatch(spc -> p.equals(spc.getOwner()));
    }
}
