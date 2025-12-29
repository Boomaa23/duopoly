package com.boomaa.duopoly;

import com.boomaa.duopoly.players.ComputerPlayer;
import com.boomaa.duopoly.players.HumanPlayer;
import com.boomaa.duopoly.players.Player;

import java.util.List;

public class PlayerManager {
    private static final List<Player> ALL_PLAYERS = List.of(
            new HumanPlayer(),
            new ComputerPlayer(),
            new ComputerPlayer(),
            new ComputerPlayer()
    );

    private PlayerManager() {
    }

    public static boolean isGameOver() {
        final long numPlayersBankrupt = ALL_PLAYERS.stream()
            .filter(Player::isBankrupt)
            .count();
        return numPlayersBankrupt >= (ALL_PLAYERS.size() - 1);
    }

    public static List<Player> getNonBankruptPlayers() {
        return ALL_PLAYERS.stream()
            .filter(Player::isBankrupt)
            .toList();
    }

    public static List<Player> getAllPlayers() {
        return ALL_PLAYERS;
    }

    public static int getNumPlayers() {
        return ALL_PLAYERS.size();
    }
}
