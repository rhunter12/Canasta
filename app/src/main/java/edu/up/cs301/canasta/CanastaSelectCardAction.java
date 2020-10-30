package edu.up.cs301.canasta;

import edu.up.cs301.game.GameFramework.GamePlayer;
import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class CanastaSelectCardAction extends GameAction {

    private int selectedValue;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param value the value of the card to be selected
     */
    public CanastaSelectCardAction(GamePlayer player, int value) {
        super(player);
        selectedValue = value;
    }

    public int getSelectedValue() {
        return selectedValue;
    }
}
