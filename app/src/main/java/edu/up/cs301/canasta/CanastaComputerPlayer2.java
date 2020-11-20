package edu.up.cs301.canasta;

import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;

public class CanastaComputerPlayer2 extends GameComputerPlayer {

    private int[] counts = new int[14];

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CanastaComputerPlayer2(int num, String name) {
        super(name);
        playerNum = num;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof CanastaGameState) {
            CanastaGameState state = (CanastaGameState)info;


            //getting accurate count of cards in hand
            for (int i = 0; i < state.getResources(playerNum).getHand().size(); i++) {
                counts[state.getResources(playerNum).getHand().get(i).getValue()]++;
            }



            if (state.getResources(playerNum).getHand().size() < 1){return;}

            //decide if we're going to draw or pick up the discard pile
            if (calcMaxPoints(state.getResources(playerNum),true) < state.checkPointsToMeld(playerNum)) {
                game.sendAction(new CanastaDrawAction(this));
            }

            else if (state.discardPile.size() >= 4) {
                int topCard = state.discardPile.get(state.discardPile.size()-1).getValue();

                if (state.getResources(playerNum).getMelds().get(topCard).size() >= 3) {
                    game.sendAction(new CanastaDiscardAction(this));
                }
                else if (countInHand(state.getResources(playerNum).getHand(), topCard) >= 2) {
                    game.sendAction(new CanastaDiscardAction(this));
                }
                else {
                    game.sendAction(new CanastaDrawAction(this));
                }
            }

            //decide which card we're going to meld
            if (!(calcMaxPoints(state.getResources(playerNum),true) < state.checkPointsToMeld(playerNum))) {

                for (int i = 0; i < state.getResources(playerNum).getMelds().size(); i++) {
                    if (i == 0 || i == 2) {
                        continue;
                    }
                    else if (countInHand(state.getResources(playerNum).getHand(), i) >= 3) {
                        for (int j = 0; j < countInHand(state.getResources(playerNum).getHand(), i); j++) {
                            game.sendAction(new CanastaSelectCardAction(this,i));
                            game.sendAction(new CanastaMeldAction(this));
                        }
                    }
                }
            }

            //now pick a card to discard
            if (countInHand(state.getResources(playerNum).getHand(),3) > 0) {
                game.sendAction(new CanastaSelectCardAction(this,3));
            }
            else {
                game.sendAction(new CanastaSelectCardAction(this,state.getResources(playerNum).getHand().get(0).getValue()));
            }
            game.sendAction(new CanastaDiscardAction(this));

            
        }
        else{
            System.out.println("Received other info message.");
        }
        return;

    }


    public int calcMaxPoints(PlayerResources p, boolean useWilds) {
        int[] countsCopy = new int[14];

        //deep copy of counts contents to modify values
        for (int i = 0; i < 14; i++) {
            countsCopy[i] = counts[i];
        }

        int possibleScore = 0;

        for (int i = 0; i < p.getHand().size(); i++) {
            countsCopy[p.getHand().get(i).getValue()]++;
        }

        for (int i = 0; i < 14; i++) {
            if (i == 0 || i == 2) {
                continue;
            }
            else if (countsCopy[i] >= 3) {
                possibleScore += (getPointValue(i)*countsCopy[i]);
            }

            if (useWilds) {
                if (countsCopy[i] == 2) {
                    if (countsCopy[0] > 0) {
                        possibleScore += (getPointValue(i)*countsCopy[i]) + 50;
                        countsCopy[0]--;
                    }
                    else if (countsCopy[2] > 0) {
                        possibleScore += (getPointValue(i)*countsCopy[i]) + 20;
                        countsCopy[2]--;
                    }
                }
            }

        }

        return possibleScore + p.getScore();
    }

    public int getPointValue(int value) {
        if (value == 1 || value == 2) {
            return 20;
        }
        else if (value == 0) {
            return 50;
        }
        else if (value <= 7) {
            return 5;
        }
        else {
            return 10;
        }
    }

    /**
     * Tells the number of instances of
     * a specific card in the player's hand
     * @param hand (The hand to search through)
     * @param n (The value we're searching for)
     * @return (The count of cards)
     */
    public int countInHand(ArrayList<Card> hand, int n) {
        int count = 0;

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == n) {
                count++;
            }
        }
        return count;
    }
}