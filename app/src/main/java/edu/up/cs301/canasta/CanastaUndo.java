package edu.up.cs301.canasta;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class CanastaUndo extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CanastaUndo(GamePlayer player) {
        super(player);
    }
}
