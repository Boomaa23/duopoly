package com.boomaa.duopoly;

import com.boomaa.duopoly.players.Player;
import com.boomaa.duopoly.spaces.JailSpace;
import com.boomaa.duopoly.spaces.Space;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        while (!PlayerManager.isGameOver()) {
            for (Player p : PlayerManager.getNonBankruptPlayers()) {
                DiceRoll roll = new DiceRoll();
                Space oldSpc = BoardManager.positionToSpace(p.getPosition());
                if (oldSpc instanceof JailSpace jailSpc) {
                    //TODO improve this logic, should not have to recheck wasInJail
                    boolean wasInJail = p.getJailTurns() != -1;
                    jailSpc.doAction(p, roll);
                    if (wasInJail && !roll.isDoubles() && p.getJailTurns() == -1) {
                        /* Was in jail, did not roll doubles, and now out of jail */
                        continue;
                    }
                }
                int newPos = p.getPosition() + roll.getTotal();
                if (newPos > BoardManager.getNumSpaces()) {
                    /* Give player money if player passes Go.
                       Do nothing additional if player lands on Go. */
                    BoardManager.spaceNameToSpace(SpaceName.GO).doAction(p, roll);
                }
                newPos %= BoardManager.getNumSpaces();
                p.setPosition(newPos);
                Space newSpc = BoardManager.positionToSpace(newPos);
                newSpc.doAction(p, roll);
            }
        }
    }

}