package edu.up.cs301.canasta;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.game.R;

public class CanastaComputerPlayer1 extends GameComputerPlayer {

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CanastaComputerPlayer1(int num, String name) {
        super(name);
        playerNum = num;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof CanastaGameState){
            CanastaGameState state = (CanastaGameState)info;

            if (state.getResources(playerNum).getHand().size() < 1){return;}

            game.sendAction(new CanastaDrawAction(this));
            game.sendAction(new CanastaSelectCardAction(this, state.getResources(playerNum).getHand().get(0).getValue()));
            game.sendAction(new CanastaDiscardAction(this));

        }
        else{
            System.out.println("Received other info message.");
        }
        return;

    }


    public void setPlayerNum(int n) { playerNum = n; }

    public int getPlayerNum() {
        return  playerNum;
    }



}