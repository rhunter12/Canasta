package edu.up.cs301.canasta;

import edu.up.cs301.game.GameFramework.actionMessage.GameAction;
import edu.up.cs301.game.GameFramework.infoMessage.IllegalMoveInfo;

/**
 * Class that the local game can use to tell the players why an action was invalid
 */
public class CanastaIllegalMoveInfo extends IllegalMoveInfo {
    private GameAction act;
    private CanastaGameState state;
    private int num;//the number of the error message, used to identify specific errors
    /*
    Possible num values and meanings
        If action is a discard action
            1: failed to pick up the discard pile
            2: failed to discard to the discard pile
     */
    /**
     * Constructor
     * @param a the action that failed
     * @param s a copy of the current gamestate
     */
    public CanastaIllegalMoveInfo(GameAction a,CanastaGameState s){
        act=a;
        state=s;
        num=0;
    }
    /**
     * Constructor
     * @param a the action that failed
     * @param s a copy of the current gamestate
     * @param n a number that corresponds to the particular type of error (see num values above)
     */
    public CanastaIllegalMoveInfo(GameAction a,CanastaGameState s,int n){
        act=a;
        state=s;
        num=n;
    }
    public GameAction getAct(){
        return act;
    }
    public CanastaGameState getState(){
        return state;
    }
    public int getNum(){
        return num;
    }
}
