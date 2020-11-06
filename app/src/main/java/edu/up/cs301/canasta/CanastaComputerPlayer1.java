package edu.up.cs301.canasta;

import android.widget.Button;

import java.util.ArrayList;

import edu.up.cs301.game.GameFramework.GameComputerPlayer;
import edu.up.cs301.game.GameFramework.GameMainActivity;
import edu.up.cs301.game.GameFramework.infoMessage.GameInfo;

public class CanastaComputerPlayer1 extends GameComputerPlayer {

    private int score;
    private ArrayList<Card> hand = new ArrayList<>();
    private int playerNum;
    private int totalScore;
    private boolean discardState; //true = start of turn, pick up pile      false = discard on click

    private ArrayList<Button> meldButtons = new ArrayList<>();

    private ArrayList<ArrayList<Card>> melds = new ArrayList<>();

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
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CanastaComputerPlayer1(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {

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
