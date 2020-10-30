/**
 * @author
 *      Ryan Hunter-Bliss
 *      Sarah Ebner
 *      Lute Lillo Portero
 */

package edu.up.cs301.canasta;

import android.view.View;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameHumanPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;

public class CanastaPlayer extends GameHumanPlayer {
    private int score;
    private ArrayList<Card> hand = new ArrayList<>();
    private int playerNum;
    private int totalScore;

    private ArrayList<Card> meldedAce = new ArrayList<>();
    private ArrayList<Card> meldedWild = new ArrayList<>();
    private ArrayList<Card> melded3 = new ArrayList<>();
    private ArrayList<Card> melded4 = new ArrayList<>();
    private ArrayList<Card> melded5 = new ArrayList<>();
    private ArrayList<Card> melded6 = new ArrayList<>();
    private ArrayList<Card> melded7 = new ArrayList<>();
    private ArrayList<Card> melded8 = new ArrayList<>();
    private ArrayList<Card> melded9 = new ArrayList<>();
    private ArrayList<Card> melded10 = new ArrayList<>();
    private ArrayList<Card> meldedJack = new ArrayList<>();
    private ArrayList<Card> meldedQueen = new ArrayList<>();
    private ArrayList<Card> meldedKing = new ArrayList<>();

    private ArrayList<Integer> playerMoves = new ArrayList<>();

    /**
     * Constructor
     * @param num (The player number)
     */
    public CanastaPlayer(int num, String name) {
        super(name);
        score = 0;
        hand = new ArrayList<>();
        playerNum = num;
        totalScore = 0;
    }

    /**
     * Copy constructor
     * @param orig (The original player)
     */
    public CanastaPlayer(CanastaPlayer orig) {
        super(orig.name);
        score = orig.score;
        for (Card c: orig.hand) {
            this.hand.add(new Card(c));
        }
        for (Card c: orig.meldedAce) {
            this.meldedAce.add(new Card(c));
        }
        for (Card c: orig.meldedWild) {
            this.meldedWild.add(new Card(c));
        }
        for (Card c: orig.melded3) {
            this.melded3.add(new Card(c));
        }
        for (Card c: orig.melded4) {
            this.melded4.add(new Card(c));
        }
        for (Card c: orig.melded5) {
            this.melded5.add(new Card(c));
        }
        for (Card c: orig.melded6) {
            this.melded6.add(new Card(c));
        }
        for (Card c: orig.melded7) {
            this.melded7.add(new Card(c));
        }
        for (Card c: orig.melded8) {
            this.melded8.add(new Card(c));
        }
        for (Card c: orig.melded9) {
            this.melded9.add(new Card(c));
        }
        for (Card c: orig.melded10) {
            this.melded10.add(new Card(c));
        }
        for (Card c: orig.meldedJack) {
            this.meldedJack.add(new Card(c));
        }
        for (Card c: orig.meldedQueen) {
            this.meldedQueen.add(new Card(c));
        }
        for (Card c: orig.meldedKing) {
            this.meldedKing.add(new Card(c));
        }
        for (Integer v: orig.playerMoves) {
            this.playerMoves.add(v);
        }
        playerNum = orig.playerNum;
        totalScore = orig.totalScore;
    }

    /**
     * Converts a player info into a string
     * @return (The string to be printed)
     */
    @Override
    public String toString() {
        String info = "Player information: " + score + ", " + playerNum + "\n";
        info = info + "Hand: \n";
        for (Card c: hand) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Ace: \n";
        for (Card c: meldedAce) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Wild: \n";
        for (Card c: meldedWild) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 3: \n";
        for (Card c: melded3) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 4: \n";
        for (Card c: melded4) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 5: \n";
        for (Card c: melded5) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 6: \n";
        for (Card c: melded6) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 7: \n";
        for (Card c: melded7) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 8: \n";
        for (Card c: melded8) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 9: \n";
        for (Card c: melded9) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded 10: \n";
        for (Card c: melded10) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Jack: \n";
        for (Card c: meldedJack) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded Queen: \n";
        for (Card c: meldedQueen) {
            info = info + c.toString();
            info = info + ", ";
        }
        info = info + "\n Melded King: \n";
        for (Card c: meldedKing) {
            info = info + c.toString();
            info = info + ", ";
        }

        return info;
    }

    /**
     * Copy constructor that can hide certain hand information
     * @param orig (The original player)
     * @param copyHand (Whether to copy the hand or not)
     */
    public CanastaPlayer(CanastaPlayer orig, boolean copyHand) {
        super(orig.name);
        score = orig.score;
        if (copyHand) {
            for (Card c: orig.hand) {
                this.hand.add(new Card(c));
            }
        }
        else {
            for (Card c: orig.hand) {
                if (c.getKnownCard()) {
                    this.hand.add(new Card(c));
                }
            }
        }
        for (Card c: orig.meldedAce) {
            this.meldedAce.add(new Card(c));
        }
        for (Card c: orig.meldedWild) {
            this.meldedWild.add(new Card(c));
        }
        for (Card c: orig.melded3) {
            this.melded3.add(new Card(c));
        }
        for (Card c: orig.melded4) {
            this.melded4.add(new Card(c));
        }
        for (Card c: orig.melded5) {
            this.melded5.add(new Card(c));
        }
        for (Card c: orig.melded6) {
            this.melded6.add(new Card(c));
        }
        for (Card c: orig.melded7) {
            this.melded7.add(new Card(c));
        }
        for (Card c: orig.melded8) {
            this.melded8.add(new Card(c));
        }
        for (Card c: orig.melded9) {
            this.melded9.add(new Card(c));
        }
        for (Card c: orig.melded10) {
            this.melded10.add(new Card(c));
        }
        for (Card c: orig.meldedJack) {
            this.meldedJack.add(new Card(c));
        }
        for (Card c: orig.meldedQueen) {
            this.meldedQueen.add(new Card(c));
        }
        for (Card c: orig.meldedKing) {
            this.meldedKing.add(new Card(c));
        }
        for (Integer v: orig.playerMoves) {
            this.playerMoves.add(v);
        }
        playerNum = orig.playerNum;
        totalScore = orig.totalScore;
    }


    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        CanastaGameState state;
        if (info instanceof CanastaGameState) {
            state = (CanastaGameState) info;
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }



    /**
     * Takes two cards from deck; checks if it is a red three and
     * handles it accordingly
     * @param p (The player the action is from)
     */
    private void drawFromDeck(CanastaPlayer p, CanastaGameState state) {
        p.getHand().add(state.deck.remove(0));
        p.getHand().add(state.deck.remove(0));
        removeRedThree(p,state);
    }

    /**
     * Removes red three from hand and replaces it with something else
     * @param p (The player the action is from)
     */
    private void removeRedThree(CanastaPlayer p, CanastaGameState state) {
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(0).getValue() == 3 && (p.getHand().get(0).getSuit() == 'H' || p.getHand().get(0).getSuit() == 'D')) {
                p.getHand().remove(i);
                p.getHand().add(state.deck.remove(0));
                i = 0;  //resets loop if a red three has been found. Checks if new card is a red three
                p.setScore(p.getScore() + 100);
            }
        }
    }

    /**
     * Helper method to find the number of wild
     * cards in a meld
     * @param cards (The deck of cards to be searched)
     * @param value (Which meld is being considered)
     * @return (Returns the number of wild cards found)
     */
    public int countWildCards(ArrayList<Card> cards, int value) {
        int wildCount = 0;

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValue() != value) {
                wildCount++;
            }
        }
        return wildCount;
    }

    /**
     * Checks if all melds are of three or more cards
     * and more than half of the cards are not a wild cards or not empty
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean checkValidMeld(CanastaPlayer p) {
        if (!((p.getMeldedAce().size() >= 3 && countWildCards(p.getMeldedAce(), 1) <= p.getMeldedAce().size()/2) || p.getMeldedAce().size() == 0)) {
            return false;
        }
        if (!((p.getMelded4().size() >= 3 && countWildCards(p.getMelded4(), 4) <= p.getMelded4().size()/2) || p.getMelded4().size() == 0)) {
            return false;
        }
        if (!((p.getMelded5().size() >= 3 && countWildCards(p.getMelded5(), 5) <= p.getMelded5().size()/2) || p.getMelded5().size() == 0)) {
            return false;
        }
        if (!((p.getMelded6().size() >= 3 && countWildCards(p.getMelded6(), 6) <= p.getMelded6().size()/2) || p.getMelded6().size() == 0)) {
            return false;
        }
        if (!((p.getMelded7().size() >= 3 && countWildCards(p.getMelded7(), 7) <= p.getMelded7().size()/2) || p.getMelded7().size() == 0)) {
            return false;
        }
        if (!((p.getMelded8().size() >= 3 && countWildCards(p.getMelded8(), 8) <= p.getMelded8().size()/2) || p.getMelded8().size() == 0)) {
            return false;
        }
        if (!((p.getMelded9().size() >= 3 && countWildCards(p.getMelded9(), 9) <= p.getMelded9().size()/2) || p.getMelded9().size() == 0)) {
            return false;
        }
        if (!((p.getMelded10().size() >= 3 && countWildCards(p.getMelded10(), 10) <= p.getMelded10().size()/2) || p.getMelded10().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedJack().size() >= 3 && countWildCards(p.getMeldedJack(), 11) <= p.getMeldedJack().size()/2) || p.getMeldedJack().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedQueen().size() >= 3 && countWildCards(p.getMeldedQueen(), 12) <= p.getMeldedQueen().size()/2) || p.getMeldedQueen().size() == 0)) {
            return false;
        }
        if (!((p.getMeldedKing().size() >= 3 && countWildCards(p.getMeldedKing(), 13) <= p.getMeldedKing().size()/2) || p.getMeldedKing().size() == 0)) {
            return false;
        }

        if (!(p.getMeldedWild().size() == 0 || p.getMeldedWild().size() >= 3)) {
            return false;
        }
        return true;
    }

    /**
     * Searches hand for selected card and returns index
     * @param p (The player the action is from)
     * @param n (The value being searched for)
     * @return (Returns the index of the value in the hand)
     */
    public int searchHand(CanastaPlayer p, int n) {
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(i).getValue() == n) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Selects card
     * @param p (The player the action is from)
     * @param card (The card that is selected)
     * @return (Returns whether the action was successful or not)
     */
    public boolean selectCard(CanastaPlayer p, int card, CanastaGameState state) {
        if (state.getPlayerTurnID() == p.getPlayerNum()) {
            state.setSelectedCard(card);
            return true;
        }
        return false;
    }

    /**
     * Adds a selected card to the player's meld
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean meldCard(CanastaPlayer p, CanastaGameState state) {
        int pos = searchHand(p, state.getSelectedCard());

        if (pos == -1) {
            return false;
        }
        switch (state.getSelectedCard()) {
            case -1:
                return false;
            case 1:
                p.getPlayerMoves().add(1);
                p.getMeldedAce().add(p.getHand().remove(pos));
                break;
            case 2:
                p.getPlayerMoves().add(2);
                p.getMeldedWild().add(p.getHand().remove(pos));
                break;
            case 3:
                p.getPlayerMoves().add(3);
                p.getMelded3().add(p.getHand().remove(pos));
                break;
            case 4:
                p.getPlayerMoves().add(4);
                p.getMelded4().add(p.getHand().remove(pos));
                break;
            case 5:
                p.getPlayerMoves().add(5);
                p.getMelded5().add(p.getHand().remove(pos));
                break;
            case 6:
                p.getPlayerMoves().add(6);
                p.getMelded6().add(p.getHand().remove(pos));
                break;
            case 7:
                p.getPlayerMoves().add(7);
                p.getMelded7().add(p.getHand().remove(pos));
                break;
            case 8:
                p.getPlayerMoves().add(8);
                p.getMelded8().add(p.getHand().remove(pos));
                break;
            case 9:
                p.getPlayerMoves().add(9);
                p.getMelded9().add(p.getHand().remove(pos));
                break;
            case 10:
                p.getPlayerMoves().add(10);
                p.getMelded10().add(p.getHand().remove(pos));
                break;
            case 11:
                p.getPlayerMoves().add(11);
                p.getMeldedJack().add(p.getHand().remove(pos));
                break;
            case 12:
                p.getPlayerMoves().add(12);
                p.getMeldedQueen().add(p.getHand().remove(pos));
                break;
            case 13:
                p.getPlayerMoves().add(13);
                p.getMeldedKing().add(p.getHand().remove(pos));
                break;
        }
        return true;
    }


    /**
     * Searches through hand for selected card to move to
     * discard pile
     * @param p (The player the action is from)
     * @return (Returns whether the action was successful or not)
     */
    public boolean addToDiscard(CanastaPlayer p, CanastaGameState state) {
        if (!(checkValidMeld(p))) {
            return false;
        }
        for (int i = 0; i < p.getHand().size(); i++) {
            if (p.getHand().get(i).getValue() == state.getSelectedCard()) {
                state.discardPile.add(p.getHand().remove(i));
                state.setSelectedCard(-1);
                return true;
            }
        }
        return false;
    }


    /**
     * Allows player to unmeld a card based on their previous moves list
     * @param p (The player the action is from)
     * @return (Returns if the action was successful or not)
     */
    public boolean undo(CanastaPlayer p) {
        if(p.getPlayerMoves().size() == 0) {
            return false;
        }
        int value = p.getPlayerMoves().get(p.getPlayerMoves().size()-1);

        switch (value) {
            case 1:
                p.getHand().add(p.getMeldedAce().remove(p.getMeldedAce().size()-1));
                break;
            case 3:
                p.getHand().add(p.getMelded3().remove(p.getMelded3().size()-1));
                break;
            case 4:
                p.getHand().add(p.getMelded4().remove(p.getMelded4().size()-1));
                break;
            case 5:
                p.getHand().add(p.getMelded5().remove(p.getMelded5().size()-1));
                break;
            case 6:
                p.getHand().add(p.getMelded6().remove(p.getMelded6().size()-1));
                break;
            case 7:
                p.getHand().add(p.getMelded7().remove(p.getMelded7().size()-1));
                break;
            case 8:
                p.getHand().add(p.getMelded8().remove(p.getMelded8().size()-1));
                break;
            case 9:
                p.getHand().add(p.getMelded9().remove(p.getMelded9().size()-1));
                break;
            case 10:
                p.getHand().add(p.getMelded10().remove(p.getMelded10().size()-1));
                break;
            case 11:
                p.getHand().add(p.getMeldedJack().remove(p.getMeldedJack().size()-1));
                break;
            case 12:
                p.getHand().add(p.getMeldedQueen().remove(p.getMeldedQueen().size()-1));
                break;
            case 13:
                p.getHand().add(p.getMeldedKing().remove(p.getMeldedKing().size()-1));
                break;
        }

        p.getPlayerMoves().remove(p.getPlayerMoves().size()-1);
        return true;
    }










    public void setScore(int s) {
        score = s;
    }

    public int getScore() {
        return score;
    }

    public void setPlayerNum(int n) { playerNum = n; }

    public int getPlayerNum() {
        return  playerNum;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getMeldedAce() {
        return meldedAce;
    }

    public void setMeldedAce(ArrayList<Card> meldedAce) {
        this.meldedAce = meldedAce;
    }

    public ArrayList<Card> getMeldedWild() {
        return meldedWild;
    }

    public void setMeldedWild(ArrayList<Card> meldedWild) {
        this.meldedWild = meldedWild;
    }

    public ArrayList<Card> getMelded3() {
        return melded3;
    }
    public void setMelded3(ArrayList<Card> melded3) {
        this.melded3 = melded3;
    }

    public ArrayList<Card> getMelded4() {
        return melded4;
    }

    public void setMelded4(ArrayList<Card> melded4) {
        this.melded4 = melded4;
    }

    public ArrayList<Card> getMelded5() {
        return melded5;
    }

    public void setMelded5(ArrayList<Card> melded5) {
        this.melded5 = melded5;
    }

    public ArrayList<Card> getMelded6() {
        return melded6;
    }

    public void setMelded6(ArrayList<Card> melded6) {
        this.melded6 = melded6;
    }

    public ArrayList<Card> getMelded7() {
        return melded7;
    }

    public void setMelded7(ArrayList<Card> melded7) {
        this.melded7 = melded7;
    }

    public ArrayList<Card> getMelded8() {
        return melded8;
    }

    public void setMelded8(ArrayList<Card> melded8) {
        this.melded8 = melded8;
    }

    public ArrayList<Card> getMelded9() {
        return melded9;
    }

    public void setMelded9(ArrayList<Card> melded9) {
        this.melded9 = melded9;
    }

    public ArrayList<Card> getMelded10() {
        return melded10;
    }

    public void setMelded10(ArrayList<Card> melded10) {
        this.melded10 = melded10;
    }

    public ArrayList<Card> getMeldedJack() {
        return meldedJack;
    }

    public void setMeldedJack(ArrayList<Card> meldedJack) {
        this.meldedJack = meldedJack;
    }

    public ArrayList<Card> getMeldedQueen() {
        return meldedQueen;
    }

    public void setMeldedQueen(ArrayList<Card> meldedQueen) {
        this.meldedQueen = meldedQueen;
    }

    public ArrayList<Card> getMeldedKing() {
        return meldedKing;
    }

    public void setMeldedKing(ArrayList<Card> meldedKing) {
        this.meldedKing = meldedKing;
    }

    public ArrayList<Integer> getPlayerMoves() {
        return playerMoves;
    }

}
