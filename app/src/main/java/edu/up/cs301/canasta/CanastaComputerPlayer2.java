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

    /**
     * Performs action when it gets info back from GameInfo
     * @param info (The game info sender)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof CanastaGameState) {
            CanastaGameState state = (CanastaGameState)info;

            //getting accurate count of cards in hand
            for (int i = 0; i < state.getResources(playerNum).getHand().size(); i++) {
                counts[state.getResources(playerNum).getHand().get(i).getValue()]++;
            }

            if (state.getResources(playerNum).getHand().size() < 1){return;}

            if (state.getTurnStage() == 0) {
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
                else {
                    game.sendAction(new CanastaDrawAction(this));
                }

                //decide which card we're going to meld
                if (!(calcMaxPoints(state.getResources(playerNum),false) < state.checkPointsToMeld(playerNum))) {

                    for (int i = 0; i < state.getResources(playerNum).getMelds().size(); i++) {
                        if (i == 0 || i == 2) {
                            continue;
                        }
                        else if (countInHand(state.getResources(playerNum).getHand(), i) >= 3) {
                            for (int j = 0; j < countInHand(state.getResources(playerNum).getHand(), i);) {
                                game.sendAction(new CanastaSelectCardAction(this,i));
                                game.sendAction(new CanastaMeldAction(this));
                            }
                        }
                    }
                }

                //how pick a card to discard
                if (countInHand(state.getResources(playerNum).getHand(),3) > 0) {
                    game.sendAction(new CanastaSelectCardAction(this,3));
                }
                else if (selectSingleCard() != -1) {
                    int cardVal = selectSingleCard();
                    game.sendAction(new CanastaSelectCardAction(this,cardVal));
                }
                else {
                    game.sendAction(new CanastaSelectCardAction(this,state.getResources(playerNum).getHand().get(0).getValue()));
                }
                game.sendAction(new CanastaDiscardAction(this));
            }



        }
        else{
            System.out.println("Received other info message.");
        }
        return;

    }

    /**
     * Helper method to select a card to discard
     * @return (The card to select)
     */
    public int selectSingleCard() {
        for (int i = 3; i < counts.length; i++) {
            if (counts[i] == 1) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Helper method to calculate the max number of points
     * from an action played. Helps AI pick the best moves.
     * @param p (The player)
     * @param useWilds (Whether they want to use wild cards)
     * @return (The point value of that move)
     */
    public int calcMaxPoints(PlayerResources p, boolean useWilds) {
        int[] countsCopy = new int[14];

        //deep copy of counts contents to modify values
        for (int i = 0; i < 14; i++) {
            countsCopy[i] = counts[i];
        }

        int possibleScore = 0;


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

    /**
     * Gets the point value of a specific card
     * @param value (The card value)
     * @return (The points value)
     */
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

    public void setPlayerNum(int n) { playerNum = n; }

    public int getPlayerNum() {
        return  playerNum;
    }
}